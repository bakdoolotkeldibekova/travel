package com.example.travel.controller;

import com.example.travel.dto.TokenDTO;
import com.example.travel.dto.UserAuthDTO;
import com.example.travel.exception.AccessDeniedException;
import com.example.travel.exception.ToMaintainDataIntegrityException;
import com.example.travel.service.UserService;
import com.example.travel.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @GetMapping("/hello")//proverka
    public String sayHello(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @PostMapping
    public TokenDTO getToken(@RequestBody UserAuthDTO userAuthDTO){
        if (userAuthDTO.getEmail() == null || userAuthDTO.getPassword() == null)
            throw new ToMaintainDataIntegrityException("email or password is null");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthDTO.getEmail(), userAuthDTO.getPassword()));
        } catch (Exception e){
            throw new AccessDeniedException("Auth failed");
        }
        return new TokenDTO(jwtUtil.generateToken(userAuthDTO.getEmail()));
    }
}
