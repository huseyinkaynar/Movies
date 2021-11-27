package com.koctas.movie.service;

import com.koctas.movie.model.data.MovieData;

import java.util.List;

/**
 * @author hkaynar on 27.11.2021
 */
public interface MovieService {

    List<MovieData> getAllMovie();

    MovieData getMovieByCode(long id);

    void saveMovieFromExternalService();

    void saveMovie(MovieData movieData);

    void update(long id,MovieData movieData);

    void remove(long id);
}
