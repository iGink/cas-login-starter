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
    <version>0.0.6</version>
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
cas.enabled=true
cas.login_success_url=/
cas.base_url=_
cas.server_url=http://localhost:8080
cas.service_validate_url=${cas.base_url}/cas/serviceValidate
cas.login_url=${cas.base_url}/cas/login?service=${cas.callback_url}
cas.logout_url=${cas.base_url}/cas/logout?service=${cas.callback_url}
cas.callback_url=${cas.server_url}/cas/callback
cas.ignore_path[0]=/static/**
cas.ignore_path[1]=/favicon.ico
cas.ignore_path[2]=/error

```
4. 完成

### 推广
纯真(CZ88.NET)自2005年起一直为广大社区用户提供社区版IP地址库，只要获得纯真的授权就能免费使用，并不断获取后续更新的版本。如果有需要免费版IP库的朋友可以前往纯真的官网进行申请。

纯真除了免费的社区版IP库外，还提供数据更加准确、服务更加周全的商业版IP地址查询数据。纯真围绕IP地址，基于 网络空间拓扑测绘 + 移动位置大数据 方案，对IP地址定位、IP网络风险、IP使用场景、IP网络类型、秒拨侦测、VPN侦测、代理侦测、爬虫侦测、真人度等均有近20年丰富的数据沉淀。
