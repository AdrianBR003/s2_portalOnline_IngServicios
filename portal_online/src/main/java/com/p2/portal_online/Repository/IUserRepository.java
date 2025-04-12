package com.p2.portal_online.Repository;


import com.p2.portal_online.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    // JPA reconoce que es un SELECT * FROM users WHERE username = ?, por lo que no hace falta poner la query completa
    Optional<User> findByusername(String username);

}
