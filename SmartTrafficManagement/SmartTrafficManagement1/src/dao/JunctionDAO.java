package dao;

import java.sql.*;

public class JunctionDAO extends BaseDAO {
    public JunctionDAO() {
        init();
    }

    public JunctionDAO(String url, String user, String password) {
        super(url, user, password);
        init();
    }

    private void init() {
        String sql = "CREATE TABLE IF NOT EXISTS junction(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(128) UNIQUE NOT NULL, " +
                "location VARCHAR(256) NOT NULL)";
        try (Connection c = connect(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            System.err.println("[DB INIT junction] " + e.getMessage());
        }
    }

    public int ensureJunction(String name, String location) {
        Integer id = getJunctionIdByName(name);
        if (id != null)
            return id;
        String ins = "INSERT INTO junction(name,location) VALUES(?,?)";
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, location);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer again = getJunctionIdByName(name);
        return again == null ? -1 : again;
    }

    public Integer getJunctionIdByName(String name) {
        String q = "SELECT id FROM junction WHERE name=?";
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[][] getAll() {
        int count = 0;
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM junction")) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[][] rows = new String[count][3];
        int i = 0;
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT id,name,location FROM junction ORDER BY id")) {
            while (rs.next() && i < count) {
                rows[i][0] = String.valueOf(rs.getInt(1));
                rows[i][1] = rs.getString(2);
                rows[i][2] = rs.getString(3);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (i == count)
            return rows;

        String[][] shr = new String[i][3];
        for (int k = 0; k < i; k++) {
            shr[k][0] = rows[k][0];
            shr[k][1] = rows[k][1];
            shr[k][2] = rows[k][2];
        }
        return shr;
    }
}