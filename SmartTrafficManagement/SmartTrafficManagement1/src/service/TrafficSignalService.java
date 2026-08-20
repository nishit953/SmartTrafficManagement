// File: service/TrafficSignalService.java
package service;

import model.Vehicle;

public interface TrafficSignalService {
    void addVehicle(Vehicle v);
    void processTraffic();
    boolean searchVehicle(String number);
    void deleteVehicle(String number);
    int getQueueSize();
    Vehicle[] getAllVehicles();
}