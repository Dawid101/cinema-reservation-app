package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.ScreeningSeatResp;
import com.cinema_reservation_app.entity.Screening;
import com.cinema_reservation_app.exception.ScreeningNotFoundException;
import com.cinema_reservation_app.mapper.ScreeningMapper;
import com.cinema_reservation_app.repository.ScreeningRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepo screeningRepo;
    private final ScreeningMapper screeningMapper;

    public ScreeningSeatResp getScreeningById(Long id){
        Screening screening = screeningRepo.findById(id).orElseThrow(() -> new ScreeningNotFoundException("Screening not found"));
        return screeningMapper.toScreeningSeatResp(screening);
    }
}
