package com.example.restaurantapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "meals")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Meal {

    @Id
    @GeneratedValue(generator = "UUID")
    public final UUID id;
    public final String name;
    public final Double price;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    public Meal(String name, Double price) {
        this.name = name;
        this.price = price;
        this.id = UUID.randomUUID();
    }



}
