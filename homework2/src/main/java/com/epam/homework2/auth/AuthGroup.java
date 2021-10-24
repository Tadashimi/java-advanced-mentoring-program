package com.epam.homework2.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auth_user_group")
public class AuthGroup {

    @Id
    @Column(name = "auth_user_group_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name ="username")
    private String username;

    @Column(name = "auth_group")
    private String authGroup;

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

    public String getAuthGroup() {
        return authGroup;
    }

    public void setAuthGroup(String authGroup) {
        this.authGroup = authGroup;
    }
}
