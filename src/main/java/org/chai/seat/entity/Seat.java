package org.chai.seat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("seats")
public class Seat {

    private String id;
    private String seatName;
    private String seatGroup;
    private LocalDateTime createdAt;
}
