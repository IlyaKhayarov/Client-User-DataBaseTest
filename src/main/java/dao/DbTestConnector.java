package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbTestConnector {

    private static final String dbTestUrl = "jdbc:mysql://localhost/test_db" +
            "?verifyServerCertificate=false" +
            "&serverTimezone=UTC";
    private static final String name = "root";
    private static final String pass = "!Kg8ikz5bVR";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbTestUrl, name, pass);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
