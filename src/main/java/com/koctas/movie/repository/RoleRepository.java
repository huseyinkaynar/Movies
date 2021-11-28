package com.koctas.movie.repository;

import com.koctas.movie.model.entity.security.RoleModel;
import com.koctas.movie.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author hkaynar on 28.11.2021
 */
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
        Optional<RoleModel> findByName(ERole name);
}
