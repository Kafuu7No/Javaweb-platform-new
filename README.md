📌 qna-springboot

一个基于 Spring Boot + MyBatis + Thymeleaf + MySQL + JWT 的简易在线问答 / 留言讨论平台，实现了用户注册登录、验证码校验、发帖、回帖、登录鉴权等完整流程。

适合作为 Java Web / MVC / Spring Boot 课程作业或入门示例项目。

✨ 功能特性

✅ 用户注册 / 登录 / 退出登录

✅ 登录验证码（Session 保存，一次性校验）

✅ JWT 登录态（Cookie 存储，拦截器统一校验）

✅ 讨论区列表展示

✅ 发起新讨论

✅ 讨论详情页 + 回复功能

✅ 登录拦截（未登录自动跳转登录页）

✅ 前后端分离清晰（Controller / Service / Mapper / Entity）

🛠 技术栈
技术	说明
Spring Boot	Web 应用基础框架
Spring MVC	请求分发 / 控制层
MyBatis	ORM 持久层（注解方式）
MySQL	数据库
Thymeleaf	服务端模板引擎
JWT (jjwt)	用户登录态认证
Maven	项目构建与依赖管理
Java 17	运行环境
## 📂 项目结构
```
qna-springboot/
├── pom.xml
├── src/main/java/com/example/qna/
│ ├── QnaApplication.java
│ ├── config/
│ │ └── WebConfig.java
│ ├── controller/
│ │ ├── AuthController.java
│ │ ├── CaptchaController.java
│ │ └── DiscussionController.java
│ ├── entity/
│ │ ├── User.java
│ │ ├── Discussion.java
│ │ └── Reply.java
│ ├── interceptor/
│ │ └── AuthInterceptor.java
│ ├── mapper/
│ │ ├── UserMapper.java
│ │ ├── DiscussionMapper.java
│ │ └── ReplyMapper.java
│ ├── service/
│ │ ├── UserService.java
│ │ ├── DiscussionService.java
│ │ └── ReplyService.java
│ ├── service/impl/
│ │ ├── UserServiceImpl.java
│ │ ├── DiscussionServiceImpl.java
│ │ └── ReplyServiceImpl.java
│ └── util/
│ └── JwtUtil.java
└── src/main/resources/
├── templates/
├── static/css/style.css
└── application.yml
```

## 🗄 数据库设计（MySQL）

请先在 MySQL 中创建数据库并执行以下 SQL（数据库名需与 `application.yml` 一致）：

```sql
CREATE DATABASE IF NOT EXISTS db_homework07
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_homework07;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS discussions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  author VARCHAR(50) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS replies (
  id INT AUTO_INCREMENT PRIMARY KEY,
  discussion_id INT NOT NULL,
  content TEXT NOT NULL,
  author VARCHAR(50) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_reply_discussion
    FOREIGN KEY (discussion_id)
    REFERENCES discussions(id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```
