package com.nipa.agroneed.controller.api;


import com.nipa.agroneed.dto.AddRoleDto;
import com.nipa.agroneed.dto.LoginDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UserDto;
import com.nipa.agroneed.service.Impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
                RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class AuthController {
    private final AuthService authService;

    //this page for authcontroller
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginDto loginDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return authService.login(loginDto, httpServletRequest, httpServletResponse);
    }
    @PostMapping("/registerUser") //admin aro ekta user k add korte parbe as a admin
    public Response register(@RequestBody UserDto userDto) {
        return authService.register(userDto);
    }

    @PostMapping("/addRole")
    public Response addRole(@RequestBody AddRoleDto addRoleDto) {
        return authService.addRole(addRoleDto);
    }


}