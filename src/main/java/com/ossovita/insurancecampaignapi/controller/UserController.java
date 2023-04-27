package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.payload.request.UserRequest;
import com.ossovita.insurancecampaignapi.payload.response.UserResponse;
import com.ossovita.insurancecampaignapi.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    //TODO | test register, add allowed urls to security constant list

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @PostMapping("/create")
    public UserResponse createUser(@Validated @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }
}
