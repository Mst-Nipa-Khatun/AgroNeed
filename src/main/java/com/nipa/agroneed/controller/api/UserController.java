package com.nipa.agroneed.controller.api;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UserDto;
import com.nipa.agroneed.service.UserService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstraint.Users.ROOT)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(UrlConstraint.Users.CREATE)
    public Response userRegister(@RequestBody UserDto userDto) {
        return userService.userRegister(userDto);
    }

}
