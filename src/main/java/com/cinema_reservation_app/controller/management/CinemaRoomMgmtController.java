package com.cinema_reservation_app.controller.management;

import com.cinema_reservation_app.dto.CinemaRoomResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.service.CinemaRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cinema-room")
@RequiredArgsConstructor
public class CinemaRoomMgmtController {

    private final CinemaRoomService cinemaRoomService;

    @GetMapping("/all")
    public ResponseEntity<List<CinemaRoomResp>> getAllCinemaRooms() {
        return new ResponseEntity<>(cinemaRoomService.getCinemaRoomList(), HttpStatus.OK);
    }

    @GetMapping("/{number}")
    public ResponseEntity<CinemaRoomResp> getCinemaRoomByNumber(@PathVariable int number){
        return new ResponseEntity<>(cinemaRoomService.getCinemaRoomByNumber(number),HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<CinemaRoomResp> createCinemaRoom(@RequestBody CinemaRoom cinemaRoom){
        CinemaRoomResp createdCinemaRoom = cinemaRoomService.createCinemaRoom(cinemaRoom);
        return ResponseEntity.ok(createdCinemaRoom);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCinemaRoom(@PathVariable Long id){
        cinemaRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CinemaRoomResp> updateCinemaRoom(@PathVariable Long id, @RequestBody CinemaRoom cinemaRoom){
        CinemaRoomResp updatedCinemaRoom = cinemaRoomService.update(id,cinemaRoom);
        return ResponseEntity.ok(updatedCinemaRoom);
    }

}
