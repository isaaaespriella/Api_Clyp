/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.MoodCheckin;

import java.sql.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elena
 */

public class ControllerMoodCheckin {

    public List<MoodCheckin> getAll(int idUser) throws SQLException {
        String sql = idUser > 0
            ? "SELECT * FROM mood_checkins WHERE id_user = ? ORDER BY checkin_time DESC"
            : "SELECT * FROM mood_checkins ORDER BY checkin_time DESC";

        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        if (idUser > 0) pstm.setInt(1, idUser);

        ResultSet rs = pstm.executeQuery();
        List<MoodCheckin> lista = new ArrayList<>();

        while (rs.next()) {
            MoodCheckin m = new MoodCheckin();
            m.setId_checkin(rs.getInt("id_checkin"));
            m.setId_user(rs.getInt("id_user"));
            m.setId_mood(rs.getInt("id_mood"));
            m.setCheckin_time(formatIso(rs.getTimestamp("checkin_time")));
            lista.add(m);
        }
        rs.close(); conn.close(); connMysql.close();
        return lista;
    }

    public MoodCheckin save(MoodCheckin m) throws SQLException {
        Timestamp t = parseOrNow(m.getCheckin_time());

        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "INSERT INTO mood_checkins(id_user, id_mood, checkin_time) VALUES(?,?,?)",
            Statement.RETURN_GENERATED_KEYS);

        pstm.setInt(1, m.getId_user());
        pstm.setInt(2, m.getId_mood());
        pstm.setTimestamp(3, t);

        pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        if (rs.next()) m.setId_checkin(rs.getInt(1));

        PreparedStatement fetch = conn.prepareStatement(
            "SELECT checkin_time FROM mood_checkins WHERE id_checkin = ?");
        fetch.setInt(1, m.getId_checkin());
        ResultSet rs2 = fetch.executeQuery();
        if (rs2.next()) m.setCheckin_time(formatIso(rs2.getTimestamp("checkin_time")));

        rs.close(); rs2.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
        return m;
    }

    public MoodCheckin update(MoodCheckin m) throws SQLException {
        Timestamp t = parseOrNow(m.getCheckin_time());

        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "UPDATE mood_checkins SET id_mood=?, checkin_time=? WHERE id_checkin=?");
        pstm.setInt(1, m.getId_mood());
        pstm.setTimestamp(2, t);
        pstm.setInt(3, m.getId_checkin());
        int rows = pstm.executeUpdate();

        if (rows == 0) { pstm.close(); conn.close(); connMysql.close(); return null; }

        PreparedStatement fetch = conn.prepareStatement(
            "SELECT * FROM mood_checkins WHERE id_checkin = ?");
        fetch.setInt(1, m.getId_checkin());
        ResultSet rs = fetch.executeQuery();
        MoodCheckin updated = null;
        if (rs.next()) {
            updated = new MoodCheckin();
            updated.setId_checkin(rs.getInt("id_checkin"));
            updated.setId_user(rs.getInt("id_user"));
            updated.setId_mood(rs.getInt("id_mood"));
            updated.setCheckin_time(formatIso(rs.getTimestamp("checkin_time")));
        }
        rs.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
        return updated;
    }

    public boolean delete(int idCheckin) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "DELETE FROM mood_checkins WHERE id_checkin = ?");
        pstm.setInt(1, idCheckin);
        int rows = pstm.executeUpdate();
        pstm.close(); conn.close(); connMysql.close();
        return rows > 0;
    }

    private Timestamp parseOrNow(String iso) {
        if (iso == null || iso.isBlank()) {
            return Timestamp.from(Instant.now());
        }
        return Timestamp.from(Instant.parse(iso));
    }

    private String formatIso(java.sql.Timestamp ts) {
        if (ts == null) return null;
        return java.time.format.DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(java.time.ZoneOffset.UTC)
            .format(ts.toInstant());
    }
}