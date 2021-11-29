package com.koctas.movie.model.data;

import com.koctas.movie.model.entity.security.RoleModel;
import lombok.Data;

import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author hkaynar on 28.11.2021
 */
@Data
public class UserData {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Set<?> roles;
}
