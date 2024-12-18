package com.ptoject.VkFriendsWeather.repository;

import com.ptoject.VkFriendsWeather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
