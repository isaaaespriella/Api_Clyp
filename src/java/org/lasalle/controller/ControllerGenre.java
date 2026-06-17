/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elena
 */
public class ControllerGenre {

    public List<Genre> getAll() throws SQLException {

        String sql = "SELECT * FROM genres";

        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        List<Genre> lista = new ArrayList<>();

        while(rs.next()){

            Genre g = new Genre();

            g.setId_genre(rs.getInt("id_genre"));
            g.setName(rs.getString("name"));

            lista.add(g);
        }

        rs.close();
        conn.close();
        connMysql.close();

        return lista;
    }

    public Genre save(Genre g) throws SQLException {

        String sql =
                "INSERT INTO genres VALUES(0,?)";

        ConnectionMysql connMysql =
                new ConnectionMysql();

        Connection conn =
                connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS);

        pstm.setString(1, g.getName());

        pstm.executeUpdate();

        ResultSet rs =
                pstm.getGeneratedKeys();

        while(rs.next()){

            g.setId_genre(rs.getInt(1));
        }

        pstm.close();
        conn.close();
        connMysql.close();

        return g;
    }
    
    
    public Genre update(Genre g) throws SQLException {
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();
    PreparedStatement pstm = conn.prepareStatement(
        "UPDATE genres SET name=? WHERE id_genre=?");
    pstm.setString(1, g.getName());
    pstm.setInt(2, g.getId_genre());

    try {
        int rows = pstm.executeUpdate();
        if (rows == 0) { pstm.close(); conn.close(); connMysql.close(); return null; }
    } catch (SQLIntegrityConstraintViolationException e) {
        pstm.close(); conn.close(); connMysql.close();
        throw e;
    }

    PreparedStatement fetch = conn.prepareStatement(
        "SELECT * FROM genres WHERE id_genre = ?");
    fetch.setInt(1, g.getId_genre());
    ResultSet rs = fetch.executeQuery();
    Genre updated = null;
    if (rs.next()) {
        updated = new Genre();
        updated.setId_genre(rs.getInt("id_genre"));
        updated.setName(rs.getString("name"));
    }
    rs.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
    return updated;
}

public boolean delete(int idGenre) throws SQLException {
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();

    PreparedStatement check = conn.prepareStatement(
        "SELECT COUNT(*) FROM movies WHERE id_genre = ?");
    check.setInt(1, idGenre);
    ResultSet rs = check.executeQuery();
    if (rs.next() && rs.getInt(1) > 0) {
        rs.close(); check.close(); conn.close(); connMysql.close();
        throw new SQLIntegrityConstraintViolationException("genre in use");
    }
    rs.close(); check.close();

    PreparedStatement pstm = conn.prepareStatement(
        "DELETE FROM genres WHERE id_genre = ?");
    pstm.setInt(1, idGenre);
    int rows = pstm.executeUpdate();
    pstm.close(); conn.close(); connMysql.close();
    return rows > 0;
}
}