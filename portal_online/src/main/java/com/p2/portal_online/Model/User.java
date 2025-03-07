package com.p2.portal_online.Model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "users")
public class User {

    // VARIABLES

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_User;

    @NonNull
    private String username;
    private String name;
    private String lastname;
    private String email;
    @NonNull
    private String password;

    // CONSTRUCTORES

    public User() {
    }

    public User(@NonNull String username, @NonNull String name, @NonNull String lastname, @NonNull String email, @NonNull String password) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }

    // METODOS

    @Override
    public String toString() {
        return "Usuario{" +
                "UserName='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }




    // GETTER AND SETTER

    public Long getId_User() {
        return id_User;
    }

    public void setId_User(Long id_User) {
        this.id_User = id_User;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getLastname() {
        return lastname;
    }

    public void setLastname(@NonNull String lastname) {
        this.lastname = lastname;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
