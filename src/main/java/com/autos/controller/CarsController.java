package com.autos.controller;

import com.autos.AppProperties;
import com.autos.domain.Car;
import com.autos.repository.CarRepository;
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
    CarRepository carRepository;
    @Autowired
    AppProperties appProperties;
    @GetMapping("/getCars")
    public List<Car> getCars(){
        return carRepository.getAllCars();
    }
}
