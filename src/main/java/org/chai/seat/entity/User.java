package org.chai.seat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("users")
public class User {

    @TableField(exist = false)
    private Integer id;
    private String username;
    private String password;
    private String userGroup;
    private String role;
    private String displayName;

}
