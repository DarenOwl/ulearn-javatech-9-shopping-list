package com.example.shoppinglist.entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "token")
    private String token;
    @Column(name = "salt")
    private String salt;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "created")
    private Date created;
    @Column(name = "last_login")
    private Date lastLogin;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        salt = BCrypt.gensalt();
        passwordHash = BCrypt.hashpw(password, salt);
        created = new Date();
        lastLogin = new Date();

        byte[] randomBytes = new byte[24];
        new SecureRandom().nextBytes(randomBytes);
        token = Base64.getUrlEncoder().encodeToString(randomBytes);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
