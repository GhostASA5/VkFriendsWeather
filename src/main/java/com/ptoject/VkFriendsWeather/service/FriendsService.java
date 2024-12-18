package com.ptoject.VkFriendsWeather.service;

import com.ptoject.VkFriendsWeather.exception.FriendException;
import com.ptoject.VkFriendsWeather.exception.UserNotFoundException;
import com.ptoject.VkFriendsWeather.model.Friend;
import com.ptoject.VkFriendsWeather.model.User;
import com.ptoject.VkFriendsWeather.model.Weather;
import com.ptoject.VkFriendsWeather.repository.FriendsRepository;
import com.ptoject.VkFriendsWeather.repository.UserRepository;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService extends ApiClientService{

    private final WeatherService weatherService;

    private final UserRepository userRepository;

    private final FriendsRepository friendsRepository;

    public void save(String token) {
        log.info("Start saving user friends: token - {}", token);
        try {
            ServiceActor actor = getServiceActor(token);
            List<Long> friendIds = vk.friends().get(actor)
                    .execute()
                    .getItems();
            Long userId = vk.users().get(actor).execute().get(0).getId();

            saveFriends(friendIds, userId, token);
        } catch (ApiException | ClientException ex) {
            log.error("Ошибка во время получения списка id друзей: {} ", ex.getMessage());
            throw new FriendException("Ошибка во время получения списка id друзей " + ex.getMessage());
        }
    }

    private void saveFriends(List<Long> friendIds, Long userId, String token) {
        log.info("Start saving user: friendsCount - {}, userId - {}, token - {}", friendIds.size(), userId, token);
        try {
            List<GetResponse> responseList = vk.users()
                    .get(getServiceActor(token))
                    .userIds(friendIds.stream().map(String::valueOf).collect(Collectors.toList()))
                    .fields(Fields.CITY)
                    .execute();
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new UserNotFoundException("Пользователь " + userId + " не найден.")
            );

            for (GetResponse response : responseList) {
                Friend friend = Friend.builder()
                        .id(response.getId())
                        .firstName(response.getFirstName())
                        .lastName(response.getLastName())
                        .userFriend(user)
                        .build();
                if (response.getCity() != null) {
                    friend.setCity(response.getCity().getTitle());
                    Weather weather = weatherService.loadWeather(response.getCity().getTitle());
                    friend.setWeather(weather);
                }
                friendsRepository.save(friend);
            }
        } catch (ApiException | ClientException ex) {
            log.error("Fail to save friends: {}", ex.getMessage());
            throw new FriendException("Ошибка во время получения списка друзей " + ex.getMessage());
        }
    }

    private ServiceActor getServiceActor(String token) {
        return new ServiceActor(Integer.valueOf(clientId), token);
    }
}
