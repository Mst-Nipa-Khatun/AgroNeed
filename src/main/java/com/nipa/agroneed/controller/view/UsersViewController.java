package com.nipa.agroneed.controller.view;


import com.nipa.agroneed.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersViewController {
    private final UserService userService;



    public UsersViewController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public String users() {
        return "users";
    }
}
