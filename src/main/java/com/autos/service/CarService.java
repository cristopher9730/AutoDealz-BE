package com.autos.service;

import com.autos.domain.Car;
import com.autos.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public void upsert(Car car){
        if(car.getId() != null){
            carRepository.update(car);
            return;
        }
        carRepository.save(car);
    }
    public List<Car> get(){
        return carRepository.getAllCars();
    }
}
