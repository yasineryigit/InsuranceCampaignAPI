package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.payload.request.UserRequest;
import com.ossovita.insurancecampaignapi.payload.response.UserResponse;
import com.ossovita.insurancecampaignapi.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    //admin
    //@PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/update-user-enabled")
    User updateUserEnabled(long userId, boolean enabled) {
        return userService.updateUserEnabled(userId, enabled);
    }


}
