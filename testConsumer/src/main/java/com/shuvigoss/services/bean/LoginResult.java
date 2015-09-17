package com.shuvigoss.services.bean;

import java.io.Serializable;

/**
 * Package:com.shuvigoss.services.bean
 * User: shuvigoss
 * Date: 15/9/16
 * Time: 下午3:50
 * Desc:
 *
 *
 */
public class LoginResult implements Serializable {

    public LoginResult(String responseCode, String userInfo) {
        this.responseCode = responseCode;
        this.userInfo = userInfo;
    }

    private String responseCode;

    private String userInfo;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
