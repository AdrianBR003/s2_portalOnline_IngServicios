package com.p2.portal_online;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerHome {

    @GetMapping(value = "/login")
    public String getLogin() {
        return "form-login";
    }

    @GetMapping(value = "/register")
    public String getRegister() {
        return "form-register";
    }

}
