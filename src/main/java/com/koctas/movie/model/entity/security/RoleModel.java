package com.koctas.movie.model.entity.security;

import com.koctas.movie.model.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

/**
 * @author hkaynar on 28.11.2021
 */
@Entity
@Data
@AllArgsConstructor
@Table(name = "roles")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public RoleModel() {

    }

    public RoleModel(ERole name) {
        this.name = name;
    }
}
