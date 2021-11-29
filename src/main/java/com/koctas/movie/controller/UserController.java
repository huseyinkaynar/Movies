package com.koctas.movie.controller;

import com.koctas.movie.model.data.ServiceResponseData;
import com.koctas.movie.model.enums.ProcessStatus;
import com.koctas.movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hkaynar on 28.11.2021
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ServiceResponseData getUsers() {
        var userData = userService.getAllUsers();
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setData(userData);
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }

    @GetMapping("/{id}")
    public ServiceResponseData getMovie(@PathVariable long id) {
        var userData = userService.getUserById(id);
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setData(userData);
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }
}
