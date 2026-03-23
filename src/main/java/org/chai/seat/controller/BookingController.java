package org.chai.seat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.chai.seat.dao.BookingDao;
import org.chai.seat.dao.UserDao;
import org.chai.seat.dto.TableRowDTO;
import org.chai.seat.entity.Booking;
import org.chai.seat.entity.Seat;
import org.chai.seat.entity.User;
import org.chai.seat.service.BookingService;
import org.chai.seat.vo.BookingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;
    private final BookingDao bookingDao;
    private final UserDao userDao;

    @Autowired
    public BookingController(BookingService bookingService, BookingDao bookingDao, UserDao userDao) {
        this.bookingService = bookingService;
        this.bookingDao = bookingDao;
        this.userDao = userDao;
    }

    @PostMapping("/tableData")
    public ResponseEntity<List<TableRowDTO>> getTableData(@RequestBody List<String> dates) {
        List<TableRowDTO> tableData = bookingService.getTableData(dates);
        return ResponseEntity.ok(tableData);
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookSeat(@RequestBody BookingVO bookingRequest) {
        boolean success = bookingService.bookSeat(
                bookingRequest.getSeatId(),
                bookingRequest.getUsername(),
                bookingRequest.getBookingDate()
        );
        if (success) {
            return ResponseEntity.ok("预订成功！");
        } else {
            return ResponseEntity.badRequest().body("预订失败，座位已被预订！");
        }
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelBooking(
            @RequestParam String seatId,
            @RequestParam String userId,
            @RequestParam String bookingDate) {
        boolean success = bookingService.cancelBooking(seatId, userId, LocalDate.parse(bookingDate));
        if (success) {
            return ResponseEntity.ok("取消预订成功！");
        } else {
            return ResponseEntity.badRequest().body("取消预订失败，未找到对应的记录！");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String userId) {
        List<Booking> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/date/{bookingDate}")
    public ResponseEntity<List<Booking>> getBookingsByDate(@PathVariable String bookingDate) {
        List<Booking> bookings = bookingService.getBookingsByDate(LocalDate.parse(bookingDate));
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/available-seats")
    public ResponseEntity<List<Seat>> getAvailableSeatsByUserAndDate(
            @RequestParam String userId,
            @RequestParam String bookingDate) {
        List<Seat> availableSeats = bookingService.getAvailableSeatsByUserAndDate(userId, LocalDate.parse(bookingDate));
        return ResponseEntity.ok(availableSeats);
    }

    @GetMapping("/available-seats-month/{userId}")
    public ResponseEntity<Map<LocalDate, List<Seat>>> getAvailableSeatsForUserInCurrentMonth(@PathVariable String userId) {
        Map<LocalDate, List<Seat>> availableSeats = bookingService.getAvailableSeatsForUserInCurrentMonth(userId);
        return ResponseEntity.ok(availableSeats);
    }

    @GetMapping("/seat-book/{seatId}")
    public ResponseEntity<Map<String, Object>> getSeatBookings(@PathVariable String seatId) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", seatId);
        
        List<Map<String, String>> dates = bookingDao.selectList(
            new QueryWrapper<Booking>().eq("seat_id", seatId)
        ).stream().map(b -> {
            Map<String, String> dateMap = new HashMap<>();
            dateMap.put("id", b.getBookingDate().toString());
            dateMap.put("date", b.getBookingDate().toString());
            User user = userDao.selectById(b.getUserId());
            dateMap.put("username", user != null ? user.getUsername() : "");
            return dateMap;
        }).collect(Collectors.toList());
        
        result.put("dates", dates);
        return ResponseEntity.ok(result);
    }
    
    @PatchMapping("/seat-book/{seatId}")
    public ResponseEntity<Map<String, Object>> updateSeatBookings(
            @PathVariable String seatId,
            @RequestBody Map<String, Object> updateData) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", seatId);
        result.put("updated", true);
        return ResponseEntity.ok(result);
    }
}
