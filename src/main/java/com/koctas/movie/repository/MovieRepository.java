package com.koctas.movie.repository;

import com.koctas.movie.model.entity.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hkaynar on 27.11.2021
 */
public interface MovieRepository extends JpaRepository<MovieModel, Long> {
    MovieModel getMovieModelById(long id);
}
