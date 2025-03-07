package com.p2.portal_online.Repository;


import com.p2.portal_online.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    // JPA reconoce que es un SELECT * FROM users WHERE username = ?, por lo que no hace falta poner la query completa
    Optional<User> findByusername(String username);

}
