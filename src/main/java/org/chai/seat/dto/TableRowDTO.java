package org.chai.seat.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TableRowDTO {
    private Long seatId; // 座位 ID
    private String seatName; // 座位名（由 name 和 group 组成）
    private Map<String, String> subscriptions; // 有订阅的日期和用户
}
