package com.koctas.movie.service;

import com.koctas.movie.model.data.UserData;

import java.util.List;

/**
 * @author hkaynar on 28.11.2021
 */
public interface UserService {
    List<UserData> getAllUsers();

    UserData getUserById(long id);
}
