package com.backend.portfolio_ac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO seguro para exponer únicamente los datos públicos del usuario,
 * sin incluir ni el id ni el password.
 *
 * @author bunnystring
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSafeDto {
    private String name;
    private String email;
}