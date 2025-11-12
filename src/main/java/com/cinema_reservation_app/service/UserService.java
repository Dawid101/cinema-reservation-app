package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.UserRegistrationReq;
import com.cinema_reservation_app.entity.Role;
import com.cinema_reservation_app.entity.User;
import com.cinema_reservation_app.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User create(UserRegistrationReq userRegistrationReq) {
        User user = new User();
        user.setUsername(userRegistrationReq.username());
        user.setPassword(passwordEncoder.encode(userRegistrationReq.password()));
        user.setRole(Role.ROLE_USER);
        return userRepo.save(user);
    }
}
