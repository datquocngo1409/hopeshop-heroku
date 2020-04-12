package com.example.hopeshop.model;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    private String email;

    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    private String avatar;
    private int roleId;
    public User() {
        super();
    }
    public User(String email, String username, String password) {
        super();
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public User(int id, String email, String username, String password, String avatar, int roleId) {
        super();
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.roleId = roleId;
    }

    public User(String username, String password, String email, String avatar, int roleId) {
        super();
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
