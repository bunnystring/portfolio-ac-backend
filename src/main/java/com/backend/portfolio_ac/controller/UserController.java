package com.backend.portfolio_ac.controller;

import com.backend.portfolio_ac.dto.AuthResponse;
import com.backend.portfolio_ac.dto.GenericResponse;
import com.backend.portfolio_ac.dto.LoginRequest;
import com.backend.portfolio_ac.dto.RegisterRequest;
import com.backend.portfolio_ac.entity.User;
import com.backend.portfolio_ac.repository.UserRepository;
import com.backend.portfolio_ac.security.JwtUtil;
import com.backend.portfolio_ac.service.OAuth2TokenService;
import com.backend.portfolio_ac.service.VerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.backend.portfolio_ac.service.UserService;
import static com.backend.portfolio_ac.util.Messages.EMAIL_VERIFIED;

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

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private OAuth2TokenService oAuth2TokenService;


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
            User user = userService.registerNewUser(request);


           //Obtiene el token y envia el correo
            String accessToken = oAuth2TokenService.getAccessToken();

            //enviar correo de verificacion
            verificationService.sendVerificationEmail(request, accessToken);
            // hacer Login
            LoginRequest loginRequest = new LoginRequest(request.getEmail(), request.getPassword());
            AuthResponse authResponse = userService.login(loginRequest);

            return ResponseEntity.ok(authResponse);
    }


    @GetMapping("/verify")
    public ResponseEntity<GenericResponse> verifyEmail(@RequestParam("token") String token){
            verificationService.verifyEmail(token);
        GenericResponse response = new GenericResponse(
                HttpStatus.CREATED.value(),
                EMAIL_VERIFIED
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}