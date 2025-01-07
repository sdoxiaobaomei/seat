package org.chai.seat.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingVO {
    private Long seatId;      // 座位 ID
    private Long userId;
    private String username; // 用户 ID
    private LocalDate bookingDate; // 预订日期
}
