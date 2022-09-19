package com.example.restaurantapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
//@RequiredArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RestaurantType type;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant",cascade=CascadeType.ALL)
//    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "restaurant")
    private List<Meal> mealsList;


    public Restaurant(String name, String address, RestaurantType type) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.id = UUID.randomUUID();
        this.mealsList = new ArrayList<>();
    }

    public void addMeal(Meal meal) {
        this.mealsList.add(meal);
    }
}


