package com.ossovita.insurancecampaignapi.payload.response;

import lombok.Data;

@Data
public class AuthResponse<T> {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;



}