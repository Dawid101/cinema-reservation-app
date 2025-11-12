package com.cinema_reservation_app.controller;

import com.cinema_reservation_app.dto.UserRegistrationReq;
import com.cinema_reservation_app.dto.UserRegistrationResp;
import com.cinema_reservation_app.entity.User;
import com.cinema_reservation_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResp> createUser(@RequestBody UserRegistrationReq userRegistrationReq){
        User user = userService.create(userRegistrationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserRegistrationResp.fromUser(user));
    }
}
