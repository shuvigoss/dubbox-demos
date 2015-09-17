package com.shuvigoss.services;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Package:com.shuvigoss.services
 * User: shuvigoss
 * Date: 15/9/16
 * Time: 下午4:52
 * Desc:
 *
 *
 */
public class LoginServiceProviderTest {

    static ApplicationContext applicationContext;

    @BeforeClass
    public static void initSpring() {
        applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring-local.xml",
                "classpath:spring-services.xml");
    }

    @Test
    public void registServices() throws InterruptedException {
        Thread.sleep(Integer.MAX_VALUE);
    }

    @AfterClass
    public static void destroySpring() {
        ((AbstractApplicationContext) applicationContext).destroy();
    }

}
