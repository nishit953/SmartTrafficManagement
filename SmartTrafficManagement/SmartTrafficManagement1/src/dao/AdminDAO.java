package dao;

import java.sql.*;

public class AdminDAO extends BaseDAO {
    public AdminDAO() {
        init();
    }

    public AdminDAO(String url, String user, String password) {
        super(url, user, password);
        init();
    }

    private void init() {
        String sql = "CREATE TABLE IF NOT EXISTS admins(" +
                "username VARCHAR(64) PRIMARY KEY, " +
                "password VARCHAR(128) NOT NULL)";
        try (Connection c = connect(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            System.err.println("[DB INIT admins] " + e.getMessage());
        }

        // seed admin/admin if empty
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM admins")) {
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement ps = c.prepareStatement("INSERT INTO admins(username,password) VALUES('admin','admin')")) {
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        String sql = "SELECT 1 FROM admins WHERE username=? AND password=?";
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}