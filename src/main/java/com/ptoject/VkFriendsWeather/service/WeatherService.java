package com.ptoject.VkFriendsWeather.service;

import com.ptoject.VkFriendsWeather.feign.WeatherClient;
import com.ptoject.VkFriendsWeather.model.Weather;
import com.ptoject.VkFriendsWeather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    @Value("${app.weather.apiKey}")
    private String apiKey;

    @Value("${app.weather.units}")
    private String units;

    @Value("${app.weather.lang}")
    private String lang;

    private final WeatherClient weatherClient;

    private final WeatherRepository weatherRepository;

    public Weather loadWeather(String city) {
        log.info("Load weather from city {}", city);
        String weatherResponse;
        try {
            weatherResponse = weatherClient.getWeather(city, apiKey, units, lang);
        } catch (Exception ex) {
            log.error("Ошибка во время получения погоды города {}: {}", city, ex.getMessage());
            return null;
        }

        JSONObject weatherJSON = new JSONObject(weatherResponse);

        JSONArray json = weatherJSON.getJSONArray("weather");
        String main = json.getJSONObject(0).getString("main");
        String description = json.getJSONObject(0).getString("description");

        JSONObject mainJSON = weatherJSON.getJSONObject("main");
        Double temp = mainJSON.getDouble("temp");
        Double pressure = mainJSON.getDouble("pressure");
        Double humidity = mainJSON.getDouble("humidity");

        Weather weather = Weather.builder()
                .city(city)
                .main(main)
                .description(description)
                .temp(temp)
                .pressure(pressure)
                .humidity(humidity)
                .build();

        return weatherRepository.save(weather);
    }
}
