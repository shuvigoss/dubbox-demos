# 协议配置(Provider)dubbo:protocol
配置类：`com.alibaba.dubbo.config.ProtocolConfig`</br>
说明：如果需要支持多协议，可以声明多个<dubbo:protocol>标签，并在<dubbo:service>中通过protocol属性指定使用的协议。

[具体描述](http://dubbo.io/User+Guide-zh.htm#UserGuide-zh-%253Cdubbo%253Aprotocol%252F%253E)

## 现有协议
	dubbo 
	hessian
	http
	injvm
	memcached
	redis
	rest
	rmi
	thrift
	webservice

## 多协议配置
	<!-- 多协议配置 -->
    <dubbo:protocol name="dubbo" port="20880" />
    <dubbo:protocol name="rmi" port="1099" />
 
    <!-- 使用dubbo协议暴露服务 -->
    <dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService" protocol="dubbo" />
    <!-- 使用rmi协议暴露服务 -->
    <dubbo:service interface="com.alibaba.hello.api.DemoService" version="1.0.0" ref="demoService" protocol="rmi" />

## 多协议暴露服务
	<!-- 多协议配置 -->
    <dubbo:protocol name="dubbo" port="20880" />
    <dubbo:protocol name="hessian" port="8080" />
 
    <!-- 使用多个协议暴露服务 -->
    <dubbo:service id="helloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0" protocol="dubbo,hessian" />

## 如何选择协议
	在内网中，建议使用dubbo协议。

## 序列化
	dubbox默认使用hessian lite(hessian2)进行序列化的，该序列化性能较低（3年前的东西），当当实现了基于kryo和fst的序列化策略。  
----
![image](https://cloud.githubusercontent.com/assets/3062921/9951454/131f6836-5df9-11e5-86bb-4fb7da87441d.png)
----
	<dependency>
	    <groupId>com.esotericsoftware.kryo</groupId>
	    <artifactId>kryo</artifactId>
	    <version>2.24.0</version>
	</dependency>
	<dependency>
	    <groupId>de.javakaffee</groupId>
	    <artifactId>kryo-serializers</artifactId>
	    <version>0.26</version>
	</dependency>

	<dubbo:protocol name="dubbo" serialization="kryo" optimizer="com.alibaba.dubbo.demo.SerializationOptimizerImpl"/>


	<dependency>
	    <groupId>de.ruedigermoeller</groupId>
	    <artifactId>fst</artifactId>
	    <version>1.55</version>
	</dependency>

	<dubbo:protocol name="dubbo" serialization="fst" optimizer="com.alibaba.dubbo.demo.SerializationOptimizerImpl"/>

	//可将现有需要序列化的class注册进去，能够更快的进行序列化。
	public class SerializationOptimizerImpl implements SerializationOptimizer {
	    public Collection<Class> getSerializableClasses() {
	        List<Class> classes = new LinkedList<Class>();
	        classes.add(BidRequest.class);
	        classes.add(BidResponse.class);
	        classes.add(Device.class);
	        classes.add(Geo.class);
	        classes.add(Impression.class);
	        classes.add(SeatBid.class);
	        return classes;
	    }
	}
