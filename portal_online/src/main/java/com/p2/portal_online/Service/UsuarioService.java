package com.p2.portal_online.Service;

import com.p2.portal_online.Repository.IUsuarioRepository;
import com.p2.portal_online.Model.Usuario;
import com.p2.portal_online.Security.HashPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    public boolean authUsuario(Usuario usuario){
        // TODO: Hacer la comprobación para el caso donde sea nulo el usuario (no exista)

        Usuario usuarioDB = iUsuarioRepository.findBynombreUsuario(usuario.getNombreUsuario()).orElse(null);
        System.out.println("UsuarioDB " + usuarioDB);

        if (usuarioDB == null || !HashPassword.verifyP(usuario.getPassword(), usuarioDB.getPassword())) {
            return false;
        }else{
            System.out.println("Contraseña Validada! ");
            return true;
        }
    }

    public void registerUsuario(Usuario usuario){
        iUsuarioRepository.save(usuario);
    }



};
