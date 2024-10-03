package org.chai.seat.vo;


import lombok.Data;

@Data
public class UserVO {
    private String username;
    private String password = "123";
    private String displayName;
    private String userGroup;

    public UserVO(String username, String displayName, String userGroup) {

        this.username = username;
        this.displayName = displayName;
        this.userGroup = userGroup;
    }


}
