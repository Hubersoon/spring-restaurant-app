package com.example.restaurantapp.model;

import lombok.*;
import org.hibernate.annotations.IdGeneratorType;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Restaurant {

    @Column(name = "name")
    public final String name;
    @Column(name = "address")
    public final String address;
//    @Column(name = "type")
    public final RestaurantType type;
    @Id
    @GeneratedValue(generator = "UUID")
    public final UUID id;
//    @Column(name = "meals_list")
    public final List<Meal> mealsList;


    public Restaurant(String name, String address, RestaurantType type) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.id = UUID.randomUUID();
        this.mealsList = new ArrayList<>();
    }


    public boolean addMeal(Meal meal) {
        return this.mealsList.add(meal);
    }

    public Restaurant updateName(String name) {
        return Restaurant.builder()
                .name(name)
                .address(address)
                .type(type)
                .id(id)
                .mealsList(mealsList)
                .build();
    }

    public Restaurant updateAddress(String address) {
        return Restaurant.builder()
                .name(name)
                .address(address)
                .type(type)
                .id(id)
                .mealsList(mealsList)
                .build();
    }

    public Restaurant updateRestaurantType(RestaurantType type) {
        return Restaurant.builder()
                .name(name)
                .address(address)
                .type(type)
                .id(id)
                .mealsList(mealsList)
                .build();
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Restaurant)) return false;
//
//        Restaurant that = (Restaurant) o;
//
//        if (!getName().equals(that.getName())) return false;
//        if (!address.equals(that.address)) return false;
//        if (type != that.type) return false;
//        return getId().equals(that.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        int result = getName().hashCode();
//        result = 31 * result + address.hashCode();
//        result = 31 * result + type.hashCode();
//        result = 31 * result + getId().hashCode();
//        return result;
//    }
}


