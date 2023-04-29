package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.entity.RefreshToken;
import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.error.exception.RefreshTokenException;
import com.ossovita.insurancecampaignapi.payload.request.LoginRequest;
import com.ossovita.insurancecampaignapi.payload.request.TokenRefreshRequest;
import com.ossovita.insurancecampaignapi.payload.response.AuthResponse;
import com.ossovita.insurancecampaignapi.payload.response.TokenRefreshResponse;
import com.ossovita.insurancecampaignapi.security.CustomUserDetails;
import com.ossovita.insurancecampaignapi.security.jwt.JwtUtils;
import com.ossovita.insurancecampaignapi.security.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login request received: " + loginRequest.toString());
        String userEmail = loginRequest.getUserEmail();
        String userPassword = loginRequest.getUserPassword();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userEmail, userPassword);

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        log.info("User roles: " + roles.stream().toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUser().getUserId());

        AuthResponse authResponse = new AuthResponse();

        authResponse.setUserFirstName(userDetails.getUser().getUserFirstName());
        authResponse.setUserLastName(userDetails.getUser().getUserLastName());
        authResponse.setUserEmail(userDetails.getUser().getUserEmail());
        authResponse.setUserId(userDetails.getUser().getUserId());
        authResponse.setToken(jwt);
        authResponse.setRefreshToken(refreshToken.getToken());


        return ResponseEntity.ok(authResponse);
    }

    /*
       TODO fix | expired refresh token should return an exception and user should provide his credentials again to get new access token
   * */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken token = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken + "Refresh token is not in database!"));

        RefreshToken deletedToken = refreshTokenService.verifyExpiration(token);

        User userRefreshToken = deletedToken.getUser();

        String newToken = jwtUtils.generateTokenFromUserEmail(userRefreshToken.getUserEmail());

        return ResponseEntity.ok(new TokenRefreshResponse(newToken, requestRefreshToken));
    }




}
