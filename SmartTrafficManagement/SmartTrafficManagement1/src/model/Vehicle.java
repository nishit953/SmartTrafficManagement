package model;

public abstract class Vehicle {
    protected String vehicleNumber;
    protected boolean isEmergency;

    public Vehicle(String vehicleNumber, boolean isEmergency) {
        this.vehicleNumber = sanitizeNumber(vehicleNumber);
        this.isEmergency = isEmergency;
    }

    private static String sanitizeNumber(String n) {
        if (n == null) return "";
        return n.trim().toUpperCase();
    }

    public abstract void display();

    public boolean isEmergency() {
        return isEmergency;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
}