package com.ptoject.VkFriendsWeather.controller;

import com.ptoject.VkFriendsWeather.service.FriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @PostMapping("/saveFriends")
    public void saveFriends(@RequestHeader("Authorization") String token) {
        friendsService.save(getToken(token));
    }

    private String getToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return "";
    }
}
