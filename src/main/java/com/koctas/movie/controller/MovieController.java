package com.koctas.movie.controller;

import com.koctas.movie.model.ProcessStatus;
import com.koctas.movie.model.data.MovieData;
import com.koctas.movie.model.data.ServiceResponseData;
import com.koctas.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hkaynar on 27.11.2021
 */
@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("save-from-external-service")
    public ServiceResponseData saveMoviesFromExternal() {
        movieService.saveMovieFromExternalService();
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }

    @GetMapping
    public ServiceResponseData getMovies() {
        var movieData = movieService.getAllMovie();
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setData(movieData);
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }
    @GetMapping("/{id}")
    public ServiceResponseData getMovie(@PathVariable long id) {
        var movieData = movieService.getMovieByCode(id);
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setData(movieData);
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }

    @PutMapping("/{id}")
    public ServiceResponseData update(@PathVariable long id, @RequestBody MovieData movieData) {
        movieService.update(id, movieData);
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;
    }

    @DeleteMapping("/{id}")
    public ServiceResponseData remove(@PathVariable long id) {
        movieService.remove(id);
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }
}
