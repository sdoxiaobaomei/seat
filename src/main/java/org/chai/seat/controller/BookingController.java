package org.chai.seat.controller;

import org.chai.seat.dto.TableRowDTO;
import org.chai.seat.entity.Booking;
import org.chai.seat.entity.Seat;
import org.chai.seat.service.BookingService;
import org.chai.seat.vo.BookingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * 获取座位表格数据。
     *
     * @param dates 日期列表
     * @return 表格数据
     */
    @PostMapping("/tableData")
    public ResponseEntity<List<TableRowDTO>> getTableData(@RequestBody List<String> dates) {
        List<TableRowDTO> tableData = bookingService.getTableData(dates);
        return ResponseEntity.ok(tableData);
    }

    /**
     * 预订座位。
     *
     * @param seatId 座位 ID
     * @param userId 用户 ID
     * @param bookingDate 预订日期
     * @return 成功预订的响应
     */
    @CrossOrigin(origins = "http://localhost:5173")
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

    /**
     * 取消座位预订。
     *
     * @param seatId 座位 ID
     * @param userId 用户 ID
     * @param bookingDate 预订日期
     * @return 成功取消的响应
     */
    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelBooking(
            @RequestParam Long seatId,
            @RequestParam Long userId,
            @RequestParam String bookingDate) {
        boolean success = bookingService.cancelBooking(seatId, userId, LocalDate.parse(bookingDate));
        if (success) {
            return ResponseEntity.ok("取消预订成功！");
        } else {
            return ResponseEntity.badRequest().body("取消预订失败，未找到对应的记录！");
        }
    }

    /**
     * 获取指定用户的所有预订记录。
     *
     * @param userId 用户 ID
     * @return 用户的所有预订记录
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    /**
     * 按日期查询所有座位的预订信息。
     *
     * @param bookingDate 预订日期
     * @return 该日期所有座位的预订记录
     */
    @GetMapping("/date/{bookingDate}")
    public ResponseEntity<List<Booking>> getBookingsByDate(@PathVariable String bookingDate) {
        List<Booking> bookings = bookingService.getBookingsByDate(LocalDate.parse(bookingDate));
        return ResponseEntity.ok(bookings);
    }

    /**
     * 按用户和日期查询空闲座位。
     *
     * @param userId 用户 ID
     * @param bookingDate 预订日期
     * @return 用户在该日期可以预订的空闲座位
     */
    @GetMapping("/available-seats")
    public ResponseEntity<List<Seat>> getAvailableSeatsByUserAndDate(
            @RequestParam Long userId,
            @RequestParam String bookingDate) {
        List<Seat> availableSeats = bookingService.getAvailableSeatsByUserAndDate(userId, LocalDate.parse(bookingDate));
        return ResponseEntity.ok(availableSeats);
    }

    /**
     * 获取指定用户在当前月份所有日期的空闲座位（未预订的座位）。
     *
     * @param userId 用户 ID
     * @return 当前月份用户可以预订的空闲座位
     */
    @GetMapping("/available-seats-month/{userId}")
    public ResponseEntity<Map<LocalDate, List<Seat>>> getAvailableSeatsForUserInCurrentMonth(@PathVariable Long userId) {
        Map<LocalDate, List<Seat>> availableSeats = bookingService.getAvailableSeatsForUserInCurrentMonth(userId);
        return ResponseEntity.ok(availableSeats);
    }
}
