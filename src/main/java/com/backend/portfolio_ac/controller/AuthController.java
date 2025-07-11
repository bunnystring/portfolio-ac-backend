package com.backend.portfolio_ac.controller;

import com.backend.portfolio_ac.dto.AuthResponse;
import com.backend.portfolio_ac.dto.LoginRequest;
import com.backend.portfolio_ac.dto.RegisterRequest;
import com.backend.portfolio_ac.dto.UserSafeDto;
import com.backend.portfolio_ac.entity.User;
import com.backend.portfolio_ac.repository.UserRepository;
import com.backend.portfolio_ac.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.backend.portfolio_ac.service.UserService;

/**
 * Controlador de autenticación que gestiona el login y registro de usuarios.
 * Proporciona endpoints REST para autenticación y registro.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            // Recuperar entidad User y mapear a UserSafeDto
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            UserSafeDto safeUser = new UserSafeDto(user.getName(), user.getEmail());

            return ResponseEntity.ok(new AuthResponse(safeUser, token));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @author bunnystring
     * @param request El objeto con los datos necesarios para el registro.
     * @return Un ResponseEntity con mensaje de éxito o error.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        try {
            User user = userService.registerNewUser(request);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}