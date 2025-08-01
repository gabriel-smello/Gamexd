package com.gamexd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedDto {
    private Long id;
    private String type;
    private String username;
    private String description;
    private LocalDateTime timestamp;
}
