# 基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制主机上的jar包至镜像内
COPY target/yuoj-code-sandbox-0.0.1-SNAPSHOT.jar app.jar

# 调试：列出当前目录内容并显示文件权限
RUN ls -la

# 容器启动执行命令
ENTRYPOINT ["sh", "-c", "ls -la && java -jar /app/app.jar --spring.profiles.active=prod"]

# 对外暴露的端口号
EXPOSE 8090