package org.chai.seat.controller;

import org.chai.seat.entity.Seat;
import org.chai.seat.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@CrossOrigin(origins = "*")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable String id) {
        return seatService.getSeatById(id);
    }

    @PostMapping
    public boolean addSeat(@RequestBody Seat seat) {
        return seatService.addSeat(seat);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSeat(@PathVariable String id) {
        return seatService.deleteSeat(id);
    }
}
