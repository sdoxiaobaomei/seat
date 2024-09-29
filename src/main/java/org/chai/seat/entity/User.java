package org.chai.seat.entity;

public class User {

    private String username;
    private String password;
    private String group;
    private String role;
    private String displayName;

    public User(String username, String password, String group, String role, String displayName) {
        this.username = username;
        this.password = password;
        this.group = group;
        this.role = role;
        this.displayName = displayName;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
