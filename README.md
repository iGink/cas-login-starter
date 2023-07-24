# CAS-login-starter

### 简介
CAS登录框架，使springboot项目可以简单接入cas登录


### 为什么用
1. 其实CAS官方已经提供了cas的springboot登录sdk了，项目地址：[java-cas-client](https://github.com/apereo/java-cas-client)，在这里面的cas-client-support-springboot项目也已经实现了可以快速接入cas的代码。但是这个项目是基于spring security去实现的。初学者理解起来比较费劲
2. 网上开源的项目多时依赖spring security或者是shiro这种比较重的框架，不适用于轻量接入
3. 本项目目的是在不依赖各种安全框架的情况下使用拦截器简单实现一个cas登录，并保证代码简单可靠

### 使用文档
1. 引入依赖
```xml
<dependency>
    <groupId>top.gink</groupId>
    <artifactId>cas-login-starter</artifactId>
    <version>0.0.2</version>
</dependency>
```
2. 在项目中使用@EnableCasLogin注解启用cas登录
```java
//引入注解
@EnableCasLogin
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
```

3. 在application.proerties中配置host地址

```
cas.base_url=https://aa.aa.aa
```
4. 完成
