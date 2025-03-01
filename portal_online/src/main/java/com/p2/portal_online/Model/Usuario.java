package com.p2.portal_online.Model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "users")
public class Usuario {

    // VARIABLES

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Character;

    @NonNull
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    @NonNull
    private String password;

    // CONSTRUCTORES

    public Usuario() {
    }

    public Usuario (@NonNull String nombreUsuario, @NonNull String nombre, @NonNull String apellidos, @NonNull String email, @NonNull String password) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
    }

    public Usuario (@NonNull String nombreUsuario, @NonNull String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    // METODOS

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }




    // GETTER AND SETTER

    public Long getId_Character() {
        return id_Character;
    }

    public void setId_Character(Long id_Character) {
        this.id_Character = id_Character;
    }

    @NonNull
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NonNull String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(@NonNull String apellidos) {
        this.apellidos = apellidos;
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
