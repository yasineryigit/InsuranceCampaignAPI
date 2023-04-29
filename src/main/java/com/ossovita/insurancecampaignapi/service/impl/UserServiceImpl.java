package com.ossovita.insurancecampaignapi.service.impl;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.error.exception.UserNotFoundException;
import com.ossovita.insurancecampaignapi.payload.request.UserRequest;
import com.ossovita.insurancecampaignapi.payload.response.UserResponse;
import com.ossovita.insurancecampaignapi.repository.UserRepository;
import com.ossovita.insurancecampaignapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found by given email: " + userEmail));
    }

    @Override
    public User findByUserId(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by given id: " + userId));
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .userEmail(userRequest.getUserEmail())
                .userPassword(this.passwordEncoder.encode(userRequest.getUserPassword()))
                .userRoleId(2)//todo make enum type
                .userFirstName(userRequest.getUserFirstName())
                .userLastName(userRequest.getUserLastName())
                .enabled(true)
                .locked(false)
                .build();
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }


    @Override
    public User updateUserEnabled(long userId, boolean enabled) {
        User userInDB = findByUserId(userId);
        userInDB.setEnabled(enabled);
        return userRepository.save(userInDB);
    }
}
