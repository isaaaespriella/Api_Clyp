/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.lasalle.model;
import com.google.gson.annotations.Expose;


/**
 *
 * @author elena
 */
public class User {
@Expose
private int id_user;

@Expose
private String name;

@Expose
private String email;

private String password;

@Expose
private String created_at;

       public User() {
    }
   
    public User(int id_user, String name, String email, String password, String created_at) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
   
   
}
