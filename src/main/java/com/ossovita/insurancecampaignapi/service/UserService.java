package com.ossovita.insurancecampaignapi.service;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.payload.request.UserRequest;
import com.ossovita.insurancecampaignapi.payload.response.UserResponse;

import java.util.Optional;

public interface UserService {

    User findByUserEmail(String userEmail);

    User findByUserId(long userId);

    UserResponse createUser(UserRequest userRequest);
}
