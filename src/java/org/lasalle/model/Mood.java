/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.model;

/**
 *
 * @author elena
 */
public class Mood {
    private int id_mood;
    private String name;
    private String description;
    
    public Mood(){
    }

    public Mood(int id_mood, String name, String description) {
        this.id_mood = id_mood;
        this.name = name;
        this.description = description;
    }

    public int getId_mood() {
        return id_mood;
    }

    public void setId_mood(int id_mood) {
        this.id_mood = id_mood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
