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
    public List<Movie> getByMood(int idMood) throws SQLException {
    String sql = "SELECT * FROM movies WHERE id_mood = ?";
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();
    PreparedStatement pstm = conn.prepareStatement(sql);
    pstm.setInt(1, idMood);
    ResultSet rs = pstm.executeQuery();
    List<Movie> lista = new ArrayList<>();
    while (rs.next()) {
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
    rs.close(); conn.close(); connMysql.close();
    return lista;
}
    
    public Movie update(Movie m) throws SQLException {
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();
    PreparedStatement pstm = conn.prepareStatement(
        "UPDATE movies SET title=?, description=?, year=?, image_url=?, id_genre=?, id_mood=? WHERE id_movie=?");
    pstm.setString(1, m.getTitle());
    pstm.setString(2, m.getDescription());
    pstm.setInt(3, m.getYear());
    pstm.setString(4, m.getImage_url());
    pstm.setInt(5, m.getId_genre());
    pstm.setInt(6, m.getId_mood());
    pstm.setInt(7, m.getId_movie());

    int rows;
    try {
        rows = pstm.executeUpdate();
    } catch (SQLIntegrityConstraintViolationException e) {
        pstm.close(); conn.close(); connMysql.close();
        throw e;
    }
    if (rows == 0) { pstm.close(); conn.close(); connMysql.close(); return null; }

    PreparedStatement fetch = conn.prepareStatement(
        "SELECT * FROM movies WHERE id_movie = ?");
    fetch.setInt(1, m.getId_movie());
    ResultSet rs = fetch.executeQuery();
    Movie updated = null;
    if (rs.next()) {
        updated = new Movie();
        updated.setId_movie(rs.getInt("id_movie"));
        updated.setTitle(rs.getString("title"));
        updated.setDescription(rs.getString("description"));
        updated.setYear(rs.getInt("year"));
        updated.setImage_url(rs.getString("image_url"));
        updated.setId_genre(rs.getInt("id_genre"));
        updated.setId_mood(rs.getInt("id_mood"));
    }
    rs.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
    return updated;
}

public boolean delete(int idMovie) throws SQLException {
    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();

    // Cascada manual: borrar referencias antes que la película
    PreparedStatement delWatched = conn.prepareStatement(
        "DELETE FROM watched_movies WHERE id_movie = ?");
    delWatched.setInt(1, idMovie);
    delWatched.executeUpdate();
    delWatched.close();

    PreparedStatement delFav = conn.prepareStatement(
        "DELETE FROM favorites WHERE id_movie = ?");
    delFav.setInt(1, idMovie);
    delFav.executeUpdate();
    delFav.close();

    PreparedStatement delRev = conn.prepareStatement(
        "DELETE FROM reviews WHERE id_movie = ?");
    delRev.setInt(1, idMovie);
    delRev.executeUpdate();
    delRev.close();

    PreparedStatement pstm = conn.prepareStatement(
        "DELETE FROM movies WHERE id_movie = ?");
    pstm.setInt(1, idMovie);
    int rows = pstm.executeUpdate();
    pstm.close(); conn.close(); connMysql.close();
    return rows > 0;
}
}
