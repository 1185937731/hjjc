package com.ccnu.hjjc.Bean;

/**
 * 用户信息model
 */

public class UserInfo {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    private int monitor=-1;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMonitor() {
        return monitor;
    }

    public void setMonitor(int monitor) {
        this.monitor = monitor;
    }
}
