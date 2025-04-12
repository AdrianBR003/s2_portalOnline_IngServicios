package com.p2.portal_online.Controller;


import com.p2.portal_online.Model.User;
import com.p2.portal_online.Repository.IProductRepository;
import com.p2.portal_online.Security.HashPassword;
import com.p2.portal_online.Service.ProductService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class ControllerHome {

    private final ProductService productService;
    private final UserService userService;

    // Inyectas las dependencias (ProductService e UserService) al constructor de Controller
    @Autowired
    public ControllerHome(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    /*
        NOTES:
        > El ID del usuario, debe de ser unico para extraer el valor de la base de datos
        > La sesión del usuario debe de ir vinculador por su ID

     */

    // Vamos a dejar la comprobación de sesión unicamente para el GET /homeshop
    @GetMapping(value = "/login")
    public String getLogin(HttpServletRequest req, Model model) {
        if (req.getSession(false) != null && req.getSession(false).getAttribute("id_Usuario") != null) {
            // Si es admin, le devuelve a la vista 'admin'
            if (req.getSession().getAttribute("name").equals("admin")) {
                model.addAttribute("userslist", this.userService.getAllUsers());
                return "admin";
            }
            return "homeshop";
        } else {
            return "form-login";
        }
    }

    @GetMapping(value = "/register")
    public String getRegister(HttpServletRequest req, Model model) {
        if (req.getSession(false) != null && req.getSession(false).getAttribute("id_Usuario") != null) {
            // Si es admin, le devuelve a la vista 'admin'
            if (req.getSession().getAttribute("name").equals("admin")) {
                model.addAttribute("userslist", this.userService.getAllUsers());
                return "admin";
            }
            return "homeshop";
        } else {
            return "form-register";
        }
    }

    @GetMapping(value = "/homeshop")
    public String getHomeshop(HttpServletRequest req, RedirectAttributes redirectAttributes, Model model) {
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("id_Usuario") != null) {
            // Si es admin, le devuelve a la vista 'admin'
            if (session.getAttribute("name").equals("admin")) {
                model.addAttribute("userslist", this.userService.getAllUsers());
                return "admin";
            }

            boolean esCompra = "true".equals(model.getAttribute("rehomeshop"));
            model.addAttribute("alertMessage", esCompra ? "La compra se ha realizado exitósamente" : "Éxito Inicio de Sesión");
            model.addAttribute("toastType", "success");
            model.addAttribute("listProducts", productService.inicializar());

            return "homeshop";
        } else {
            redirectAttributes.addFlashAttribute("alertMessage", "NO se ha iniciado sesion");
            redirectAttributes.addFlashAttribute("toastType", "danger");
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/homeshop")
    public String postInicio(HttpServletRequest req, RedirectAttributes redirectAttributes, Model model) {
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
                session.setAttribute("name", nameUser); // Le pasamos el nameuser para mostrarlo en el html

                // Verifica si es adminsitrador
                if (nameUser.equalsIgnoreCase("admin")) {
                    model.addAttribute("userslist", this.userService.getAllUsers());
                    return "admin";
                }

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
                session.setAttribute("name", nameUser); // Le pasamos el nameuser para mostrarlo en el html
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

    @PostMapping(value = "/signout")
    public String postSignOut(HttpServletRequest req, Model model) {
        // Invalidamos la sesion y devolvemos al Login
        req.getSession().invalidate();
        model.addAttribute("alertMessage", "Éxito en el Cierre de Sesión");
        model.addAttribute("toastType", "success");
        return "form-login";
    }

    @GetMapping("/paypal")
    public String mostrarFormularioPago(@RequestParam String nombre, @RequestParam Double precio, Model model) {
        model.addAttribute("nombre", nombre);
        model.addAttribute("precio", precio);
        return "paypal";
    }

    @GetMapping("/rehomeshop")
    public String rehomeShop(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("rehomeshop", "true");
        return "redirect:/homeshop";
    }

}
