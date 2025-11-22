package com.hassan.store.controllers;

import com.hassan.store.config.JwtConfig;
import com.hassan.store.dtos.JwtResponse;
import com.hassan.store.dtos.UserDto;
import com.hassan.store.dtos.UserLoginRequest;
import com.hassan.store.mappers.UserMapper;
import com.hassan.store.repositories.UserRepository;
import com.hassan.store.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtResponse;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody UserLoginRequest request,
            HttpServletResponse response
    ){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtResponse.generateAccessToken(user);
        var refreshToken = jwtResponse.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("me")
    public ResponseEntity<UserDto> me(){

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        var user = userRepository.findById(userId).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        var userDto = userMapper.toDto(user);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue("refreshToken") String refreshToken
    ){
        if(!jwtService.validateToken(refreshToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var userId = jwtService.getUserId(refreshToken);
        var user = userRepository.findById(userId).orElseThrow();
        var token = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    @ExceptionHandler(value={BadCredentialsException.class})
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
