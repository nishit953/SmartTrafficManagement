package dao;

import java.sql.*;

public class VehicleLogDAO extends BaseDAO {
    public VehicleLogDAO() {
        init();
    }

    public VehicleLogDAO(String url, String user, String password) {
        super(url, user, password);
        init();
    }

    private void init() {
        String sql = "CREATE TABLE IF NOT EXISTS vehicles_log(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "vehicleNumber VARCHAR(64) NOT NULL, " +
                "action VARCHAR(16) NOT NULL, " + // ADDED | PROCESSED | DELETED
                "junctionId INT, " +
                "adminUser VARCHAR(64), " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "INDEX idx_vno(vehicleNumber))";
        try (Connection c = connect(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            System.err.println("[DB INIT vehicles_log] " + e.getMessage());
        }
    }

    public void record(String vno, String action, Integer junctionId, String admin) {
        String sql = "INSERT INTO vehicles_log(vehicleNumber,action,junctionId,adminUser,timestamp) VALUES(?,?,?,?,?)";
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, vno);
            ps.setString(2, action);
            if (junctionId == null)
                ps.setNull(3, Types.INTEGER);
            else
                ps.setInt(3, junctionId);
            ps.setString(4, admin);
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countAll() {
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM vehicles_log")) {
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String[][] countsByAction() {
        // We have only three actions; we can fill a static array
        String[] acts = new String[]{"ADDED", "PROCESSED", "DELETED"};
        String[][] rows = new String[acts.length][2];
        for (int i = 0; i < acts.length; i++) {
            rows[i][0] = acts[i];
            rows[i][1] = "0";
        }

        String sql = "SELECT action, COUNT(*) c FROM vehicles_log GROUP BY action";
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String a = rs.getString(1);
                String cnt = String.valueOf(rs.getInt(2));
                // place in rows
                for (int i = 0; i < acts.length; i++) {
                    if (acts[i].equals(a)) {
                        rows[i][1] = cnt;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}