package org.chai.seat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("bookings")
public class Booking {
    private Long id;
    private String seatId;
    private String userId;
    private LocalDate bookingDate;
    private String status;
    private LocalDateTime createdAt;
}
