package org.chai.seat.vo;


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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
