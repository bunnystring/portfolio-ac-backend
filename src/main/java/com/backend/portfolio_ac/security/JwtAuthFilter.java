package com.backend.portfolio_ac.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para la autenticación basada en JWT.
 * Intercepta cada petición HTTP para validar el token JWT y autenticar al usuario si el token es válido.
 * Si el token es inválido o no contiene un email, responde con un error adecuado.
 *
 * @author bunnystring
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Método principal del filtro que valida el token JWT y autentica al usuario en el contexto de seguridad.
     *
     * @param request       la solicitud HTTP entrante
     * @param response      la respuesta HTTP saliente
     * @param filterChain   la cadena de filtros
     * @throws ServletException en caso de error de servlet
     * @throws IOException      en caso de error de IO
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String autHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        try {
            if (autHeader != null && autHeader.startsWith("Bearer ")) {
                token = autHeader.substring(7);
                email = jwtUtil.getEmailFromToken(token);

                if (email == null || email.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Token no contiene un email válido.");
                    return;
                }

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    if (jwtUtil.validateToken(token)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Token inválido.");
                        return;
                    }
                }
            }
            // Si no hay header o no es Bearer, se permite la petición (para rutas públicas)
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("No autorizado o token malformado: " + e.getMessage());
        }
    }
}