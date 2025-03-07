package com.p2.portal_online.Service;

import com.p2.portal_online.Model.User;
import com.p2.portal_online.Repository.IUserRepository;
import com.p2.portal_online.Security.HashPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    public Long authUser(User user){ // Si se autentica devuelve el id del usuario, si no, devuelve -1
        // TODO: Hacer la comprobación para el caso donde sea nulo el usuario (no exista)
        User userDB = iUserRepository.findByusername(user.getUsername()).orElse(null);
        System.out.println("UserDB " + userDB);

        if (userDB == null || !HashPassword.verifyP(user.getPassword(), userDB.getPassword())) {
            return -1L;
        }else{
            System.out.println("Success! ");
            return userDB.getId_User();
        }
    }

    public Long registerUser(User user){ // Al registrarse, devuelve el id_usuario
        // Comprobar que el nombre de usuario no exista
        if(iUserRepository.findByusername(user.getUsername()).isPresent()){
            return -1L;
        }else {
            User userDB = iUserRepository.save(user);
            return userDB.getId_User();
        }
    }

};
