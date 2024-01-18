package com.ecommers.service;

import com.ecommers.dto.ResponseDto;
import com.ecommers.dto.user.SignInDto;
import com.ecommers.dto.user.SigninResponseDto;
import com.ecommers.dto.user.Signup;
import com.ecommers.exceptions.AuthenticationFailException;
import com.ecommers.exceptions.CustomException;
import com.ecommers.models.AuthenticationToken;
import com.ecommers.models.User;
import com.ecommers.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;


    @Transactional
    public ResponseDto suignup(Signup signup){
        if (Objects.nonNull(userRepository.findByEmail(signup.getEmail()))){
            throw new CustomException("user already present");
        }
        String encryptedpassword = signup.getPassword();
        try {
            encryptedpassword = hashPassword(signup.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = new User();
        user.setEmail(signup.getEmail());
        user.setFirstName(signup.getFirstName());
        user.setLastName(signup.getLastName());
        user.setPassword(encryptedpassword);
        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);
        ResponseDto responseDto = new ResponseDto(true, "user created successfully!!!");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SigninResponseDto signIn(SignInDto signInDto) {
        User user = userRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)){
            throw new AuthenticationFailException("user is not valid");
        }
        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        AuthenticationToken token = authenticationService.getToken(user);
//        System.out.println(token);
        if (Objects.isNull(token)){
            throw new CustomException("token is not present!");
        }
        return new SigninResponseDto(true, token.getToken());
    }
}
