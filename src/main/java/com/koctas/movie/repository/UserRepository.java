package com.koctas.movie.repository;

import com.koctas.movie.model.entity.movie.MovieModel;
import com.koctas.movie.model.entity.security.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author hkaynar on 28.11.2021
 */
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    UserModel getUserModelById(long id);

}
