package com.koctas.movie.service.imp;

import com.koctas.movie.model.data.MovieData;
import com.koctas.movie.model.entity.MovieModel;
import com.koctas.movie.repository.MovieRepository;
import com.koctas.movie.service.MovieService;
import com.koctas.movie.service.exception.model.ModelNotFoundException;
import com.koctas.movie.service.exception.model.ModelRemoveException;
import com.koctas.movie.service.exception.model.ModelSaveException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author hkaynar on 27.11.2021
 */
@Service
@Slf4j
public class MovieServiceImp implements MovieService {

    @Value("${external.service.url}")
    private String SERVICE_URL;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MovieData> getAllMovie() {

        var movies = movieRepository.findAll();
        if (CollectionUtils.isEmpty(movies)) {
            throw new ModelNotFoundException("Not found MovieModel");
        }
        return List.of(modelMapper.map(movies, MovieData[].class));
    }

    @Override
    public MovieData getMovieByCode(long id) {
        var movieModel = movieRepository.getMovieModelById(id);
        if (Objects.isNull(movieModel)) {
            log.error("{} movieModel not found", id);
            throw new ModelNotFoundException(String.format("%s id not found on MovieModel", id));
        }
        var movieData = modelMapper.map(movieModel, MovieData.class);

        return movieData;
    }

    @Override
    public void saveMovieFromExternalService() {
        String url = SERVICE_URL;
        RestTemplate restTemplate = new RestTemplate();
        Object response = restTemplate.getForObject(url, Object.class);

        var movies = ((LinkedHashMap) response).get("results");

        try {
            var movieData = List.of(modelMapper.map(movies, MovieModel[].class));
            movieRepository.saveAll(movieData);
        } catch (Exception e) {
            log.error("Error occurred while saving movie =>  {}", e.getMessage());
            throw new ModelSaveException("Error occurred while saving movie : " + e.getMessage());
        }
    }

    @Override
    public void saveMovie(MovieData movieData) {

        try {
            var movieModel = modelMapper.map(movieData, MovieModel.class);
            movieRepository.save(movieModel);
        } catch (Exception e) {
            log.error("Error occurred while saving movie =>  {}", e.getMessage());
            throw new ModelSaveException("Error occurred while saving movie : " + e.getMessage());
        }

    }

    @Override
    public void update(long id, MovieData movieData) {
        try {
            var movieModel = movieRepository.getById(id);
            modelMapper.map(movieData, movieModel);

            movieRepository.save(movieModel);
        } catch (Exception e) {
            log.error("Error occurred while saving movie =>  {}", e.getMessage());
            throw new ModelSaveException("Error occurred while saving movie : " + e.getMessage());
        }

    }

    @Override
    public void remove(long id) {
        try {
            var mediaModel = movieRepository.getMovieModelById(id);
            if (Objects.isNull(mediaModel)) {
                throw new ModelRemoveException("Model Remove Exception...MediaModel is null");
            }
            movieRepository.delete(mediaModel);

        } catch (Exception e) {
            log.error("Error while removing model.Error message : {}", e.getMessage());
            throw new ModelRemoveException("Model Remove Exception.... " + e.getMessage());
        }

    }
}
