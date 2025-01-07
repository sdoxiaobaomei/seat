package org.chai.seat.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.chai.seat.entity.Seat;
import org.chai.seat.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController{

    @Autowired
    private SeatService seatService;

    // 获取所有座位
    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    // 根据ID获取座位
    @GetMapping("/{seatId}")
    public Seat getSeatById(@PathVariable Long seatId) {
        return seatService.getSeatById(seatId);
    }

    // 添加座位
    @PostMapping
    public ResponseEntity<String> addSeat(@RequestBody Seat seat) {
        if (seatService.addSeat(seat)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Seat added successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add seat");
    }

    // 更新座位
    @PutMapping("/{seatId}")
    public ResponseEntity<String> updateSeat(@PathVariable Long seatId, @RequestBody Seat seat) {
        if (seatService.updateSeat(seatId, seat)) {
            return ResponseEntity.ok("Seat updated successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update seat");
    }

    // 删除座位
    @DeleteMapping("/{seatId}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long seatId) {
        if (seatService.deleteSeat(seatId)) {
            return ResponseEntity.ok("Seat deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete seat");
    }
}
