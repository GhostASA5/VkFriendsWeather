package com.ptoject.VkFriendsWeather.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "friends")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Friend {

    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userFriend;
}
