package com.nipa.agroneed.controller.api;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UserDto;
import com.nipa.agroneed.service.UserService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(UrlConstraint.Users.GET_ALL)
    public Response getAllUsers() {
        return userService.getAllUsers();
    }

   /* @GetMapping("/{id}")
    public Response getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public Response updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }*/

}
