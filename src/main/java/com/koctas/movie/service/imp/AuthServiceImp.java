package com.koctas.movie.service.imp;

import com.koctas.movie.model.data.AuthData;
import com.koctas.movie.model.entity.security.RoleModel;
import com.koctas.movie.model.entity.security.UserModel;
import com.koctas.movie.model.enums.ERole;
import com.koctas.movie.model.jwt.JwtUtils;
import com.koctas.movie.repository.RoleRepository;
import com.koctas.movie.repository.UserRepository;
import com.koctas.movie.service.AuthService;
import com.koctas.movie.service.exception.model.ModelNotFoundException;
import com.koctas.movie.service.exception.model.ModelSaveException;
import com.koctas.movie.service.exception.model.UserException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hkaynar on 28.11.2021
 */
@Service
@Slf4j
public class AuthServiceImp implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public AuthData authenticate(AuthData authData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authData.getUsername(), authData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        var userAuthData = modelMapper.map(userDetails, AuthData.class);
        userAuthData.setRoles(roles);
        userAuthData.setToken(jwt);
        return userAuthData;
    }

    @Override
    public void register(AuthData authData) {

        if (userRepository.existsByUsername(authData.getUsername())) {
            log.error(String.join("Exception: ", "Username is already taken!"));
            throw new UserException("Username is already taken!");
        }

        if (userRepository.existsByEmail(authData.getEmail())) {
            log.error(String.join("Exception: ", "Email is already in use!"));
            throw new UserException("Email is already in use!");

        }

        // Create new user's account
        UserModel user = new UserModel(authData.getUsername(),
                authData.getEmail(),
                encoder.encode(authData.getPassword()));

        Set<String> strRoles = authData.getRoles();
        Set<RoleModel> roles = new HashSet<>();

        if (strRoles == null) {
            RoleModel userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleModel adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ModelNotFoundException("Role is not found"));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleModel modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new ModelNotFoundException("Role is not found"));
                        roles.add(modRole);

                        break;
                    default:
                        RoleModel userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() ->  new ModelNotFoundException("Role is not found"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occurred while saving user =>  {}", e.getMessage());
            throw new ModelSaveException("Error occurred while saving user : " + e.getMessage());
        }
    }
}
