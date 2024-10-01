package org.chai.seat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("seats")
public class Seat {

    @TableId
    private Long id;
    private String seatName;
    private String seatGroup;
    private LocalDateTime createdAt;
}
