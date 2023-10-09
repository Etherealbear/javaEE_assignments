package org.example.Car.Impl;

import org.example.Car.Car;

public class CarImpl implements Car {
    private int boughtYear;

    private String carname;

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public void setBoughtYear(int boughtYear) {
        this.boughtYear = boughtYear;
    }

    @Override
    public void running(){
        System.out.println("Car is running" + " and the car's boughtYear is "+ boughtYear + ", carname is "+carname);
    }
}
