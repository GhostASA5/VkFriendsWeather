package com.ptoject.VkFriendsWeather.controller;

import com.ptoject.VkFriendsWeather.dto.AccountResponse;
import com.ptoject.VkFriendsWeather.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/oauth2/callback/vk")
    public String handleCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 Model model,
                                 HttpServletResponse response){
        AccountResponse account = authService.authenticate(code, response);
        model.addAttribute("firstName", account.getFirstName());
        model.addAttribute("lastName", account.getLastName());

        return "home";
    }
}
