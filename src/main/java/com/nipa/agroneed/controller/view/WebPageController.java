package com.nipa.agroneed.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WebPageController {

    @GetMapping("/")
    public String redirectToDashboard() {
        return "redirect:/pages/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/multiple")
    public String multiple() {
        return "multiple";
    }

}