package com.cinema_reservation_app.dto;

import com.cinema_reservation_app.entity.User;
import lombok.Builder;

@Builder
public record UserRegistrationResp(
        Long id,
        String username
) {
    public static UserRegistrationResp fromUser(User user){
        return UserRegistrationResp.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
