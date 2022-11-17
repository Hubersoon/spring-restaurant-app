package com.example.restaurantapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

import static java.util.UUID.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "meals")
@NoArgsConstructor(force = true)
@AllArgsConstructor

public class Meal {

    @Id
    @GeneratedValue(generator = "UUID")
    public final UUID id;
    public final String name;
    public final Double price;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    public Meal(String name, Double price) {
        this.name = name;
        this.price = price;
        this.id = randomUUID();
    }

    public Meal(String name, Double price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
        this.id = randomUUID();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

