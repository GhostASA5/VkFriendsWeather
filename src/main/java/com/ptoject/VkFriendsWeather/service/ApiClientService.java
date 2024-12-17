package com.ptoject.VkFriendsWeather.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;

public class ApiClientService {

    @Value("${spring.security.oauth2.client.registration.vk.client-id}")
    public String clientId;

    @Value("${spring.security.oauth2.client.registration.vk.client-secret}")
    public String clientSecret;

    @Value("${spring.security.oauth2.client.registration.vk.redirect-uri}")
    public String redirectUri;

    TransportClient transportClient = new HttpTransportClient();

    VkApiClient vk = new VkApiClient(transportClient);
}
