package com.ptoject.VkFriendsWeather.service;

import com.ptoject.VkFriendsWeather.dto.AccountResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.account.responses.GetProfileInfoResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    TransportClient transportClient = new HttpTransportClient();

    VkApiClient vk = new VkApiClient(transportClient);

    @Value("${spring.security.oauth2.client.registration.vk.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.vk.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.vk.redirect-uri}")
    private String redirectUri;

    public AccountResponse authenticate(String code, HttpServletResponse response) {
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
            return new AccountResponse(accountResponse.getFirstName(), accountResponse.getLastName());
        } catch (ApiException | ClientException e) {
            throw new RuntimeException("Произошла ошибка во время авторизации, повторите попытку позже.");
        }
    }
}
