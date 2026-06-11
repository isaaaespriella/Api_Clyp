/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.connection;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author elena
 */
public class ConnectionMysql {
    Connection conn;
    
    //Metodo para establecer conexion con mysql
    public Connection open(){
        //Declaramos variables para credenciales
        String user = "admin_clyp";
        String password = "Clyp2026@";
        
        String dbName = "moodmovies";
String url =
"jdbc:mysql://api-clyp.mysql.database.azure.com:3306/moodmovies?sslMode=REQUIRED&useUnicode=true&characterEncoding=UTF-8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void close(){
        if(conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}