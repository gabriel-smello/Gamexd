import requests
import mysql.connector
import schedule
import time
import os

clientId  = os.getenv("igdb_client-id")
clientToken = os.getenv("igdb_token")

# Configurações do banco de dados
db_config = {
    "host": "localhost",
    "user": "root",
    "password": os.getenv("MYSQL_PASSWORD"),
    "database": "igdb_cache"
}

# Função para se conectar ao banco de dados MySQL
def connect_db():
    return mysql.connector.connect(**db_config)

# Função para salvar os dados no MySQL
def save_game_data(games):
    conn = connect_db()
    cursor = conn.cursor()

    # Insere ou atualiza os dados dos jogos
    for game in games:
        game_id = game.get('id')
        name = game.get('name', '')
        summary = game.get('summary', '')
        storyline = game.get('storyline', '')
        release_date = None
        first_release_timestamp = game.get('first_release_date')

        if first_release_timestamp and isinstance(first_release_timestamp, (int, float)) and first_release_timestamp > 0:
            try:
                release_date = time.strftime('%Y-%m-%d', time.localtime(first_release_timestamp))
            except (OSError, ValueError):
                release_date = None
        cover_url = ''

        if 'cover' in game and 'url' in game['cover']:
            cover_url = "https:" + game['cover']['url']

                # Inserir ou atualizar jogo
            cursor.execute("""
                    INSERT INTO games (id, name, summary, storyline, release_date, cover_url)
                    VALUES (%s, %s, %s, %s, %s, %s)
                    ON DUPLICATE KEY UPDATE
                        name=VALUES(name),
                        summary=VALUES(summary),
                        storyline=VALUES(storyline),
                        release_date=VALUES(release_date),
                        cover_url=VALUES(cover_url)
                """, (game_id, name, summary, storyline, release_date, cover_url))

            # Inserir gêneros
            genres = game.get('genres', [])
            for genre_id in genres:
                cursor.execute("""
                        INSERT IGNORE INTO game_genres (game_id, genre_id) VALUES (%s, %s)
                        """, (game_id, genre_id))
                # Inserir plataformas
            platforms = game.get('platforms', [])
            for platform_id in platforms:
                cursor.execute("""
                        INSERT IGNORE INTO game_platforms (game_id, platform_id) VALUES (%s, %s)
                        """, (game_id, platform_id))

    conn.commit()
    cursor.close()
    conn.close()

# Função para consumir a API da IGDB
def fetch_games():
    url = "https://api.igdb.com/v4/games"
    headers = {
        "Client-ID": f"{clientId}",
        "Authorization": f"Bearer {clientToken}"
    }

    limit = 500
    offset = 0
    total_fetched = 0
    while True:
        # Corpo da requisição para a API
        data = f"""
        fields id, name, summary, storyline, first_release_date, cover.url, genres, platforms;
        limit {limit};
        offset {offset};
        """

        response = requests.post(url, headers=headers, data=data)

        if response.status_code == 200:
            games = response.json()
            if not games:
                print("Nenhum jogo retornado, fim da paginação.")
                break  # Sai do while se não vier mais jogos

            save_game_data(games)
            fetched_now = len(games)
            total_fetched += fetched_now
            print(f"{fetched_now} jogos baixados (total: {total_fetched}).")
            if fetched_now < limit:
                # Se retornou menos do que o limite, acabou os jogos.
                print("Todos os jogos foram baixados.")
                break
            offset += limit
            time.sleep(0.5)
        else:
            print(f"Erro ao consumir a API. Status code: {response.status_code}")
            break

def fetch_and_update_genres():
    url = "https://api.igdb.com/v4/genres"
    headers = {
        "Client-ID": f"{clientId}",
        "Authorization": f"Bearer {clientToken}"
    }

    limit = 500
    offset = 0

    conn = connect_db()
    cursor = conn.cursor()

    while True:
        data = f"""
        fields id, name;
        limit {limit};
        offset {offset};
        """

        response = requests.post(url, headers=headers, data=data)

        if response.status_code == 200:
            genres = response.json()
            if not genres:
                print("Nenhum gênero retornado, fim da paginação.")
                break

            for genre in genres:
                genre_id = genre.get('id')
                genre_name = genre.get('name', f"Genero_{genre_id}")

                cursor.execute("""
                    INSERT INTO genres (id, name)
                    VALUES (%s, %s)
                    ON DUPLICATE KEY UPDATE name=VALUES(name)
                """, (genre_id, genre_name))

            conn.commit()

            if len(genres) < limit:
                print("Todos os gêneros foram atualizados.")
                break

            offset += limit
            time.sleep(1)

        else:
            print(f"Erro ao consumir a API (genres). Status code: {response.status_code}, Mensagem: {response.text}")
            break

    cursor.close()
    conn.close()

def fetch_and_update_platforms():
    url = "https://api.igdb.com/v4/platforms"
    headers = {
        "Client-ID": f"{clientId}",
        "Authorization": f"Bearer {clientToken}"
    }

    limit = 500
    offset = 0

    conn = connect_db()
    cursor = conn.cursor()

    while True:
        data = f"""
        fields id, name;
        limit {limit};
        offset {offset};
        """

        response = requests.post(url, headers=headers, data=data)

        if response.status_code == 200:
            platforms = response.json()
            if not platforms:
                print("Nenhuma plataforma retornada, fim da paginação.")
                break

            for platform in platforms:
                platform_id = platform.get('id')
                platform_name = platform.get('name', f"Platform_{platform_id}")

                cursor.execute("""
                    INSERT INTO platforms (id, name)
                    VALUES (%s, %s)
                    ON DUPLICATE KEY UPDATE name=VALUES(name)
                """, (platform_id, platform_name))

            conn.commit()

            if len(platforms) < limit:
                print("Todas as plataformas foram atualizadas.")
                break

            offset += limit
            time.sleep(1)

        else:
            print(f"Erro ao consumir a API (platforms). Status code: {response.status_code}, Mensagem: {response.text}")
            break

    cursor.close()
    conn.close()


# Função agendada que será executada a cada 24 horas
def job():
    print("Iniciando a atualização dos jogos...")
    fetch_and_update_genres()
    fetch_and_update_platforms()
    fetch_games()
    print("Atualização concluída.")


# Rodando o agendador
if __name__ == "__main__":

    print("Iniciando o script de cache da IGDB...")
    job()  # Rodar uma vez imediatamente

    # Agendamento da tarefa para rodar a cada 24 horas
    schedule.every(24).hours.do(job)
    while True:
        schedule.run_pending()  # Verifica se é hora de rodar a próxima tarefa
        time.sleep(60)  # Aguarda 1 minuto antes de verificar novamente
