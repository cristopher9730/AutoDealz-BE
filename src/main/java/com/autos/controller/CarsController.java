package com.autos.controller;

import com.autos.AppProperties;
import com.autos.domain.Car;
import com.autos.repository.CarRepository;
import com.autos.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
