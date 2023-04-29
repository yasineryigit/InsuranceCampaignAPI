package com.ossovita.insurancecampaignapi.service;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.payload.request.UserRequest;
import com.ossovita.insurancecampaignapi.payload.response.UserResponse;

public interface UserService {

    User findByUserEmail(String userEmail);

    User findByUserId(long userId);

    UserResponse createUser(UserRequest userRequest);

    User updateUserEnabled(long userId, boolean status);
}
