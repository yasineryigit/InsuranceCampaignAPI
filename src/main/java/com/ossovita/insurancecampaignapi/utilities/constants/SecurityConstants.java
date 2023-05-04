package com.ossovita.insurancecampaignapi.utilities.constants;

import java.util.Arrays;
import java.util.List;

public final class SecurityConstants {

    public static final String CREATE_USER_URL = "/api/1.0/users/create";
    public static final String LOGIN_URL = "/api/1.0/auth/login";
    public static final String REFRESH_TOKEN_URL = "/api/1.0/auth/refresh-token";
    public static final String SWAGGER_UI_URL = "/api/1.0/swagger-ui.html/**";


    // private constructor to prevent instantiation
    private SecurityConstants() {
    }

    public static List<String> getIgnoringUrls() {
        return Arrays.asList(
                CREATE_USER_URL,
                LOGIN_URL,
                REFRESH_TOKEN_URL,
                SWAGGER_UI_URL
        );
    }
}

