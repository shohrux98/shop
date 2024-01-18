package com.ecommers.controller;


import com.ecommers.dto.ResponseDto;
import com.ecommers.dto.user.SignInDto;
import com.ecommers.dto.user.SigninResponseDto;
import com.ecommers.dto.user.Signup;
import com.ecommers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody Signup signup){
        ResponseDto suignup = userService.suignup(signup);
        return ResponseEntity.ok(suignup);
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> signIn(@RequestBody SignInDto signInDto){
        SigninResponseDto singin = userService.signIn(signInDto);
        return ResponseEntity.ok(singin);
    }
}
