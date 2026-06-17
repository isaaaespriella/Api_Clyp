/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.User;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elena
 */
public class ControllerUser {

    public List<User> getAll() throws SQLException {

        String sql = "SELECT * FROM users";

        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        List<User> lista = new ArrayList<>();

        while(rs.next()){

            User u = new User();

            u.setId_user(rs.getInt("id_user"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setCreated_at(formatIso(rs.getTimestamp("created_at")));

            lista.add(u);
        }

        rs.close();
        conn.close();
        connMysql.close();

        return lista;
    }

    public User save(User u) throws SQLException {

        String sql =
        "INSERT INTO users VALUES(0,?,?,?,NOW())";

        ConnectionMysql connMysql =
                new ConnectionMysql();

        Connection conn =
                connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS);

        pstm.setString(1, u.getName());
        pstm.setString(2, u.getEmail());
        String hashed = BCryptHelper.hash(u.getPassword());
        pstm.setString(3, hashed);

       try {

    pstm.executeUpdate();

    ResultSet rs = pstm.getGeneratedKeys();

    while (rs.next()) {
        u.setId_user(rs.getInt(1));
    }
    PreparedStatement fetch = conn.prepareStatement(
        "SELECT created_at FROM users WHERE id_user = ?");

fetch.setInt(1, u.getId_user());

ResultSet rs2 = fetch.executeQuery();

if (rs2.next()) {
    u.setCreated_at(
            formatIso(rs2.getTimestamp("created_at"))
    );
}

rs2.close();
fetch.close();

    rs.close();
    pstm.close();
    conn.close();
    connMysql.close();

    return u;

} catch (SQLIntegrityConstraintViolationException e) {

    pstm.close();
    conn.close();
    connMysql.close();

    return null;
}
    }
    
    
    
    
    
    
    
    private String formatIso(java.sql.Timestamp ts) {
    if (ts == null) return null;

    java.time.format.DateTimeFormatter fmt =
            java.time.format.DateTimeFormatter
                    .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    .withZone(java.time.ZoneOffset.UTC);

    return fmt.format(ts.toInstant());
}
    
    
    
    
    
    
    
    
    
    
    public User login(String email, String password) throws SQLException {

    String sql = "SELECT * FROM users WHERE email = ?";

    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();

    PreparedStatement pstm = conn.prepareStatement(sql);
    pstm.setString(1, email);

    ResultSet rs = pstm.executeQuery();

    User u = null;

    if (rs.next()) {

        String storedHash = rs.getString("password");

        if (BCryptHelper.verify(password, storedHash)) {

            u = new User();

            u.setId_user(rs.getInt("id_user"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
           u.setCreated_at(
        formatIso(rs.getTimestamp("created_at"))
);

            
        }
    }

    rs.close();
    pstm.close();
    conn.close();
    connMysql.close();

    return u;
}
   public User update(User u) throws SQLException {
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();
    PreparedStatement pstm = conn.prepareStatement(
        "UPDATE users SET name=?, email=? WHERE id_user=?");
    pstm.setString(1, u.getName());
    pstm.setString(2, u.getEmail());
    pstm.setInt(3, u.getId_user());

    try {
        int rows = pstm.executeUpdate();
        if (rows == 0) { pstm.close(); conn.close(); connMysql.close(); return null; }
    } catch (SQLIntegrityConstraintViolationException e) {
        pstm.close(); conn.close(); connMysql.close();
        throw e;
    }

    PreparedStatement fetch = conn.prepareStatement(
        "SELECT * FROM users WHERE id_user = ?");
    fetch.setInt(1, u.getId_user());
    ResultSet rs = fetch.executeQuery();
    User updated = null;
    if (rs.next()) {
        updated = new User();
        updated.setId_user(rs.getInt("id_user"));
        updated.setName(rs.getString("name"));
        updated.setEmail(rs.getString("email"));
        updated.setCreated_at(formatIso(rs.getTimestamp("created_at")));
    }
    rs.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
    return updated;
}

public boolean delete(int idUser) throws SQLException {
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();
    PreparedStatement pstm = conn.prepareStatement(
        "DELETE FROM users WHERE id_user = ?");
    pstm.setInt(1, idUser);
    int rows = pstm.executeUpdate();
    pstm.close(); conn.close(); connMysql.close();
    return rows > 0;
}
}
