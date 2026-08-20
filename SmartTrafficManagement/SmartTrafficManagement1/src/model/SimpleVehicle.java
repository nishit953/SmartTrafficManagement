package model;

public class SimpleVehicle extends Vehicle {
    public SimpleVehicle(String number, boolean isEmergency) {
        super(number, isEmergency);
    }

    @Override
    public void display() {
        System.out.println("Vehicle: " + vehicleNumber + " | Emergency: " + isEmergency);}
}