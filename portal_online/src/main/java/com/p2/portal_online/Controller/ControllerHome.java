package com.p2.portal_online.Controller;


import com.p2.portal_online.Model.User;
import com.p2.portal_online.Security.HashPassword;
import com.p2.portal_online.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class ControllerHome {

    /*
        NOTES:
        > El ID del usuario, debe de ser unico para extraer el valor de la base de datos
        > La sesión del usuario debe de ir vinculador por su ID

     */

    @Autowired
    private UserService userService;

    // Vamos a dejar la comprobación de sesión unicamente para el GET /homeshop
    @GetMapping(value = "/login")
    public String getLogin(HttpServletRequest req, RedirectAttributes redirec) {
        if (req.getSession(false) != null && req.getSession(false).getAttribute("id_Usuario") != null) {
            return "/homeshop";
        } else {
            return "form-login";
        }
    }

    @GetMapping(value = "/register")
    public String getRegister(HttpServletRequest req, RedirectAttributes redirec) {
        if (req.getSession(false) != null && req.getSession(false).getAttribute("id_Usuario") != null) {
            return "/homeshop";
        } else {
            return "form-register";
        }
    }

    @GetMapping(value = "/homeshop")
    public String getHomeshop(HttpServletRequest req, RedirectAttributes redirectAttributes, Model model) {
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("id_Usuario") != null) {
            model.addAttribute("alertMessage", "Bienvenido de Nuevo!");
            model.addAttribute("toastType", "success");
            return "/homeshop";
        } else {
            redirectAttributes.addFlashAttribute("alertMessage", "NO se ha iniciado sesion");
            redirectAttributes.addFlashAttribute("toastType", "danger");
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/homeshop")
    public String postInicio(HttpServletRequest req, HttpServletResponse res, Model mod, RedirectAttributes redirectAttributes) {
        String nameUser = req.getParameter("nameUser");
        String name = req.getParameter("name");
        String lastname = req.getParameter("apell");
        String email = req.getParameter("email");
        String password = req.getParameter("pass");

        // Creacion del Usuario
        System.out.println("User!: " + new User(nameUser, name, lastname, email, password));

        if (req.getParameter("email") == null) { // Estaremos en Login
            System.out.println("LOGIN!");
            Long id_usuario = userService.authUser(new User(nameUser, password));
            if (id_usuario != -1) {
                // Se ha autenticado correctamente, asociación de ID usuarios con ID sesion
                HttpSession session = req.getSession(true); // Se crea una vez que se vaya a iniciar sesión, no antes
                session.setAttribute("id_Usuario", id_usuario); // Le pasamos en la sesion el id_usuario
                redirectAttributes.addFlashAttribute("alertMessage", "Login Verificado!");
                redirectAttributes.addFlashAttribute("toastType", "success");
                return "redirect:/homeshop";
            } else {
                redirectAttributes.addFlashAttribute("alertMessage", "Usuario o contraseña incorrectos");
                redirectAttributes.addFlashAttribute("toastType", "danger");
                req.getSession().setAttribute("id_Usuario", null);
                return "redirect:/login";
            }

        } else { // Estaremos en Register
            System.out.println("REGISTER!");
            String passwordHash = HashPassword.hashP(password);
            Long id_usuario = userService.registerUser(new User(nameUser, name, lastname, email, passwordHash));
            if (id_usuario != -1) {
                HttpSession session = req.getSession(true); // Se crea una vez que se vaya a iniciar sesión, no antes
                session.setAttribute("id_Usuario", id_usuario);
                redirectAttributes.addFlashAttribute("alertMessage", "Register Verificado!");
                redirectAttributes.addFlashAttribute("toastType", "success");
                return "redirect:/homeshop";
            } else {
                // NO se ha registrado
                System.out.println("ERROR REGISTER, exist!");
                redirectAttributes.addFlashAttribute("alertMessage", "Usuario o contraseña incorrectos");
                redirectAttributes.addFlashAttribute("toastType", "danger");
                req.getSession().setAttribute("id_Usuario", null);
                return "redirect:/register";
            }
        }
    }
}
