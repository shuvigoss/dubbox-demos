package com.shuvigoss.services.bean;

import java.io.Serializable;

/**
 * Package:com.shuvigoss.services.bean
 * User: shuvigoss
 * Date: 15/9/16
 * Time: 下午3:49
 * Desc:
 *
 *
 */
public class User implements Serializable {

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;

    private String password;

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
}
