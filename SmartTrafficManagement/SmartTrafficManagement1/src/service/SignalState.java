package service;

public class SignalState {
    public static final int RED = 1, YELLOW = 2, GREEN = 3;

    public static String getStateName(int s) {
        switch (s) {
            case RED: return "RED";
            case YELLOW: return "YELLOW";
            case GREEN: return "GREEN";
            default: return "UNKNOWN";
        }
    }
}