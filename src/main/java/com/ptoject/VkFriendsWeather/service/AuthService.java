package com.ptoject.VkFriendsWeather.service;

import com.ptoject.VkFriendsWeather.dto.AccountResponse;
import com.ptoject.VkFriendsWeather.exception.AuthorizationException;
import com.ptoject.VkFriendsWeather.model.User;
import com.ptoject.VkFriendsWeather.repository.UserRepository;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.account.responses.GetProfileInfoResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService extends ApiClientService{

    private final UserRepository userRepository;

    public AccountResponse authenticate(String code, HttpServletResponse response) {
        log.info("Authenticating with code: {}", code);
        try {
            UserAuthResponse authResponse = vk.oAuth()
                    .userAuthorizationCodeFlow(Integer.valueOf(clientId),
                            clientSecret,
                            redirectUri,
                            code)
                    .execute();

            Long userId = Long.valueOf(authResponse.getUserId());
            UserActor actor = new UserActor(userId, authResponse.getAccessToken());

            response.addHeader("Authorization", "Bearer " + actor.getAccessToken());

            GetProfileInfoResponse accountResponse = vk.account().getProfileInfo(actor).execute();
            User newUser = User.builder()
                    .id(userId)
                    .firstName(accountResponse.getFirstName())
                    .lastName(accountResponse.getLastName())
                    .build();
            userRepository.save(newUser);
            return new AccountResponse(accountResponse.getFirstName(), accountResponse.getLastName());
        } catch (ApiException | ClientException e) {
            log.error("Authentication failed", e);
            throw new AuthorizationException("Произошла ошибка во время авторизации, повторите попытку позже.");
        }
    }
}
