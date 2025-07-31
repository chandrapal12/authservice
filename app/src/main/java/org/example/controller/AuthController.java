package org.example.controller;


import org.example.entities.RefreshToken;
import org.example.model.UserDto;
import org.example.response.JwtResponseDto;
import org.example.service.JwtService;
import org.example.service.RefreshTokenService;
import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("auth/v1/signup")
    public ResponseEntity<Object> Signup(@RequestBody UserDto userDto){
        try{
            Boolean isSignup = userDetailsServiceImpl.signupUser(userDto);
            if(Boolean.FALSE.equals(isSignup)){
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken =  refreshTokenService.createRefreshToken(userDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userDto.getUsername());

            return new ResponseEntity<>(JwtResponseDto.builder().accessToken(jwtToken).token(refreshToken.getToken()).build(), HttpStatus.OK))
        }catch(Exception ex){
                return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
