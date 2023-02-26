package com.autos.controller;

import com.autos.domain.Car;
import com.autos.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cars")
public class CarsController {
    @Autowired
    private CarService carService;
    @GetMapping("/getCars")
    public List<Car> getCars(){
        return carService.get();
    }

    @PostMapping("/submitCar")
    public String submitCar(@RequestBody Car car){

        carService.upsert(car);
        return "Success";
    }
}
