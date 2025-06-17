package com.nipa.agroneed.service.Impl;


import com.nipa.agroneed.config.JwtTokenProvider;
import com.nipa.agroneed.config.SecurityConfig;
import com.nipa.agroneed.dto.*;
import com.nipa.agroneed.entity.Role;
import com.nipa.agroneed.entity.User;
import com.nipa.agroneed.repository.RoleRepository;
import com.nipa.agroneed.repository.UserRepository;
import com.nipa.agroneed.utils.ResponseBuilder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Response login(LoginDto loginDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User user = userRepository.findByPhoneAndStatus(loginDto.getPhone(), 1);
        if (user == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST,
                    null, "Invalid Phone or Password");
        }
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getPhone(),
                        loginDto.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setRoles(user.getRoles()
                    .stream()
                    .map(Role::getName).collect(Collectors.toList()));
            loginResponseDto.setToken(jwtTokenProvider.generateToken(authentication, httpServletRequest));
            loginResponseDto.setUsername(user.getName());
            loginResponseDto.setUserId(user.getId());


            Cookie cookie = new Cookie(SecurityConfig.COOKIES_JWT_TOKEN_KEY, loginResponseDto.getToken());
            //cookie.setHttpOnly(true);//The HttpOnly flag makes the cookie inaccessible to JavaScript running in the browser, which helps mitigate the risk of cross-site scripting (XSS) attacks.
            cookie.setHttpOnly(false);
            //cookie.setSecure(true); // Ensure the cookie is sent over HTTPS
            cookie.setSecure(false); // Ensure the cookie is sent over HTTP
            cookie.setPath("/");
            cookie.setMaxAge(SecurityConfig.COOKIES_JWT_TOKEN_MAX_AGE);
            httpServletResponse.addCookie(cookie);

            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    loginResponseDto, "Login Successful");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST,
                null, "Invalid Phone or Password");
    }

    public Response register(UserDto userDto) {
        User user = userRepository.findByPhoneAndStatus(userDto.getPhone(), 1);
        if (user == null) {
            user = new User();
            user.setStatus(1);
            user.setName(userDto.getUsername());
            user.setPhone(userDto.getPhone());

            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            role.setStatus(1);
            roles.add(role);
            user.setRoles(roles);

            String encodedPass = passwordEncoder.encode(userDto.getPassword());
            user.setPasswordHash(encodedPass);
            user.setEmail(userDto.getEmail());
            User savedUser = userRepository.save(user);

            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, null,
                    "Register Successful");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Exist user using this phone");
    }

    public Response addRole(AddRoleDto addRoleDto) {
        User user = userRepository.findByIdAndStatus(addRoleDto.getUserId(), 1);
        if (user == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "User does not exist");
        }
        Role role = roleRepository.findByNameAndStatus(addRoleDto.getRoleName(), 1);
        if (role == null) {
            role = new Role();
            role.setName(addRoleDto.getRoleName());
            role.setStatus(1);
            role = roleRepository.save(role);
        }
        List<Role> roles = user.getRoles();
        roles.add(role);

        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, savedUser,
                "Successfully added role");
    }

}
