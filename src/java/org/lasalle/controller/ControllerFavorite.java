/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

/**
 *
 * @author elena
 */
import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.Favorite;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerFavorite {

    public List<Favorite> getByUser(int idUser) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "SELECT * FROM favorites WHERE id_user = ? ORDER BY favorited_at DESC");
        pstm.setInt(1, idUser);
        ResultSet rs = pstm.executeQuery();
        List<Favorite> lista = new ArrayList<>();
        while (rs.next()) {
            Favorite f = new Favorite();
            f.setId_favorite(rs.getInt("id_favorite"));
            f.setId_user(rs.getInt("id_user"));
            f.setId_movie(rs.getInt("id_movie"));
            f.setFavorited_at(formatIso(rs.getTimestamp("favorited_at")));
            lista.add(f);
        }
        rs.close(); conn.close(); connMysql.close();
        return lista;
    }

    public Favorite save(Favorite f) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "INSERT INTO favorites(id_user, id_movie, favorited_at) VALUES(?,?,NOW())",
            Statement.RETURN_GENERATED_KEYS);
        pstm.setInt(1, f.getId_user());
        pstm.setInt(2, f.getId_movie());
        pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        if (rs.next()) f.setId_favorite(rs.getInt(1));

        PreparedStatement fetch = conn.prepareStatement(
            "SELECT favorited_at FROM favorites WHERE id_favorite = ?");
        fetch.setInt(1, f.getId_favorite());
        ResultSet rs2 = fetch.executeQuery();
        if (rs2.next()) f.setFavorited_at(formatIso(rs2.getTimestamp("favorited_at")));

        rs.close(); rs2.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
        return f;
    }

    public boolean delete(int idFavorite) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "DELETE FROM favorites WHERE id_favorite = ?");
        pstm.setInt(1, idFavorite);
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
