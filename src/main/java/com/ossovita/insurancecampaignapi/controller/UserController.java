package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.payload.request.UserRequest;
import com.ossovita.insurancecampaignapi.payload.response.UserResponse;
import com.ossovita.insurancecampaignapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "User Resource", description = "Create and Update User")
public class UserController {


    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @ApiOperation(value = "Create a new user and return UserResponse object", response = UserResponse.class)
    @PostMapping("/create")
    public UserResponse createUser(@Validated @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    //admin
    //@PreAuthorize("hasAuthority('Admin')")
    @ApiOperation(value = "Update user's enabled status", response = User.class)
    @PutMapping("/update-user-enabled")
    User updateUserEnabled(long userId, boolean enabled) {
        return userService.updateUserEnabled(userId, enabled);
    }


}
