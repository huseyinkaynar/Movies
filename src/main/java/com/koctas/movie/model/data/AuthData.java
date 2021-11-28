package com.koctas.movie.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author hkaynar on 28.11.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthData {
    private String token;
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}
