/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.model;

/**
 *
 * @author elena
 */
public class MoodCheckin {
    private int id_checkin;
    private int id_user;
    private int id_mood;
    private String checkin_time;
    
    public MoodCheckin(){
    }

    public int getId_checkin() {
        return id_checkin;
    }

    public void setId_checkin(int id_checkin) {
        this.id_checkin = id_checkin;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_mood() {
        return id_mood;
    }

    public void setId_mood(int id_mood) {
        this.id_mood = id_mood;
    }

    public String getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(String checkin_time) {
        this.checkin_time = checkin_time;
    }
    
    
}
