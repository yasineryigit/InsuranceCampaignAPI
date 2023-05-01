package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.payload.request.UserRequest;
import com.ossovita.insurancecampaignapi.payload.response.UserResponse;
import com.ossovita.insurancecampaignapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Api(value = "User Resource", description = "Create and Update User")
public class UserController {


    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @ApiOperation(value = "Create a new user and return UserResponse object", response = UserResponse.class)
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Validated @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    //admin
    @PreAuthorize("hasAuthority('Admin')")
    @ApiOperation(value = "Update user's enabled status", response = User.class)
    @PutMapping("/update-user-enabled")
    public ResponseEntity<User> updateUserEnabled(@RequestParam long userId, @RequestParam boolean enabled) {
        User updatedUser = userService.updateUserEnabled(userId, enabled);
        return ResponseEntity.ok(updatedUser);
    }

}
