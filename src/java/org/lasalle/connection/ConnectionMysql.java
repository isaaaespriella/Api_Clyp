package org.lasalle.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionMysql {

    Connection conn;

    public Connection open() {

        // Read credentials from environment variables (set in Render dashboard)
        String user     = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        String url      = System.getenv("DB_URL");

        // Fallback to hardcoded values for local development only
        if (user     == null) user     = "admin_clyp";
        if (password == null) password = "Clyp2026@";
        if (url      == null) url      =
            "jdbc:mysql://api-clyp.mysql.database.azure.com:3306/moodmovies"
            + "?sslMode=REQUIRED&useUnicode=true&characterEncoding=UTF-8";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}