package com.koctas.movie.service.imp;

import com.koctas.movie.model.data.MovieData;
import com.koctas.movie.model.entity.movie.MovieModel;
import com.koctas.movie.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hkaynar on 29.11.2021
 */
class MovieServiceImpTest {

    private MovieServiceImp movieServiceImp;
    private MovieRepository movieRepository;
    private ModelMapper modelMapper;
    private RestTemplate restTemplate;
    private MockMvc mockMvc;

    MovieData movieData1 = new MovieData((long) 123,"deneme","12.12.12","","",12,12,12);
    MovieData movieData2 = new MovieData((long) 1234,"deneme","12.12.12","","",12,12,12);


    @BeforeEach
    void setUp() {
        movieRepository = Mockito.mock(MovieRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        restTemplate = Mockito.mock(RestTemplate.class);
        movieServiceImp = new MovieServiceImp(movieRepository);


    }

    @Test
    public void whenSaveMovieWithRequest() {
        MovieData movieData = new MovieData();
        movieData.setId((long) 123);
        movieData.setOriginal_title("deneme");
        movieData.setOriginal_language("tr");
        movieData.setPopularity(150);
        movieData.setRelease_date("12.2.12");

        movieServiceImp.saveMovie(movieData);
    }

    @Test
    public void whenPostMovieId() {
        MovieData movieData = new MovieData();
        movieData.setId((long) 123);
        movieData.setTitle("Title");
        movieData.setOriginal_title("deneme");
        movieData.setOriginal_language("tr");
        movieData.setPopularity(150);
        movieData.setRelease_date("12.2.12");

        MovieModel movieModel = new MovieModel();
        movieModel.setId(movieData.getId());
        movieModel.setTitle(movieData.getTitle());
        movieModel.setOriginal_title(movieData.getOriginal_title());
        movieModel.setOriginal_language(movieData.getOriginal_language());
        movieModel.setPopularity(movieData.getPopularity());
        movieModel.setRelease_date(movieData.getRelease_date());


        Mockito.when(movieRepository.getMovieModelById(movieData.getId())).thenReturn(movieModel);

    }

    @Test
    public void externalService() throws Exception {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=af51a7125c68ebf5435e31b2151b9f8a";

        Object response = new Object();
        Mockito.when(restTemplate.getForObject(url, Object.class))
                .thenReturn(response);


    }

    @Test
    public void delete() throws Exception {

        MovieModel movie = new MovieModel();
        movie.setId(movieData1.getId());
        movie.setTitle(movieData1.getTitle());
        movie.setOriginal_title(movieData1.getOriginal_title());
        movie.setOriginal_language(movieData1.getOriginal_language());
        movie.setPopularity(movieData1.getPopularity());
        movie.setRelease_date(movieData1.getRelease_date());

        MovieModel movie1 = new MovieModel();
        movie.setId(movieData2.getId());
        movie.setTitle(movieData2.getTitle());
        movie.setOriginal_title(movieData2.getOriginal_title());
        movie.setOriginal_language(movieData2.getOriginal_language());
        movie.setPopularity(movieData2.getPopularity());
        movie.setRelease_date(movieData2.getRelease_date());


        Mockito.when(movieRepository.getMovieModelById(1L)).thenReturn(movie1); //expect a fetch, return a "fetched" person;

        movieServiceImp.remove(movie.getId());

        Mockito.verify(movieRepository, Mockito.times(1)).delete(movie1); //pretty sure it is verify after call

    }


}