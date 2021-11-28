package com.koctas.movie.service;

import com.koctas.movie.model.data.AuthData;

/**
 * @author hkaynar on 28.11.2021
 */
public interface AuthService {
    AuthData authenticate(AuthData authData);
    void register(AuthData authData);
}
