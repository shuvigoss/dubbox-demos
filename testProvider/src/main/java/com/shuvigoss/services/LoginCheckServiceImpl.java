package com.shuvigoss.services;

import com.shuvigoss.services.bean.User;

import java.util.Objects;

import static com.google.common.base.Preconditions.*;

/**
 * Package:com.shuvigoss.services
 * User: shuvigoss
 * Date: 15/9/16
 * Time: 下午3:52
 * Desc:
 *
 *
 */
public class LoginCheckServiceImpl implements LoginCheckService{

    public void check(User user) {
        argCheck(user);
        passwordCheck(user);
    }

    private void argCheck(User user) {
        checkNotNull(user);
        checkArgument(user.getUsername() != null);
        checkArgument(user.getPassword() != null);
    }

    private void passwordCheck(User user) {
        if (!(Objects.equals("shuwei", user.getUsername()) && Objects.equals("111", user.getPassword())))
            throw new RuntimeException("错误的用户名密码");
    }

}
