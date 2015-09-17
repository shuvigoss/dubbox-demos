# 快速开始。
10分钟内实现服务注册、订阅。

## 准备工作
[本地安装](http://dangdangdotcom.github.io/dubbox/demo.html)后，maven install 到本地仓库，dubboxjar包就生成了，使用者可以通过

    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>dubbo</artifactId>
        <version>2.8.4</version>
    </dependency>

坐标引入到本地工程。

接下来引入一些基本jar包（这里选择使用zookeeper做注册中心）

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>3.2.4.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.4.6</version>
    </dependency>
    <dependency>
        <groupId>com.github.sgroschupf</groupId>
        <artifactId>zkclient</artifactId>
        <version>0.1</version>
    </dependency>

提示：如果想使用redis等其他注册中心，请引入[相关](http://dubbo.io/Dependencies-zh.htm)jar包。

拿登录做个例子，如果在本地有个登录的服务.
### 服务提供方(Provider)
    //登录接口
    public interface LoginService {
        public LoginResult login(User user);
    }

    //登录接口实现
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

    //登录校验
    public interface LoginCheckService {
        public void check(User user);
    }

    //登录校验实现
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

    <!--spring 配置-->
    <bean id="loginCheck" class="com.shuvigoss.services.LoginCheckServiceImpl"></bean>

    <bean id="login" class="com.shuvigoss.services.LoginServiceImpl">
        <property name="loginCheckService" ref="loginCheck"></property>
    </bean>

    //调用实现
    LoginService loginService = applicationContext.getBean(LoginService.class);
    LoginResult loginResultSuccess = loginService.login(new User("shuwei", "111"));
    //...其他处理

如果要把LoginService的login方法提供给别的业务系统调用，通过dubbox只需要这么做就好了。

    <dubbo:application name="testProvider" owner="shuvigoss" organization="ecs"/>
    <dubbo:protocol name="dubbo" port="20880" />
    <dubbo:registry address="zookeeper://127.0.0.1:2181"/>
    <dubbo:service interface="com.shuvigoss.services.LoginService" ref="login"/>

只需在spring中使用`<dubbo:service interface="com.shuvigoss.services.LoginService" ref="login"/>`暴露出来，dubbox会在zookeeper中将服务注册。

### 服务调用方(Consumer)
    <dubbo:application name="testConsumer" owner="shuvigoss" organization="ecs"/>
    <dubbo:registry address="zookeeper://127.0.0.1:2181"/>
    <dubbo:reference id="loginService" interface="com.shuvigoss.services.LoginService"/>

通过dubbo:reference从zookeeper处拿到`com.shuvigoss.services.LoginService`服务的服务器列表。

    <!--spring 注入loginService 看似跟本地调用一样-->
    <bean id="ecard" class="com.shuvigoss.services.ECardServiceImpl">
        <property name="loginService" ref="loginService"></property>
    </bean>

    //本地取卡服务
    public class ECardServiceImpl implements ECardService {

        private LoginService loginService;

        public String getCard(String username, String password) {
            //首先登录
            LoginResult loginResult = loginService.login(new User(username, password));
            //登录成功返回卡号"AAAA"
            if (Objects.equals("0000", loginResult.getResponseCode())) {
                return "AAAA";
            }
            //登录失败返回空
            return null;
        }

        public void setLoginService(LoginService loginService) {
            this.loginService = loginService;
        }
    }

    //调用点
    ECardService eCardService = applicationContext.getBean(ECardService.class);
    assertEquals("AAAA", eCardService.getCard("shuwei", "111"));

    
代码很简单，通过简单的spring配置，完成了服务的注册、订阅、调用的工作。

    这儿有个问题，`Consumer`在调用`LoginService`时，需要传入`User`类做参数，  
    并且返回值是`LoginResult`这个对象，`Consumer`本地并没有User、LoginResult、LoginService对象。  

我认为有3种方式去实现。
1. 使用String json方式进行交互。
2. 将各业务平台API进行梳理，API无需过多的说明，直接把interface、javabean放到服务器上，谁需要调用谁就将所需文件集成到本地。（适合其他不是很熟悉的业务平台）
3. 内部的业务平台所有interface、javabean以jar包方式打到maven 仓库中，需要的业务平台在自己的pom中加入依赖（推荐）。

