package com.autos.domain;

import lombok.Data;

@Data
public class Car {
    private String id;
    private String plateNumber;
    private String color;
    private String brand;
    private String model;
    private String type;
    private String year;
    private double price;
    private String picture;
    private String fuel;
    private String odometer;
    private String transmission;
    private String cylinders;
    private boolean sold;
}
