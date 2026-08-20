// File: dao/BaseDAO.java
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {
    protected String url = "jdbc:mysql://localhost:3306/trafficdb";
    protected String user = "root";
    protected String password = ""; // set your password

    BaseDAO() {}

    BaseDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}