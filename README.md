![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d640749c820d41c1b9d9846663fa56aa~tplv-k3u1fbpfcp-watermark.image?)

<p align="center">
  <a href="#"><img src="https://img.shields.io/badge/Author-3y-orange.svg" alt="作者"></a>
  <a href="https://gitee.com/zhongfucheng/austin"><img src="https://gitee.com/zhongfucheng/hades/badge/star.svg?theme=dark" alt="Gitee Starts"></a>
  <a href="https://gitee.com/zhongfucheng/austin"><img src="https://gitee.com/zhongfucheng/hades/badge/fork.svg?theme=dark" alt="Gitee Starts"></a>
  <a href="https://github.com/ZhongFuCheng3y/austin"><img src="https://img.shields.io/github/forks/ZhongFuCheng3y/hades.svg?style=flat&label=GithubFork"></a> 
  <a href="https://github.com/ZhongFuCheng3y/austin"><img src="https://img.shields.io/github/stars/ZhongFuCheng3y/hades.svg?style=flat&label=GithubStars"></a>
  <a href="#项目交流"><img src="https://img.shields.io/github/issues/ZhongFuCheng3y/hades" alt="issue-open"></a>
  <a href="#项目交流"><img src="https://img.shields.io/github/issues-closed/ZhongFuCheng3y/hades" alt="issue-close"></a>
  <a href="#项目交流"><img src="https://img.shields.io/github/issues-pr/ZhongFuCheng3y/hades" alt="issue-close"></a>
  <a href="#项目交流"><img src="https://img.shields.io/github/issues-pr-closed/ZhongFuCheng3y/hades" alt="issue-close"></a>
  <a href="#项目交流"><img src="https://img.shields.io/github/license/ZhongFuCheng3y/hades" alt="issue-close"></a>
  <a href="#项目交流"><img src="https://img.shields.io/badge/JDK-8-red.svg" alt="jdk版本"></a>
  <a href="#项目交流"><img src="https://img.shields.io/badge/maven-3.6.x-red.svg" alt="maven版本"></a>
  <a href="#项目交流"><img src="https://img.shields.io/badge/Groovy-3.0.16-blue.svg" alt="ORM框架"></a>
  <a href="#项目交流"><img src="https://img.shields.io/badge/Apollo-2.1.0-orange.svg" alt="ORM框架"></a>
  <a href="#项目交流"><img src="https://img.shields.io/badge/Nacos-2.1.0-red.svg" alt="ORM框架"></a>
</p>

## 一、hades 介绍

项目背景：业务开发逻辑变更频繁，一点小的修改都要发布系统，灵活度不够。对于一些**非核心流程又频繁改动**的部分是否可以用**动态脚本**替代？

核心功能：**Java应用无须发布上下线，动态变更业务逻辑，对象实例均交由Spring管理**



## 二、hades 使用手册

### 2.1 引入maven依赖

```xml

<!--如果你用apollo，则引入该dependency，建议用最新的版本-->
<dependency>
    <groupId>io.github.ZhongFuCheng3y</groupId>
    <artifactId>hades-apollo-starter</artifactId>
    <version>1.0.4</version>
</dependency>

<!--如果你用nacos，则引入该dependency，建议用最新的版本-->
<dependency>
    <groupId>io.github.ZhongFuCheng3y</groupId>
    <artifactId>hades-nacos-starter</artifactId>
    <version>1.0.4</version>
</dependency>

```

### 2.2 配置信息

在接入`hades`的项目配置文件中（如：`application.properties`）填写`hades`相关的配置信息

`nacos`分布式配置中心示例：

```properties
nacos.config.server-addr=austin-nacos:8848
nacos.config.username=nacos
nacos.config.password=nacos
nacos.config.namespace=hades
nacos.config.enabled=true

# hades param you should write
hades.enabled=true
hades.config-name=hades
```

`apollo`分布式配置中心示例：

```properties

app.id=hades
apollo.bootstrap.enabled=true
apollo.meta=austin-apollo

# hades param you should write
hades.enabled=true
hades.config-name=hades
```
### 2.3 部署后端和前端（可选）

目前后端部署仅支持使用`nacos`的小伙伴，`hades`有对应配置页面，将`hades`的前端和后端部署起来会方便使用些。


后端是`SpringBoot`应用，入口：`com.java3y.hades.HadesApplication`，前端是`amis`低代码平台，点击前端[Git仓库](https://gitee.com/zhongfucheng/hades-admin)进行部署。

后端的模块是在`hades-web`，对应的配置文件在`application.properties`，按自身情况自行修改：

```properties
# hades param you should write
hades.enabled=true
hades.config-name=hades

# nacos
nacos.config.enabled=true
nacos.config.server-addr=austin-nacos:8848
nacos.config.username=nacos
nacos.config.password=nacos
nacos.config.namespace=hades

```

1、可选择直接添加Java代码，注意不要用**lombok**和**lambda**，groovy是不认的。
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e25df3922a7f48d7aff01d41671815fa~tplv-k3u1fbpfcp-watermark.image?)

2、如果是轻量级的逻辑，也可以直接在界面上配置逻辑，比如对字符串和数值的判断。当提交了之后，会根据条件自动生成对应`Java`代码，注册到`iOC`容器中

![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0129320edd2c4e4aa1ae73774ce807d7~tplv-k3u1fbpfcp-watermark.image?)

3、添加规则完后，能在规则列表中看到对应内容，并将其**脚本注册**到`Spring iOC`容器中，供客户端使用

![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3e5ce613284a4c699ed7cfab085bc285~tplv-k3u1fbpfcp-watermark.image?)

### 2.4 不部署后端和前端

完全可以只通过分布式配置中心的后台，**手动去创建配置**，来实现效果。步骤如下：

1、在对应的分布式配置中心创建`hades`主配置(配置名是`hades.config-name`的值)，配置内容如下：

```json
{
    "instanceNames": [
        "com.java3y.hades.domain.HadesParam"
    ],
    "updateTime": "2023年3月20日10:26:0131"
}
```
注：`hades`所有的配置内容**在分布式配置中心**默认均为`txt`格式。`hades`会监听以上配置的变更，当新增或修改了`Groovy`脚本时，需要**手动**更新该配置。

2、新建`Groovy`脚本，`apollo`需要跟**主配置**在同`appId`下，`nacos`在同`namespace`下。`Groovy`脚本跟`Java`代码**99%相同**（不写`lambda`和`lambda`即可）

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c5e929b392304c1ba78535df5f932568~tplv-k3u1fbpfcp-watermark.image?)



### 2.5使用客户端

客户端使用`HadesClient`获取对象或执行方法

```java
@RequestMapping("/test")
private void test() {
    // 获取脚本对象，用接口接收
    SendSmsService sendSmsService = hadesClient.getInterfaceByName("com.java3y.hades.example.apollo.demo.TencentSmsService");
    sendSmsService.send();

    // 获取GroovyObject对象
    GroovyObject groovyObject = hadesClient.getGroovyObjectByName("com.java3y.hades.example.apollo.demo.TencentSmsService");
    groovyObject.invokeMethod("send", null);
    log.info("groovy object:{}", groovyObject);

    // 直接执行脚本对应的方法，得到返回值
    Object execute = hadesClient.execute("com.java3y.hades.example.apollo.demo.TencentSmsService", "send", null);
    System.out.println("result" + execute);
}

```