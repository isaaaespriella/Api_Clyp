/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.model;

/**
 *
 * @author elena
 */
public class WatchedMovie {
    private int id;
    private int id_user;
    private int id_movie;
    private String watched_at;
    
    public WatchedMovie(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_movie() {
        return id_movie;
    }

    public void setId_movie(int id_movie) {
        this.id_movie = id_movie;
    }

    public String getWatched_at() {
        return watched_at;
    }

    public void setWatched_at(String watched_at) {
        this.watched_at = watched_at;
    }
    
    
}
