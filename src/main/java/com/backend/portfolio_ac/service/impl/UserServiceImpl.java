package com.backend.portfolio_ac.service.impl;

import com.backend.portfolio_ac.dto.AuthResponse;
import com.backend.portfolio_ac.dto.LoginRequest;
import com.backend.portfolio_ac.dto.RegisterRequest;
import com.backend.portfolio_ac.dto.UserSafeDto;
import com.backend.portfolio_ac.entity.User;
import com.backend.portfolio_ac.repository.UserRepository;
import com.backend.portfolio_ac.security.JwtUtil;
import com.backend.portfolio_ac.service.UserService;
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
            throw new UserException("Inválid credentials", UserException.Type.INVALID_CREDENTIALS);
        }

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new UserException("User not found", UserException.Type.NOT_FOUND);
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserException("User not found", UserException.Type.NOT_FOUND));

        String token = jwtUtil.generateToken(userDetails.getUsername());
        UserSafeDto safeUser = new UserSafeDto(user.getName(), user.getEmail());
        return new AuthResponse(safeUser, token);
    }

    /**
   * Registra un nuevo usuario en el sistema.
   * Verifica si el email ya está en uso, encripta la contraseña y guarda el usuario.
   *
   * @param request los datos de registro del usuario
   * @return el usuario registrado
   * @throws UserException si el email ya está en uso
   */
    @Override
    public User registerNewUser(RegisterRequest request){

        if (userRepository.existsByEmail(request.getEmail())){
            throw new UserException(
                    request.getEmail() + " is already registered",
                    UserException.Type.EMAIL_IN_USE
            );
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }
}