package com.cinema_reservation_app.dto;

import java.time.LocalDate;
import java.util.List;

public record MovieResp(Long id,
                        String title,
                        String description,
                        int durationMinutes,
                        LocalDate releaseDate,
                        List<ScreeningResp> screenings) {
}
