package model;

import java.time.LocalDateTime;

public class ViolationRecord {
    public final String vehicleNumber;
    public final int typeCode;
    public final LocalDateTime time;
    public final Integer junctionId; // nullable
    public final String adminUser;   // nullable

    public ViolationRecord(String vehicleNumber, int typeCode, LocalDateTime time) {
        this(vehicleNumber, typeCode, time, null, null);
    }

    public ViolationRecord(String vehicleNumber, int typeCode, LocalDateTime time, Integer junctionId, String adminUser) {
        this.vehicleNumber = vehicleNumber;
        this.typeCode = typeCode;
        this.time = time;
        this.junctionId = junctionId;
        this.adminUser = adminUser;
    }
}