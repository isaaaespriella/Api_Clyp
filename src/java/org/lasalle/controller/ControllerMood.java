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
}