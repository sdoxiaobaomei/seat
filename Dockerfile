# Seat Booking 应用 Docker 镜像
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 复制构建好的 JAR 包
COPY target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
