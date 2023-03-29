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

### hades 介绍

项目背景：业务开发逻辑变更频繁，一点小的修改都要发布系统，灵活度不够。对于一些**非核心流程又频繁改动**的部分是否可以用动态脚本替代？

核心功能：**Java应用无须发布上下线，即可动态变更业务逻辑**


### hades 技术原理

核心技术：Groovy+分布式配置中心(`apollo`/`nacos`)

![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/9dfc77529693487fad9dba07c6d09480~tplv-k3u1fbpfcp-watermark.image?)

技术原理：将`Groovy`脚本写在配置中心里，借助分布式配置中心监听配置功能，客户端能实时发现`Groovy`脚本变更。`hades`规则引擎将`Groovy`脚本解析后，放入`Spring IOC`容器中供客户端使用。


### hades 使用手册

1、引入maven依赖：
```xml

<!--如果你用apollo，则引入该dependency-->
<dependency>
    <groupId>io.github.ZhongFuCheng3y</groupId>
    <artifactId>hades-apollo-starter</artifactId>
    <version>1.0.3</version>
</dependency>

<!--如果你用nacos，则引入该dependency-->
<dependency>
    <groupId>io.github.ZhongFuCheng3y</groupId>
    <artifactId>hades-nacos-starter</artifactId>
    <version>1.0.3</version>
</dependency>

```

2、在接入`hades`规则引擎的项目配置文件中（如：`application.properties`）填写主配置的文件名

`nacos`分布式配置中心示例：

```properties

nacos.config.server-addr=austin.nacos:8848
nacos.config.username=nacos
nacos.config.password=nacos
nacos.config.namespace=9537c674-f3a6-4203-b286-ef0c36bfacb2
nacos.config.enabled=true

# heades param you should write
hades.main.config.enabled=true
hades.main.config.file-name=hades
hades.main.config.group-name=hades
```

`apollo`分布式配置中心示例：

```properties

app.id=austin
apollo.bootstrap.enabled=true
apollo.meta=austin-apollo

# heades param you should write
hades.main.config.enabled=true
hades.main.config.file-name=hades
```

3、在对应的分布式配置中心创建`hades`主配置，配置内容如下：

（注：`hades`所有的配置内容**在分布式配置中心**默认均为`txt`格式）

```json
{
    "instanceNames": [
        "TencentSmsService"
    ],
    "updateTime": "2023年3月20日10:26:0131"
}
```
说明：`hades`主要是监听以上配置的变更，当新增或修改了`Groovy`脚本时，需要**手动**更新该配置。

4、新建`Groovy`脚本，`apollo`需要跟**主配置**在同`appId`下，`nacos`在同`namespace`下。

**注：`Groovy`脚本跟`Java`代码99%相同（特别要注意`lambda`语法和`lombok`的问题，建议不写`lambda`）**

5、客户端使用`HadesClient`获取对象或执行方法

```java

private void test() {
    // 获取脚本对象，用接口接收
    SendSmsService sendSmsService = hadesClient.getInterfaceByName("TencentSmsService");
    sendSmsService.send();

    // 获取GroovyObject对象
    GroovyObject groovyObject = hadesClient.getGroovyObjectByName("TencentSmsService");
    groovyObject.invokeMethod("send", null);
    log.info("groovy object:{}", groovyObject);

    // 直接执行脚本对应的方法，得到返回值
    Object execute = hadesClient.execute("TencentSmsService", "send", null);
    System.out.println("result" + execute);
}

```

### hades 示例

`hades-apollo-example`和`hades-nacos-example`可用于调试



