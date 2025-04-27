package com.gamexd.service;

import com.gamexd.domain.dto.GameDto;
import com.gamexd.domain.entity.Game;
import com.gamexd.mapper.GameMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IgdbService {

    @Value("${igdb_client-id}")
    private String clientId;

    @Value("${igdb_token}")
    private String token;

    private WebClient webClient;
    @Autowired
    private GameMapper gameMapper;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.igdb.com/v4/games")
                .defaultHeader("Client-ID", clientId)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public Game fetchGameFromApi(Long id) {
        String query = "fields name,summary,rating,first_release_date,cover.url; where id = " + id + ";";

        return webClient.post()
                .bodyValue(query)
                .retrieve()
                .bodyToFlux(GameDto.class)
                .map(gameMapper::toEntity)
                .blockFirst();
    }

    public List<Game> fetchTopTenGames() {
        String query = "fields name,summary,rating,first_release_date,cover.url,genres.id, genres.name;" +
                " where rating != null;" +
                " sort rating desc;" +
                " limit 10;";

        return webClient.post()
                .bodyValue(query)
                .retrieve()
                .bodyToFlux(GameDto.class)
                .map(gameMapper::toEntity)
                .collectList()
                .block();
    }

    public List<Game> fetchTrendingGames() {
        long sixtyDaysAgo = Instant.now().minus(60, ChronoUnit.DAYS).getEpochSecond();

        String query = String.format("""
        fields id, name, summary, rating, first_release_date, cover.url, genres.id, genres.name, follows, hypes;
        where first_release_date > %d & rating != null;
        sort popularity desc;
        limit 10;
    """, sixtyDaysAgo);

        return webClient.post()
                .bodyValue(query)
                .retrieve()
                .bodyToFlux(GameDto.class)
                .map(dto -> {
                    Game game = gameMapper.toEntity(dto);
                    game.setTrendingScore(calculateTrendingScore(dto));
                    return game;
                })
                .sort((g1, g2) -> Double.compare(g2.getTrendingScore(), g1.getTrendingScore()))
                .take(20)
                .collectList()
                .block();
    }

    public List<Game> fetchNewlyGames() {
        String query = "fields name,summary,rating,first_release_date,cover.url,genres.id, genres.name;" +
                " where rating != null;" +
                " sort first_release_date desc;" +
                " limit 10;";

        return webClient.post()
                .bodyValue(query)
                .retrieve()
                .bodyToFlux(GameDto.class)
                .map(gameMapper::toEntity)
                .collectList()
                .block();
    }

    private double calculateTrendingScore(GameDto dto) {
        double popularity = dto.getPopularity() != null ? dto.getPopularity() : 0.0;
        int follows = dto.getFollows() != null ? dto.getFollows() : 0;
        int hypes = dto.getHypes() != null ? dto.getHypes() : 0;
        return (popularity * 0.4) + (follows * 0.3) + (hypes * 0.3);
    }

}
