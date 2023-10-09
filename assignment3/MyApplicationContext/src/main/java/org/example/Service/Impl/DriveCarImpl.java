package org.example.Service.Impl;

import org.example.Car.Car;
import org.example.Service.DriveCar;

public class DriveCarImpl implements DriveCar {
    private Car car;
    @Override
    public void running() {
        System.out.println("DriveCarService is saving...");
        car.running();
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
