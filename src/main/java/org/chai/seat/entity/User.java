package org.chai.seat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    private String id;
    private String username;
    private String password;
    private String userGroup;
    private String role;
    private String displayName;
    private LocalDateTime createdAt;
}
