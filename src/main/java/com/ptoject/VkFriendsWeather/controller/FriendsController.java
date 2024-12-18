package com.ptoject.VkFriendsWeather.controller;

import com.ptoject.VkFriendsWeather.service.FriendsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @GetMapping("/saveFriends")
    public void saveFriends(HttpServletRequest request) {
        friendsService.save(request.getHeader("Authorization").substring(7));
    }

}
