package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.ReservationReq;
import com.cinema_reservation_app.dto.ReservationSeatReq;
import com.cinema_reservation_app.entity.Screening;
import com.cinema_reservation_app.entity.TicketType;
import com.cinema_reservation_app.exception.SeatUnavailableException;
import com.cinema_reservation_app.repository.ScreeningRepo;
import com.cinema_reservation_app.repository.SeatRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class CreateReservationServiceTest {


    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SeatRepo seatRepo;

    @Autowired
    private ScreeningRepo screeningRepo;

    @Test
    void shouldPreventDoubleBookingWhenTwoUsersReserveSameSeatsSimultaneously() throws Exception {
        // given
        Screening screening = screeningRepo.findAll().get(0); // przykładowy screening
        Long seatId = 6L;     // wybieramy konkretne miejsce

        ReservationReq request = new ReservationReq(
                screening.getId(),
                List.of(new ReservationSeatReq(seatId, TicketType.NORMAL))
        );

        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Callable<String>> tasks = new ArrayList<>();

        tasks.add(() -> tryCreateReservation(request, "User1"));
        tasks.add(() -> tryCreateReservation(request, "User2"));

        // when
        List<Future<String>> results = executor.invokeAll(tasks);

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // then
        long successCount = results.stream()
                .filter(f -> {
                    try {
                        return f.get().contains("SUCCESS");
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();

        long failureCount = results.size() - successCount;

        System.out.printf("✅ SUCCESS=%d, ❌ FAILED=%d%n", successCount, failureCount);

        // Oczekujemy, że tylko jeden użytkownik odniósł sukces
        assertEquals(1, successCount, "Only one reservation should succeed");
        assertEquals(1, failureCount, "One reservation should fail due to seat lock");
    }

    private String tryCreateReservation(ReservationReq req, String userLabel) {
        try {
            reservationService.createReservation(req);
            System.out.println(userLabel + ": SUCCESS");
            return "SUCCESS";
        } catch (SeatUnavailableException e) {
            System.out.println(userLabel + ": FAILED (seat unavailable)");
            return "FAILED";
        } catch (Exception e) {
            System.out.println(userLabel + ": ERROR " + e.getMessage());
            return "ERROR";
        }
    }
}