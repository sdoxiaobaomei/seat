package org.chai.seat.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Data
@TableName("bookings")
public class Booking {
    @TableId
    private Long id;
    private Long seatId;
    private Long userId;
    private LocalDate bookingDate;
    private String status;
    private LocalDateTime createdAt;
}

