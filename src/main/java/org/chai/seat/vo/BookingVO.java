package org.chai.seat.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingVO {
    private String seatId;
    private String userId;
    private String username;
    private LocalDate bookingDate;
}
