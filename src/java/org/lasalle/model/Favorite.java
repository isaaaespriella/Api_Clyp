/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.model;

/**
 *
 * @author elena
 */
public class Favorite {
    private int id_favorite;
    private int id_user;
    private int id_movie;
    private String favorited_at;

    public Favorite() {}

    public int getId_favorite() { return id_favorite; }
    public void setId_favorite(int id_favorite) { this.id_favorite = id_favorite; }
    public int getId_user() { return id_user; }
    public void setId_user(int id_user) { this.id_user = id_user; }
    public int getId_movie() { return id_movie; }
    public void setId_movie(int id_movie) { this.id_movie = id_movie; }
    public String getFavorited_at() { return favorited_at; }
    public void setFavorited_at(String favorited_at) { this.favorited_at = favorited_at; }
}
