# 多阶段构建 - Seat Booking 应用
# 阶段 1: 构建环境 (Maven + Node.js)
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# 复制项目文件
COPY pom.xml .
COPY package.json package-lock.json ./
COPY src/ ./src/

# 执行 Maven 构建 (自动触发前端构建)
RUN mvn clean package -DskipTests -B

# 阶段 2: 运行环境 (轻量 JRE)
FROM registry.cn-hangzhou.aliyuncs.com/acs/containernet:3.1

WORKDIR /app

# 复制构建好的 JAR
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
