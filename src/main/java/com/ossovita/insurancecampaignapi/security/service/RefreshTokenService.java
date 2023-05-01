package com.ossovita.insurancecampaignapi.security.service;

import com.ossovita.insurancecampaignapi.entity.RefreshToken;
import com.ossovita.insurancecampaignapi.error.exception.RefreshTokenException;
import com.ossovita.insurancecampaignapi.repository.RefreshTokenRepository;
import com.ossovita.insurancecampaignapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refreshExpireMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(long userId) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userService.findByUserId(userId));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    //if refresh-token expired delete and throw error ()
    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException("Refresh token expired");
        }

        return token;
    }

}