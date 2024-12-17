package com.ptoject.VkFriendsWeather.repository;

import com.ptoject.VkFriendsWeather.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository<Friend, Long> {


}
