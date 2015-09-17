package com.shuvigoss.services;

import com.shuvigoss.services.bean.LoginResult;
import com.shuvigoss.services.bean.User;

/**
 * Package:com.shuvigoss.services
 * User: shuvigoss
 * Date: 15/9/16
 * Time: 下午3:37
 * Desc:
 *
 *
 */
public class LoginServiceImpl implements LoginService{

    private LoginCheckService loginCheckService;

    public LoginResult login(User user) {
        LoginResult loginResult;
        try {
            loginCheckService.check(user);
            loginResult = loginSuccess();
        } catch (Exception e) {
            loginResult = loginError();
        }
        return loginResult;
    }

    public void setLoginCheckService(LoginCheckService loginCheckService) {
        this.loginCheckService = loginCheckService;
    }

    private LoginResult loginSuccess() {
        return new LoginResult("0000", "成功啦");
    }

    private LoginResult loginError() {
        return new LoginResult("9999", "错误啦");
    }
}
