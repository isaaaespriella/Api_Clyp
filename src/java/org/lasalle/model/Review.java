/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.model;

/**
 *
 * @author elena
 */
public class Review {
    private int id_review;
    private int id_user;
    private int id_movie;
    private String text;
    private int rating;
    private String created_at;
    private String updated_at;

    public Review() {}

    public int getId_review() { return id_review; }
    public void setId_review(int id_review) { this.id_review = id_review; }
    public int getId_user() { return id_user; }
    public void setId_user(int id_user) { this.id_user = id_user; }
    public int getId_movie() { return id_movie; }
    public void setId_movie(int id_movie) { this.id_movie = id_movie; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }
}