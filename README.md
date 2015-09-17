# dubbox-demos
基于dubbox使用手册。

### dubbo vs dubbox
dubbo是阿里开源的 RPC服务调用框架。</br>
在他官方描述上是这么说的：Dubbo[音同double]是一个分布式服务框架，</br>
致力于提供高性能和透明化的RPC远程服务调用方案，以及SOA服务治理方案（开源版SOA基本没东西）。</br>
[github 地址 https://github.com/alibaba/dubbo](https://github.com/alibaba/dubbo)</br>
[官方文档地址 http://dubbo.io/Home-zh.htm](http://dubbo.io/Home-zh.htm)</br>
由于dubbo已经3年没有维护了（他们将dubbo的商用版(edas)放到了阿里云上，所以已经不维护开源版本了）
所以不基于dubbo，我选择[dubbox 当当开源版](https://github.com/dangdangdotcom/dubbox)进行二次开发
当当针对dubbo进行了spring、zookeeper、序列化等等升级，并且实现了基于httpComponent实现了RESTful调用。
    
### 一张图理解dubbox
![](http://dubbo.io/dubbo-architecture.jpg-version=1&modificationDate=1330892870000.jpg)</br>

### start
#### 安装
[本地安装](http://dangdangdotcom.github.io/dubbox/demo.html)非常简单，直接通过maven进行package即可。</br>
包结构</br>
![image](https://cloud.githubusercontent.com/assets/3062921/9924343/158a917c-5d31-11e5-8730-632b1c88ed1e.png)</br>
jar包缺省依赖</br>
![image](https://cloud.githubusercontent.com/assets/3062921/9924381/7eee2516-5d31-11e5-9d49-b72e5295e716.png)</br>
    可以看出，zookeeper、redis、mina等等包都没有依赖，那怎么运行呢？
    其实dubbox使用的是JDK5+提供的SPI进行自动加载jar包的机制，不过他对原始SPI进行了改进，用到时才加载。
    比如你想使用zookeeper作为注册中心，那么你就要做以下2个事情。
    1、在spring配置文件中增加<dubbo:registry address="zookeeper://127.0.0.1:2181"/>
    2、在你pom中加入：
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