/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.MoodCheckin;

import java.sql.*;
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

       PreparedStatement pstm =
        conn.prepareStatement(sql);

           if (idUser > 0) {
           pstm.setInt(1, idUser);
}

        ResultSet rs = pstm.executeQuery();

        List<MoodCheckin> lista = new ArrayList<>();

        while(rs.next()){

            MoodCheckin m = new MoodCheckin();

            m.setId_checkin(rs.getInt("id_checkin"));
            m.setId_user(rs.getInt("id_user"));
            m.setId_mood(rs.getInt("id_mood"));
            m.setCheckin_time(formatIso(rs.getTimestamp("checkin_time")));

            lista.add(m);
        }

        return lista;
    }

    public MoodCheckin save(MoodCheckin m)
            throws SQLException {

        String sql =
        "INSERT INTO mood_checkins VALUES(0,?,?,NOW())";

        ConnectionMysql connMysql =
                new ConnectionMysql();

        Connection conn =
                connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS);

        pstm.setInt(1, m.getId_user());
        pstm.setInt(2, m.getId_mood());

        pstm.executeUpdate();

        ResultSet rs =
                pstm.getGeneratedKeys();

        while(rs.next()){

            m.setId_checkin(rs.getInt(1));
        }

        return m;
        
        
    }
    private String formatIso(java.sql.Timestamp ts) {
    if (ts == null) return null;

    java.time.format.DateTimeFormatter fmt =
            java.time.format.DateTimeFormatter
                    .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    .withZone(java.time.ZoneOffset.UTC);

    return fmt.format(ts.toInstant());
}
}
