package dao;

import model.ViolationRecord;
import model.TrafficViolation;

import java.sql.*;
import java.time.LocalDateTime;

public class ViolationDAO extends BaseDAO {
    public ViolationDAO() {
        init();
    }

    public ViolationDAO(String url, String user, String password) {
        super(url, user, password);
        init();
    }

    private void init() {
        String sql = "CREATE TABLE IF NOT EXISTS violations(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "vehicleNumber VARCHAR(64) NOT NULL, " +
                "typeCode INT NOT NULL, " +
                "reason TEXT NOT NULL, " +
                "junctionId INT, " +
                "adminUser VARCHAR(64), " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "INDEX idx_vtype(typeCode), INDEX idx_vno2(vehicleNumber))";
        try (Connection c = connect(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            System.err.println("[DB INIT violations] " + e.getMessage());
        }
    }

    public void log(ViolationRecord rec) {
        String sql = "INSERT INTO violations(vehicleNumber,typeCode,reason,junctionId,adminUser,timestamp) VALUES(?,?,?,?,?,?)";
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, rec.vehicleNumber);
            ps.setInt(2, rec.typeCode);
            ps.setString(3, TrafficViolation.getViolationName(rec.typeCode));
            if (rec.junctionId == null) ps.setNull(4, Types.INTEGER);
            else ps.setInt(4, rec.junctionId);
            ps.setString(5, rec.adminUser);
            ps.setTimestamp(6, Timestamp.valueOf(rec.time));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getViolationCount() {
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM violations")) {
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int[] getCountsByType() {
        int[] counts = new int[13];
        String sql = "SELECT typeCode, COUNT(*) c FROM violations GROUP BY typeCode";
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int code = rs.getInt(1);
                int cnt = rs.getInt(2);
                if (code >= 1 && code <= 12)
                    counts[code] = cnt;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counts;
    }

    public String[][] getTopOffenders(int limit) {
        if (limit < 1)
            limit = 1;
        if (limit > 50)
            limit = 50;

        String[][] rows = new String[limit][2];
        int i = 0;
        String sql = "SELECT vehicleNumber, COUNT(*) c FROM violations GROUP BY vehicleNumber ORDER BY c DESC, vehicleNumber ASC LIMIT " + limit;
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next() && i < limit) {
                rows[i][0] = rs.getString(1);
                rows[i][1] = String.valueOf(rs.getInt(2));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[][] shr = new String[i][2];
        for (int k = 0; k < i; k++) {
            shr[k][0] = rows[k][0];
            shr[k][1] = rows[k][1];
        }
        return shr;
    }

    public ViolationRecord[] getViolationsForVehicle(String vehicle) {
        int count = 0;
        String csql = "SELECT COUNT(*) FROM violations WHERE vehicleNumber=?";
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(csql)) {
            ps.setString(1, vehicle);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (count > 100)
            count = 100;

        ViolationRecord[] arr = new ViolationRecord[count];
        int i = 0;
        String sql = "SELECT typeCode, timestamp, junctionId, adminUser FROM violations WHERE vehicleNumber=? ORDER BY timestamp DESC LIMIT 100";
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, vehicle);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next() && i < count) {
                    int code = rs.getInt(1);
                    Timestamp ts = rs.getTimestamp(2);
                    Integer jId = (Integer) rs.getObject(3);
                    String admin = rs.getString(4);
                    arr[i++] = new ViolationRecord(vehicle, code, ts.toLocalDateTime(), jId, admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (i == count)
            return arr;

        ViolationRecord[] shr = new ViolationRecord[i];
        for (int k = 0; k < i; k++)
            shr[k] = arr[k];
        return shr;
    }
}