package com.hassan.store.controllers;

import com.hassan.store.dtos.JwtResponse;
import com.hassan.store.dtos.UserLoginRequest;
import com.hassan.store.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtResponse;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody UserLoginRequest request
    ){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var token = jwtResponse.generateToken(request.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("validate")
    public Boolean validate(@RequestHeader("Authorization") String authHeader){

        var token = authHeader.replace("Bearer ", "");
        return jwtResponse.validateToken(token);
    }

    @ExceptionHandler(value={BadCredentialsException.class})
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
