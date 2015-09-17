package com.shuvigoss.services;

import com.shuvigoss.services.bean.LoginResult;
import com.shuvigoss.services.bean.User;

/**
 * Package:com.shuvigoss.services
 * User: shuvigoss
 * Date: 15/9/16
 * Time: 下午5:17
 * Desc:
 *
 *
 */
public interface LoginService {

    public LoginResult login(User user);

}
