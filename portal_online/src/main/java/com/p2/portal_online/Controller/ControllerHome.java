package com.p2.portal_online.Controller;


import com.p2.portal_online.Model.Usuario;
import com.p2.portal_online.Security.HashPassword;
import com.p2.portal_online.Service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControllerHome {

    /*
        NOTA DESARROLLO:
        > El nombre de usuario debe de ser unico para extraer el valor de la base de datos

     */

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/login")
    public String getLogin() {
        return "form-login";
    }

    @PostMapping(value="/inicio")
    public String postInicio(HttpServletRequest req, HttpServletResponse res, Model mod, RedirectAttributes redirectAttributes){
        String nombreUsuario = req.getParameter("nameUser");
        String nombre = req.getParameter("name");
        String apellidos = req.getParameter("apell");
        String email = req.getParameter("email");
        String password = req.getParameter("pass");

        // Creacion del Usuario

        System.out.println("Usuario ha llegado: " + new Usuario(nombreUsuario,nombre,apellidos,email,password));

        if(req.getParameter("email")==null){ // Estaremos en Login
            // TODO: Comprobar de la base de datos

            System.out.println("LOGIN!");

            if(usuarioService.authUsuario(new Usuario(nombreUsuario,password))){
                return "inicio";
            }else{
                // TODO: Que salte el toast del servicio
                redirectAttributes.addFlashAttribute("error", "true");
                return "redirect:/login?error=true";
            }

        }else{ // Estaremos en Register

            System.out.println("REGISTER!");
            String passwordHash = HashPassword.hashP(password);
            usuarioService.registerUsuario(new Usuario(nombreUsuario,nombre,apellidos,email,passwordHash));
        }



        return "inicio";
    }

    @GetMapping(value = "/register")
    public String getRegister() {
        return "form-register";
    }

}
