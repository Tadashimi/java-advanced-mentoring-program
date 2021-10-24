package com.epam.homework2.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user_details")
public class User {

    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    /*
    To store user role in the same table as user
     */
//    @Column(name = "user_role", nullable = false)
//    private String user_role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
/*
User role
 */
//    public String getUser_role() {
//        return user_role;
//    }
//
//    public void setUser_role(String user_role) {
//        this.user_role = user_role;
//    }
}
