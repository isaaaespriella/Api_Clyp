/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;
import org.lasalle.connection.ConnectionMysql;
import org.lasalle.model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elena
 */
public class ControllerReview {

    public List<Review> getByUser(int idUser) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "SELECT * FROM reviews WHERE id_user = ? ORDER BY created_at DESC");
        pstm.setInt(1, idUser);
        ResultSet rs = pstm.executeQuery();
        List<Review> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapRow(rs));
        rs.close(); conn.close(); connMysql.close();
        return lista;
    }

    public Review getByUserAndMovie(int idUser, int idMovie) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "SELECT * FROM reviews WHERE id_user = ? AND id_movie = ?");
        pstm.setInt(1, idUser);
        pstm.setInt(2, idMovie);
        ResultSet rs = pstm.executeQuery();
        Review r = rs.next() ? mapRow(rs) : null;
        rs.close(); conn.close(); connMysql.close();
        return r;
    }

public Review save(Review r) throws SQLException, IllegalArgumentException {
    if (r.getRating() < 1 || r.getRating() > 5) {
        throw new IllegalArgumentException("rating must be between 1 and 5");
    }

    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();
    PreparedStatement pstm = conn.prepareStatement(
        "INSERT INTO reviews(id_user, id_movie, text, rating, created_at, updated_at) VALUES(?,?,?,?,NOW(),NOW())",
        Statement.RETURN_GENERATED_KEYS);
    pstm.setInt(1, r.getId_user());
    pstm.setInt(2, r.getId_movie());
    if (r.getText() == null || r.getText().isBlank()) {
        pstm.setNull(3, java.sql.Types.VARCHAR);
    } else {
        pstm.setString(3, r.getText());
    }
    pstm.setInt(4, r.getRating());

    try {
        pstm.executeUpdate();
    } catch (SQLIntegrityConstraintViolationException e) {
        pstm.close(); conn.close(); connMysql.close();
        throw e;
    }

    ResultSet rs = pstm.getGeneratedKeys();
    if (rs.next()) r.setId_review(rs.getInt(1));

    PreparedStatement fetch = conn.prepareStatement(
        "SELECT * FROM reviews WHERE id_review = ?");
    fetch.setInt(1, r.getId_review());
    ResultSet rs2 = fetch.executeQuery();
    if (rs2.next()) r = mapRow(rs2);

    rs.close(); rs2.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
    return r;
}

public Review update(Review r) throws SQLException, IllegalArgumentException {
    if (r.getRating() < 1 || r.getRating() > 5) {
        throw new IllegalArgumentException("rating must be between 1 and 5");
    }

    ConnectionMysql connMysql = new ConnectionMysql();
    Connection conn = connMysql.open();
    PreparedStatement pstm = conn.prepareStatement(
        "UPDATE reviews SET text=?, rating=?, updated_at=NOW() WHERE id_review=?");
    pstm.setString(1, r.getText());
    pstm.setInt(2, r.getRating());
    pstm.setInt(3, r.getId_review());
    int rows = pstm.executeUpdate();
    if (rows == 0) { pstm.close(); conn.close(); connMysql.close(); return null; }

    PreparedStatement fetch = conn.prepareStatement(
        "SELECT * FROM reviews WHERE id_review = ?");
    fetch.setInt(1, r.getId_review());
    ResultSet rs = fetch.executeQuery();
    Review updated = rs.next() ? mapRow(rs) : null;
    rs.close(); fetch.close(); pstm.close(); conn.close(); connMysql.close();
    return updated;
}

    public boolean delete(int idReview) throws SQLException {
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(
            "DELETE FROM reviews WHERE id_review = ?");
        pstm.setInt(1, idReview);
        int rows = pstm.executeUpdate();
        pstm.close(); conn.close(); connMysql.close();
        return rows > 0;
    }

    private Review mapRow(ResultSet rs) throws SQLException {
        Review r = new Review();
        r.setId_review(rs.getInt("id_review"));
        r.setId_user(rs.getInt("id_user"));
        r.setId_movie(rs.getInt("id_movie"));
        r.setText(rs.getString("text"));
        r.setRating(rs.getInt("rating"));
        r.setCreated_at(formatIso(rs.getTimestamp("created_at")));
        r.setUpdated_at(formatIso(rs.getTimestamp("updated_at")));
        return r;
    }

    private String formatIso(java.sql.Timestamp ts) {
        if (ts == null) return null;
        return java.time.format.DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(java.time.ZoneOffset.UTC)
            .format(ts.toInstant());
    }
}
