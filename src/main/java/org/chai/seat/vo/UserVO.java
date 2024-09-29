package org.chai.seat.vo;


public class UserVO {
    private String username;
    private String password = "123";
    private String displayName;
    private String group;

    public UserVO(String username, String displayName, String group) {

        this.username = username;
        this.displayName = displayName;
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
