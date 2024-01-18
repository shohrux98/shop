package com.ecommers.service;

import com.ecommers.exceptions.AuthenticationFailException;
import com.ecommers.models.AuthenticationToken;
import com.ecommers.models.User;
import com.ecommers.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token) {
        AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token);
        if (Objects.isNull(authenticationToken)) {
//            if (Objects.isNull(authenticationToken.getUser())) {
//                return authenticationToken.getUser();
//            }
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if (Objects.isNull(token)) {
            throw new AuthenticationFailException("token not present");
        }
        if (Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }
    }
}
