package com.backend.portfolio_ac.controller;

import com.backend.portfolio_ac.dto.AuthResponse;
import com.backend.portfolio_ac.dto.LoginRequest;
import com.backend.portfolio_ac.dto.RegisterRequest;
import com.backend.portfolio_ac.repository.UserRepository;
import com.backend.portfolio_ac.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.backend.portfolio_ac.service.UserService;

/**
 * Controlador de autenticación que gestiona el login y registro de usuarios.
 * Proporciona endpoints REST para autenticación y registro.
 * @author bunnystring
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Autentica a un usuario y genera un token JWT si las credenciales son válidas.
     * @author bunnystring
     * @param loginRequest El objeto con el email y password del usuario.
     * @return Un ResponseEntity con el token JWT o un mensaje de error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
     AuthResponse authResponse = userService.login(loginRequest);
     return ResponseEntity.ok(authResponse);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @author bunnystring
     * @param request El objeto con los datos necesarios para el registro.
     * @return Un ResponseEntity con mensaje de éxito o error.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {

            //Registrar usuario
            userService.registerNewUser(request);

            // hacer Login
            LoginRequest loginRequest = new LoginRequest(request.getEmail(), request.getPassword());
            AuthResponse authResponse = userService.login(loginRequest);

            return ResponseEntity.ok(authResponse);
    }
}