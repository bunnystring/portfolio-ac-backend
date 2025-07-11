package com.backend.portfolio_ac.security;

import com.backend.portfolio_ac.entity.User;
import com.backend.portfolio_ac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementación de UserDetailsService para la autenticación de usuarios.
 * Busca el usuario por email y construye un objeto UserDetails para Spring Security.
 *
 * @author bunnystring
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Carga los detalles del usuario a partir del email.
     *
     * @param email el email del usuario a buscar
     * @return UserDetails con las credenciales y roles del usuario
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ADMIN"))
        );
    }
}