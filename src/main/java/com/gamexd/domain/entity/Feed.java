package com.gamexd.domain.entity;

import com.gamexd.domain.enums.FeedType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feed")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private FeedType type;

    @ManyToOne(optional = false)
    private User actor;

    @ManyToOne
    private Review review;

    @ManyToOne
    private GameList gameList;
}
