/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.WatchedMovie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elena
 */


public class ControllerWatchedMovie {

    public List<WatchedMovie> getAll(int idUser) throws SQLException {
        String sql = idUser > 0
            ? "SELECT * FROM watched_movies WHERE id_user = ? ORDER BY watched_at DESC"
            : "SELECT * FROM watched_movies ORDER BY watched_at DESC";

        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        if (idUser > 0) pstm.setInt(1, idUser);

        ResultSet rs = pstm.executeQuery();
        List<WatchedMovie> lista = new ArrayList<>();

        while (rs.next()) {
            WatchedMovie w = new WatchedMovie();
            w.setId_watched(rs.getInt("id"));   // columna en DB sigue llamándose "id"
            w.setId_user(rs.getInt("id_user"));
            w.setId_movie(rs.getInt("id_movie"));
            w.setWatched_at(formatIso(rs.getTimestamp("watched_at")));
            lista.add(w);
        }
        rs.close(); conn.close(); connMysql.close();
        return lista;
    }

    public WatchedMovie save(WatchedMovie w) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();

        // Check si ya existe
        PreparedStatement check = conn.prepareStatement(
            "SELECT * FROM watched_movies WHERE id_user = ? AND id_movie = ?");
        check.setInt(1, w.getId_user());
        check.setInt(2, w.getId_movie());
        ResultSet existing = check.executeQuery();

        if (existing.next()) {
            w.setId_watched(existing.getInt("id"));
            w.setWatched_at(formatIso(existing.getTimestamp("watched_at")));
            existing.close(); check.close(); conn.close(); connMysql.close();
            return w;
        }
        existing.close(); check.close();

        PreparedStatement pstm = conn.prepareStatement(
            "INSERT INTO watched_movies VALUES(0,?,?,NOW())",
            Statement.RETURN_GENERATED_KEYS);
        pstm.setInt(1, w.getId_user());
        pstm.setInt(2, w.getId_movie());
        pstm.executeUpdate();

        ResultSet rs = pstm.getGeneratedKeys();
        if (rs.next()) w.setId_watched(rs.getInt(1));

        // Fetch watched_at real
        PreparedStatement fetch = conn.prepareStatement(
            "SELECT watched_at FROM watched_movies WHERE id = ?");
        fetch.setInt(1, w.getId_watched());
        ResultSet rs2 = fetch.executeQuery();
        if (rs2.next()) w.setWatched_at(formatIso(rs2.getTimestamp("watched_at")));

        rs.close(); rs2.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
        return w;
    }

    public boolean delete(int idWatched) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "DELETE FROM watched_movies WHERE id = ?");
        pstm.setInt(1, idWatched);
        int rows = pstm.executeUpdate();
        pstm.close(); conn.close(); connMysql.close();
        return rows > 0;
    }

    private String formatIso(java.sql.Timestamp ts) {
        if (ts == null) return null;
        return java.time.format.DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(java.time.ZoneOffset.UTC)
            .format(ts.toInstant());
    }
}