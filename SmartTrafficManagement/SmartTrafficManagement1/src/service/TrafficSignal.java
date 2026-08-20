package service;

import dao.VehicleLogDAO;
import dao.ViolationDAO;
import datastructures.VehicleBST;
import model.Vehicle;
import model.ViolationRecord;

import java.time.LocalDateTime;

public class TrafficSignal implements TrafficSignalService, Runnable {
    private final VehicleBST emergency = new VehicleBST();
    private final VehicleBST regular = new VehicleBST();

    private final ViolationDAO violations;
    private final VehicleLogDAO vlogs;

    private final Vehicle[] inOrderList = new Vehicle[5000];
    private int mergedCount = 0;

    private int state = SignalState.RED;
    private long greenMillis = 1500, yellowMillis = 600, redMillis = 800;
    private Integer junctionId;
    private String adminUser;

    public TrafficSignal(ViolationDAO vio, VehicleLogDAO vlogs, Integer junctionId, String adminUser) {
        this.violations = vio;
        this.vlogs = vlogs;
        this.junctionId = junctionId;
        this.adminUser = adminUser;
        System.out.println("Signal is: " + SignalState.getStateName(state));
    }

    public synchronized void configureDurations(long redMs, long yellowMs, long greenMs) {
        this.redMillis = redMs < 100 ? 100 : redMs;
        this.yellowMillis = yellowMs < 100 ? 100 : yellowMs;
        this.greenMillis = greenMs < 100 ? 100 : greenMs;
    }

    @Override
    public synchronized void addVehicle(Vehicle v) {
        if (v == null || v.getVehicleNumber().isEmpty()) {
            System.out.println("[WARN] Ignored invalid vehicle.");
            return;
        }
        if (v.isEmergency())
            emergency.insert(v);
        else
            regular.insert(v);
        vlogs.record(v.getVehicleNumber(), "ADDED", junctionId, adminUser);
    }

    @Override
    public synchronized boolean searchVehicle(String number) {
        return emergency.contains(number) || regular.contains(number);
    }

    @Override
    public synchronized void deleteVehicle(String number) {
        String sanitizedNumber = sanitizeNumber(number);
        boolean deletedFromEmergency = emergency.delete(sanitizedNumber);
        boolean deletedFromRegular = regular.delete(sanitizedNumber);

        if (deletedFromEmergency || deletedFromRegular) {
            vlogs.record(sanitizedNumber, "DELETED", junctionId, adminUser);
            System.out.println("Vehicle " + sanitizedNumber + " deleted.");
        } else {
            System.out.println("Vehicle " + sanitizedNumber + " not found.");
        }
    }

    @Override
    public synchronized int getQueueSize() {
        return emergency.size() + regular.size();
    }

    private synchronized void rebuildMerged() {
        mergedCount = 0;
        mergedCount += emergency.toInOrderArray(inOrderList, mergedCount);
        mergedCount += regular.toInOrderArray(inOrderList, mergedCount);
    }

    @Override
    public void processTraffic() {
        rebuildMerged();
        if (mergedCount == 0) {
            System.out.println("No vehicles in queue.");
            return;
        }
        System.out.println(" [PROCESS] Starting lane flow. Total vehicles: " + mergedCount);
        for (int i = 0; i < mergedCount; i++) {
            Vehicle v = inOrderList[i];
            changeState(SignalState.GREEN);
            v.display();
            sleep(greenMillis);
            changeState(SignalState.YELLOW);
            sleep(yellowMillis);
            changeState(SignalState.RED);
            sleep(redMillis);
            vlogs.record(v.getVehicleNumber(), "PROCESSED", junctionId, adminUser);
        }
        System.out.println("[PROCESS] Completed.");
    }

    private void changeState(int s) {
        this.state = s;
        System.out.println("Signal -> " + SignalState.getStateName(s));
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    @Override
    public void run() {
        processTraffic();
    }

    @Override
    public synchronized Vehicle[] getAllVehicles() {
        rebuildMerged();
        Vehicle[] out = new Vehicle[mergedCount];
        for (int i = 0; i < mergedCount; i++)
            out[i] = inOrderList[i];
        return out;
    }

    public int getState() {
        return state;
    }

    public void logViolation(String vehicleNumber, int typeCode) {
        if (typeCode < 1 || typeCode > 12) {
            System.out.println("[WARN] Invalid violation code.");
            return;
        }
        ViolationRecord r = new ViolationRecord(vehicleNumber.toUpperCase(), typeCode, LocalDateTime.now(), junctionId, adminUser);
        violations.log(r);
        System.out.println("[DB] Violation logged: " + vehicleNumber + " -> " +model.TrafficViolation.getViolationName(typeCode));
    }

    private String sanitizeNumber(String n) {
        if (n == null) return "";
        return n.trim().toUpperCase();
    }
}