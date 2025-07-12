package com.backend.portfolio_ac.service;

import com.backend.portfolio_ac.dto.AuthResponse;
import com.backend.portfolio_ac.dto.LoginRequest;
import com.backend.portfolio_ac.dto.RegisterRequest;
import com.backend.portfolio_ac.entity.User;

/**
 * Servicio para operaciones relacionadas con usuarios.
 * Define el contrato para el registro de nuevos usuarios.
 *
 * @author bunnystring
 */
public interface UserService {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request los datos de registro del usuario
     * @return el usuario registrado
     */
    User registerNewUser(RegisterRequest request);


    /**
     * Autentica un suario en el sistema
     *
     * @param loginRequest los datos de autenticación del usuario
     * @return AuthResponse
     */
    AuthResponse login(LoginRequest loginRequest);
}