package com.tictactoe.tictactoe.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PLAYER")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    public Player() {
    }

    public Player(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
