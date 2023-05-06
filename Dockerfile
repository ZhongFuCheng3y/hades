# 使用openjdk8的镜像
FROM openjdk:8-jre

# 设置工作目录
WORKDIR /build

# 将jar包复制到容器中
ADD ./hades-web/target/hades-web-1.0.4.jar ./hades.jar

# 运行jar包
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS hades.jar $PARAMS"]
