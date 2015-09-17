package com.shuvigoss.services;

import com.shuvigoss.services.bean.LoginResult;
import com.shuvigoss.services.bean.User;

import java.util.Objects;

/**
 * Package:com.shuvigoss.services
 * User: shuvigoss
 * Date: 15/9/16
 * Time: 下午5:15
 * Desc:
 *
 *
 */
public class ECardServiceImpl implements ECardService {

    private LoginService loginService;

    public String getCard(String username, String password) {
        LoginResult loginResult = loginService.login(new User(username, password));
        if (Objects.equals("0000", loginResult.getResponseCode())) {
            return "AAAA";
        }
        return "BBBB";
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
