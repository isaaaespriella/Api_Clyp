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

    public List<WatchedMovie> getAll(int idUser)
            throws SQLException {

        String sql = idUser > 0
        ? "SELECT * FROM watched_movies WHERE id_user = ? ORDER BY watched_at DESC"
        : "SELECT * FROM watched_movies ORDER BY watched_at DESC";

        ConnectionMysql connMysql =
                new ConnectionMysql();

        Connection conn =
                connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(sql);
        if (idUser > 0) {
    pstm.setInt(1, idUser);
}

        ResultSet rs =
                pstm.executeQuery();

        List<WatchedMovie> lista =
                new ArrayList<>();

        while(rs.next()){

            WatchedMovie w =
                    new WatchedMovie();

            w.setId(rs.getInt("id"));
            w.setId_user(rs.getInt("id_user"));
            w.setId_movie(rs.getInt("id_movie"));
            w.setWatched_at(
            formatIso(rs.getTimestamp("watched_at"))
);

            lista.add(w);
        }

        return lista;
    }

  public WatchedMovie save(
        WatchedMovie w)
        throws SQLException {

    String sql =
    "INSERT INTO watched_movies VALUES(0,?,?,NOW())";

    ConnectionMysql connMysql =
            new ConnectionMysql();

    Connection conn =
            connMysql.open();

    // CHECK FOR EXISTING RECORD
    PreparedStatement check = conn.prepareStatement(
            "SELECT * FROM watched_movies WHERE id_user = ? AND id_movie = ?");

    check.setInt(1, w.getId_user());
    check.setInt(2, w.getId_movie());

    ResultSet existing = check.executeQuery();

    if (existing.next()) {

        w.setId(existing.getInt("id"));

        w.setWatched_at(
                formatIso(
                        existing.getTimestamp("watched_at")
                )
        );

        existing.close();
        check.close();
        conn.close();
        connMysql.close();

        return w;
    }

    existing.close();
    check.close();

    // INSERT ONLY IF NOT FOUND
    PreparedStatement pstm =
            conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

    pstm.setInt(1, w.getId_user());
    pstm.setInt(2, w.getId_movie());

    pstm.executeUpdate();

    ResultSet rs =
            pstm.getGeneratedKeys();

    while(rs.next()) {
        w.setId(rs.getInt(1));
    }

    return w;
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
