package com.example.restaurantapp.tutorialExcercise;


import com.example.restaurantapp.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("test")
public class TutorialController {

    private TutorialRepo tutorialRepo;

    @Autowired
    public TutorialController(TutorialRepo tutorialRepo) {
        this.tutorialRepo = tutorialRepo;
    }


}


