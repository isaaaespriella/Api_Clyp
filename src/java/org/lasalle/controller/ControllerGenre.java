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
}