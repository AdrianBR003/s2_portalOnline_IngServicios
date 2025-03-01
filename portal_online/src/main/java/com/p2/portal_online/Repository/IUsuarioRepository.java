package com.p2.portal_online.Repository;


import com.p2.portal_online.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    // JPA reconoce que es un SELECT * FROM users WHERE username = ?, por lo que no hace falta poner la query completa
    Optional<Usuario> findBynombreUsuario(String nombreUsuario);

}
