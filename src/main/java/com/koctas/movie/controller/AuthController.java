package com.koctas.movie.controller;

import com.koctas.movie.model.data.AuthData;
import com.koctas.movie.model.data.ServiceResponseData;
import com.koctas.movie.model.enums.ProcessStatus;
import com.koctas.movie.model.jwt.JwtUtils;
import com.koctas.movie.service.AuthService;
import com.koctas.movie.service.imp.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hkaynar on 28.11.2021
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public AuthData authenticateUser(@Valid @RequestBody AuthData authData) {
        return authService.authenticate(authData);
    }

    @PostMapping("/signup")
    public ServiceResponseData registerUser(@Valid @RequestBody AuthData authData) {
        authService.register(authData);
        var responseData = new ServiceResponseData();
        responseData.setStatus(ProcessStatus.SUCCESS);
        return responseData;
    }
}
