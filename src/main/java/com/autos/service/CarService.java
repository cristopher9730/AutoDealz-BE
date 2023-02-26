package com.autos.service;

import com.autos.domain.Car;
import com.autos.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public void upsert(Car car){
        if(car.getId() == null){
            car.setId(String.format("%s-%s-%s",car.getBrand(),car.getModel(), LocalDateTime.now()));
            carRepository.save(car);
            return;
        }
        carRepository.update(car);
    }
    public List<Car> get(){
        return carRepository.getAllCars();
    }

    public void delete(String carId){
        carRepository.delete(carId);
    }
}
