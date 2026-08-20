package model;

public class TrafficViolation {
    public static final int RED_LIGHT_JUMP = 1;
    public static final int WRONG_SIDE = 2;
    public static final int NO_HELMET_OR_SEATBELT = 3;
    public static final int OVERSPEEDING = 4;
    public static final int DRINK_AND_DRIVE = 5;
    public static final int RECKLESS_DRIVING = 6;
    public static final int MOBILE_USE = 7;
    public static final int NO_VALID_LICENSE = 8;
    public static final int NO_INSURANCE = 9;
    public static final int NO_WAY_FOR_EMERGENCY = 10;
    public static final int OVERLOADING_PASSENGERS = 11;
    public static final int NO_VALID_PUC = 12;

    public static String getViolationName(int code) {
        switch (code) {
            case RED_LIGHT_JUMP: return "Jumped red light.";
            case WRONG_SIDE: return "Driving in wrong side.";
            case NO_HELMET_OR_SEATBELT: return "No helmet or seatbelt.";
            case OVERSPEEDING: return "Overspeeding.";
            case DRINK_AND_DRIVE: return "Drink and drive.";
            case RECKLESS_DRIVING: return "Reckless driving.";
            case MOBILE_USE: return "Using a mobile while driving.";
            case NO_VALID_LICENSE: return "Driving without a valid license.";
            case NO_INSURANCE: return "Driving without insurance.";
            case NO_WAY_FOR_EMERGENCY: return "No providing a way for emergency vehicle/s.";
            case OVERLOADING_PASSENGERS: return "Overloading passengers.";
            case NO_VALID_PUC: return "Driving without a valid PUC.";
            default: return "Unknown Violation";
        }
    }
}