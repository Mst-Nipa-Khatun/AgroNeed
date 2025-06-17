package com.nipa.agroneed.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
                RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class TokenCheckController {

    public record CheckTokenResponse(String message) {
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> checkToken() {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new CheckTokenResponse("Token is valid"));
    }

}
