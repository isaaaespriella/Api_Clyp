/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.model;

/**
 *
 * @author elena
 */
public class Movie {
    private int id_movie;
    private String title;
    private String description;
    private int year;
    private String image_url;
    private int id_genre;
    private int id_mood;
    
    public Movie(){
    }

    public Movie(int id_movie, String title, String description, int year, String image_url, int id_genre, int id_mood) {
        this.id_movie = id_movie;
        this.title = title;
        this.description = description;
        this.year = year;
        this.image_url = image_url;
        this.id_genre = id_genre;
        this.id_mood = id_mood;
    }

    public int getId_movie() {
        return id_movie;
    }

    public void setId_movie(int id_movie) {
        this.id_movie = id_movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getId_genre() {
        return id_genre;
    }

    public void setId_genre(int id_genre) {
        this.id_genre = id_genre;
    }

    public int getId_mood() {
        return id_mood;
    }

    public void setId_mood(int id_mood) {
        this.id_mood = id_mood;
    }
    
    
}
