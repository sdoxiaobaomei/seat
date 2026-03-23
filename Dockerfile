# 多阶段构建 - Seat Booking 应用
# 阶段 1: 构建 Vue 前端
FROM node:20-alpine AS frontend-builder

WORKDIR /app

# 复制前端依赖文件
COPY package.json package-lock.json ./

# 安装依赖
RUN npm ci --only=production && npm install

# 复制前端源码
COPY vite.config.js tsconfig.json tsconfig.node.json index.html ./
COPY src/ ./src/

# 构建前端
RUN npm run build

# 阶段 2: 构建 Java 后端
FROM maven:3.9-eclipse-temurin-21 AS backend-builder

WORKDIR /app

# 复制 pom.xml 和 Java 源码
COPY pom.xml ./
COPY src/ ./src/

# 将前端构建产物复制到 resources/static，让 Spring Boot 服务
COPY --from=frontend-builder /app/dist ./src/main/resources/static

# 构建 Java 应用
RUN mvn clean package -DskipTests

# 阶段 3: 运行应用
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 复制构建好的 jar 包
COPY --from=backend-builder /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
