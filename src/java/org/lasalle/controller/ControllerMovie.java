/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elena
 */
public class ControllerMovie {

    public List<Movie> getAll() throws SQLException {

        String sql = "SELECT * FROM movies";

        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        List<Movie> lista = new ArrayList<>();

        while(rs.next()){

            Movie m = new Movie();

            m.setId_movie(rs.getInt("id_movie"));
            m.setTitle(rs.getString("title"));
            m.setDescription(rs.getString("description"));
            m.setYear(rs.getInt("year"));
            m.setImage_url(rs.getString("image_url"));
            m.setId_genre(rs.getInt("id_genre"));
            m.setId_mood(rs.getInt("id_mood"));

            lista.add(m);
        }

        rs.close();
        conn.close();
        connMysql.close();

        return lista;
    }

    public Movie save(Movie m) throws SQLException {

        String sql =
        "INSERT INTO movies VALUES(0,?,?,?,?,?,?)";

        ConnectionMysql connMysql =
                new ConnectionMysql();

        Connection conn =
                connMysql.open();

        PreparedStatement pstm =
                conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS);

        pstm.setString(1, m.getTitle());
        pstm.setString(2, m.getDescription());
        pstm.setInt(3, m.getYear());
        pstm.setString(4, m.getImage_url());
        pstm.setInt(5, m.getId_genre());
        pstm.setInt(6, m.getId_mood());

        pstm.executeUpdate();

        ResultSet rs =
                pstm.getGeneratedKeys();

        while(rs.next()){

            m.setId_movie(rs.getInt(1));
        }

        pstm.close();
        conn.close();
        connMysql.close();

        return m;
    }
}
