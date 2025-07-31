package org.example.service;

import org.example.entities.RefreshToken;
import org.example.entities.User;
import org.example.repository.RefreshTokenRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        User userInDb=userRepository.findByUsername(username);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userInDb)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);


    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken()+ "Token is expired. PLease make a new login");
        }
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token){
        return  refreshTokenRepository.findByToken(token);
    }
}
