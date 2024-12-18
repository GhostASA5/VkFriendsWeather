package com.ptoject.VkFriendsWeather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VkFriendsWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkFriendsWeatherApplication.class, args);
	}

}
