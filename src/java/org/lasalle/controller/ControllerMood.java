/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.Mood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elena
 */
public class ControllerMood {

    public List<Mood> getAll() throws SQLException {

        String sql = "SELECT * FROM moods";

        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        List<Mood> lista = new ArrayList<>();

        while(rs.next()){

            Mood m = new Mood();

            m.setId_mood(rs.getInt("id_mood"));
            m.setName(rs.getString("name"));
            m.setDescription(rs.getString("description"));

            lista.add(m);
        }

        rs.close();
        conn.close();
        connMysql.close();

        return lista;
    }

    public Mood save(Mood m) throws SQLException {

        String sql =
        "INSERT INTO moods VALUES(0,?,?)";

        ConnectionMysql connMysql =
                new ConnectionMysql();

        Connection conn =
                connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS);

        pstm.setString(1, m.getName());
        pstm.setString(2, m.getDescription());

        pstm.executeUpdate();

        ResultSet rs =
                pstm.getGeneratedKeys();

        while(rs.next()){

            m.setId_mood(rs.getInt(1));
        }

        pstm.close();
        conn.close();
        connMysql.close();

        return m;
    }
    
    public Mood update(Mood m) throws SQLException {
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();
    PreparedStatement pstm = conn.prepareStatement(
        "UPDATE moods SET name=?, description=? WHERE id_mood=?");
    pstm.setString(1, m.getName());
    pstm.setString(2, m.getDescription());
    pstm.setInt(3, m.getId_mood());
    int rows = pstm.executeUpdate();
    if (rows == 0) { pstm.close(); conn.close(); connMysql.close(); return null; }

    PreparedStatement fetch = conn.prepareStatement(
        "SELECT * FROM moods WHERE id_mood = ?");
    fetch.setInt(1, m.getId_mood());
    ResultSet rs = fetch.executeQuery();
    Mood updated = null;
    if (rs.next()) {
        updated = new Mood();
        updated.setId_mood(rs.getInt("id_mood"));
        updated.setName(rs.getString("name"));
        updated.setDescription(rs.getString("description"));
    }
    rs.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
    return updated;
}

public boolean delete(int idMood) throws SQLException {
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();

    PreparedStatement check = conn.prepareStatement(
        "SELECT COUNT(*) FROM movies WHERE id_mood = ?");
    check.setInt(1, idMood);
    ResultSet rs1 = check.executeQuery();
    int movieCount = rs1.next() ? rs1.getInt(1) : 0;
    rs1.close(); check.close();

    PreparedStatement check2 = conn.prepareStatement(
        "SELECT COUNT(*) FROM mood_checkins WHERE id_mood = ?");
    check2.setInt(1, idMood);
    ResultSet rs2 = check2.executeQuery();
    int checkinCount = rs2.next() ? rs2.getInt(1) : 0;
    rs2.close(); check2.close();

    if (movieCount > 0 || checkinCount > 0) {
        conn.close(); connMysql.close();
        throw new SQLIntegrityConstraintViolationException("mood in use");
    }

    PreparedStatement pstm = conn.prepareStatement(
        "DELETE FROM moods WHERE id_mood = ?");
    pstm.setInt(1, idMood);
    int rows = pstm.executeUpdate();
    pstm.close(); conn.close(); connMysql.close();
    return rows > 0;
 }
}