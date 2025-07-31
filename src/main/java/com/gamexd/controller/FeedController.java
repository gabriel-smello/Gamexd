package com.gamexd.controller;

import com.gamexd.domain.dto.FeedDto;
import com.gamexd.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;
    @GetMapping
    public ResponseEntity<List<FeedDto>> getFeed(@AuthenticationPrincipal Jwt jwt) {
        List<FeedDto> feed = feedService.getFeed(jwt);
        return ResponseEntity.ok(feed);
    }
}
