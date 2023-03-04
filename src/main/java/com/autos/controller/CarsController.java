package com.autos.controller;

import com.autos.domain.Car;
import com.autos.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(value="/submitCar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String submitCar(@RequestPart("file") MultipartFile file, @RequestPart("car") String car) throws IOException {
        carService.processCar(file,car);
        return "Success";
    }
    @GetMapping("/deleteCar")
    public String deleteCar(@RequestParam String carId){
        carService.delete(carId);
        return "Success";
    }
}
