package com.ptoject.VkFriendsWeather.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weather")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String city;

    @Column
    private String main;

    @Column
    private String description;

    @Column
    private Double temp;

    @Column
    private Double pressure;

    @Column
    private Double humidity;

    @OneToOne(mappedBy = "weather")
    private Friend friend;

}
