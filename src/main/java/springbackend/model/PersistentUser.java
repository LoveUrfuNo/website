/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.model;

import javax.persistence.*;
import javax.persistence.Entity;

import java.util.Date;

/**
 * Simple JavaBean domain object that represents a user who click on "remember me".
 */
@Entity
@Table(name = "persistent_logins")
public class PersistentUser {
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "series")
    private String series;

    @Column(name = "token")
    private String token;

    @Column(name = "last_used")
    private Date lastUsed;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public String toString() {
        return "PersistentUser{" +
                "username='" + username + '\'' +
                ", series='" + series + '\'' +
                ", token='" + token + '\'' +
                ", lastUsed=" + lastUsed +
                '}';
    }
}
