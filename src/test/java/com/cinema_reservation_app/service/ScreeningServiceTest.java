package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.ScreeningSeatResp;
import com.cinema_reservation_app.entity.Screening;
import com.cinema_reservation_app.mapper.ScreeningMapper;
import com.cinema_reservation_app.repository.ScreeningRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {

    @Mock
    private ScreeningRepo screeningRepo;
    @Mock
    private ScreeningMapper screeningMapper;

    @InjectMocks
    private ScreeningService screeningService;

    @Test
    void getScreeningById_whenExist_shouldReturnScreeningSeatResp() {
        //given
        Screening screening = new Screening(1L, null, null, null, null);
        ScreeningSeatResp screeningSeatResp = new ScreeningSeatResp(1L, null, 0L, 0, null, null);
        when(screeningRepo.findById(1L)).thenReturn(Optional.of(screening));
        when(screeningMapper.toScreeningSeatResp(screening)).thenReturn(screeningSeatResp);

        //when
        ScreeningSeatResp result = screeningService.getScreeningById(1L);

        //then
        assertNotNull(result);
        verify(screeningRepo).findById(1L);
        verify(screeningMapper).toScreeningSeatResp(screening);
    }

}