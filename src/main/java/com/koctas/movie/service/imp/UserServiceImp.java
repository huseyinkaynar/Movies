package com.koctas.movie.service.imp;

import com.koctas.movie.model.data.MovieData;
import com.koctas.movie.model.data.UserData;
import com.koctas.movie.repository.UserRepository;
import com.koctas.movie.service.UserService;
import com.koctas.movie.service.exception.model.ModelNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author hkaynar on 28.11.2021
 */
@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserData> getAllUsers() {
        var users = userRepository.findAll();
        if (CollectionUtils.isEmpty(users)) {
            log.error("Not found UserModel");
            throw new ModelNotFoundException("Not found UserModel");
        }
        return List.of(modelMapper.map(users, UserData[].class));
    }

    @Override
    public UserData getUserById(long id) {

        var userModel = userRepository.getUserModelById(id);
        if (Objects.isNull(userModel)) {
            log.error("{} userModel not found", id);
            throw new ModelNotFoundException(String.format("%s id not found on UserModel", id));
        }
        var userData = modelMapper.map(userModel, UserData.class);

        return userData;
    }
}
