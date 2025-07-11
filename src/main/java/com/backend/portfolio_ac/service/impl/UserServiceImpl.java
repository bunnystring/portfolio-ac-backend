package com.backend.portfolio_ac.service.impl;

import com.backend.portfolio_ac.dto.RegisterRequest;
import com.backend.portfolio_ac.entity.User;
import com.backend.portfolio_ac.repository.UserRepository;
import com.backend.portfolio_ac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de usuarios para el registro de nuevos usuarios.
 *
 * @author bunnystring
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en el sistema.
     * Verifica si el email ya está en uso, encripta la contraseña y guarda el usuario.
     *
     * @param request los datos de registro del usuario
     * @return el usuario registrado
     * @throws RuntimeException si el email ya está en uso
     */
    @Override
    public User registerNewUser(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("El email ya está en uso");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }
}