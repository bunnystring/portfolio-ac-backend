package com.backend.portfolio_ac.service.impl;

import com.backend.portfolio_ac.dto.AuthResponse;
import com.backend.portfolio_ac.dto.LoginRequest;
import com.backend.portfolio_ac.dto.RegisterRequest;
import com.backend.portfolio_ac.dto.UserSafeDto;
import com.backend.portfolio_ac.entity.User;
import com.backend.portfolio_ac.entity.VerificationCode;
import com.backend.portfolio_ac.repository.UserRepository;
import com.backend.portfolio_ac.repository.VerificationCodeRepository;
import com.backend.portfolio_ac.security.JwtUtil;
import com.backend.portfolio_ac.service.EmailService;
import com.backend.portfolio_ac.service.UserService;
import com.backend.portfolio_ac.service.VerificationService;
import com.backend.portfolio_ac.util.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.portfolio_ac.exception.UserException;

import java.time.LocalDateTime;

/**
 * Implementación del servicio de usuarios para el registro de nuevos usuarios.
 *
 * @author bunnystring
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationService verificationService;


    /**
     * Autentica un usuario en el sistema
     * verifica que los datos venga y sean correctos
     *
     * @param loginRequest los datos de autenticación del usuario
     * @return AuthRespone
     * @author bunnystring
     * @throws UserException
     */
    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new UserException(MessageException.INVALID_CREDENTIALS, UserException.Type.INVALID_CREDENTIALS);
        }

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new UserException(MessageException.USER_NOT_FOUND, UserException.Type.NOT_FOUND);
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserException(MessageException.USER_NOT_FOUND, UserException.Type.NOT_FOUND));

        String token = jwtUtil.generateToken(userDetails.getUsername());
        UserSafeDto safeUser = new UserSafeDto(user.getName(), user.getEmail());
        return new AuthResponse(safeUser, token);
    }

    /**
   * Registra un nuevo usuario en el sistema.
   * Verifica si el email ya está en uso, encripta la contraseña y guarda el usuario, guarda codigo de verificación
   *
   * @param request los datos de registro del usuario
   * @return el usuario registrado
   * @throws UserException si el email ya está en uso
   */
    @Override
    public User registerNewUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(
                    request.getEmail() + MessageException.EMAIL_ALREADY_REGISTERED,
                    UserException.Type.EMAIL_IN_USE
            );
        }

        // Guardar usuario
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        //Genera codigo de verificación y lo guarda en la entidad
        String code = verificationService.generateVerificationCode(request.getEmail());
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(user.getEmail());
        verificationCode.setCode(code);
        verificationCode.setUser(user);
        verificationCode.setGeneratedAt(LocalDateTime.now());
        verificationCode.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        verificationCodeRepository.save(verificationCode);

        return user;
    }
}