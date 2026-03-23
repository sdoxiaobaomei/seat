package org.chai.seat.dto;

import lombok.Data;
import java.util.Map;

@Data
public class TableRowDTO {
    private String seatId;
    private String seatName;
    private Map<String, String> subscriptions;
}
