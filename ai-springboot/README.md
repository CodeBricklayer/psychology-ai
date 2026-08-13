# 心理健康 AI 助手后端

## 项目简介

本项目是心理健康 AI 助手的后端服务，基于 Spring Boot 构建，提供用户认证、心理咨询会话、AI 流式对话、情绪日记和心理健康知识文章等功能。

## 技术栈

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Security
- MyBatis-Plus 3.5.17
- Spring AI 2.0.0
- MapStruct 1.6.3
- Hutool 5.8.46
- MySQL 8.0+
- Maven

## 运行环境

启动前请确认：

- JDK 21 或更高版本；
- Maven 已正确配置；
- MySQL 8.0+ 已启动；
- 已创建 `mental_health_assistant` 数据库；
- 已执行项目提供的数据库脚本；
- 已配置阿里云百炼兼容 OpenAI 接口的 API Key。

检查 Java 和 Maven 版本：

```bash
java -version
mvn -version
```

## 数据库配置

当前数据库配置位于：

```text
src/main/resources/application.yml
```

数据库连接信息通过环境变量提供，避免将生产凭据提交到仓库：

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Windows PowerShell 示例：

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/mental_health_assistant?serverTimezone=UTC"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "your-database-password"
```

数据库主要包含以下表：

- `user`：用户表；
- `consultation_session`：咨询会话表；
- `consultation_message`：咨询消息表；
- `emotion_diary`：情绪日记表；
- `knowledge_category`：知识文章分类表；
- `knowledge_article`：知识文章表；
- `sys_file_info`：文件信息表；
- `user_favorite`：用户收藏表；
- `ai_analysis_task`：AI 分析任务表。

## AI 配置

项目通过环境变量读取 API Key：

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      base-url: https://dashscope.aliyuncs.com/compatible-mode/v1
      chat:
        model: qwen3.6-plus
```

Windows PowerShell 示例：

```powershell
$env:OPENAI_API_KEY = "your-api-key"
```

也可以在 IDEA 的 Run/Debug Configuration 中配置环境变量：

```text
OPENAI_API_KEY=your-api-key
```

## 启动项目

在 `ai-springboot` 目录执行：

```bash
mvn clean compile
mvn spring-boot:run
```

也可以直接运行：

```text
com.tp.AiSpringbootApplication
```

项目默认端口：

```text
1236
```

项目上下文路径：

```text
/api
```

因此接口完整地址格式为：

```text
http://localhost:1236/api/{接口路径}
```

## 项目结构

```text
src/main/java/com/tp
├── common       通用结果、枚举和全局异常处理
├── config       Spring、JWT、AI 和安全配置
├── controller   接口控制器
├── converter    MapStruct 对象转换器
├── entity       实体、DTO 和响应 VO
├── exception    业务异常
├── mapper       MyBatis-Plus Mapper
├── service      服务接口
└── util         JWT、响应处理等工具类
```

SQL 查询统一按照以下方式组织：

```text
src/main/java/com/tp/mapper
src/main/resources/mapper
```

MyBatis-Plus XML 配置：

```yaml
mybatis-plus:
  mapper-locations: classpath:/mapper/*.xml
```

## 主要接口

### 用户接口

| 请求方式 | 接口 | 说明 |
| --- | --- | --- |
| POST | `/api/user/login` | 用户登录 |
| POST | `/api/user/add` | 用户注册 |
| GET | `/api/user/current` | 获取当前登录用户 |
| POST | `/api/user/logout` | 退出登录并使当前 Token 失效 |

### 咨询会话接口

| 请求方式 | 接口 | 说明 |
| --- | --- | --- |
| POST | `/api/psychological-chat/session/start` | 创建咨询会话 |
| POST | `/api/psychological-chat/stream` | AI 流式咨询 |
| GET | `/api/psychological-chat/sessions` | 分页查询咨询会话 |
| GET | `/api/psychological-chat/sessions/{sessionId}/messages` | 查询会话消息 |
| DELETE | `/api/psychological-chat/sessions/{sessionId}` | 删除咨询会话 |
| GET | `/api/psychological-chat/session/{sessionId}/emotion` | 查询会话情绪分析 |

### 情绪日记接口

| 请求方式 | 接口 | 说明 |
| --- | --- | --- |
| POST | `/api/emotion-diary` | 新增或更新情绪日记 |
| GET | `/api/emotion-diary/admin/page` | 分页查询情绪日记 |
| DELETE | `/api/emotion-diary/admin/{id}` | 删除情绪日记 |

### 知识文章接口

| 请求方式 | 接口 | 说明 |
| --- | --- | --- |
| GET | `/api/knowledge/category/tree` | 查询文章分类 |
| GET | `/api/knowledge/article/page` | 分页查询文章 |
| GET | `/api/knowledge/article/{id}` | 查询文章详情 |
| POST | `/api/knowledge/article` | 新增文章 |
| PUT | `/api/knowledge/article/{id}` | 修改文章 |
| PUT | `/api/knowledge/article/{id}/status` | 修改文章状态 |
| DELETE | `/api/knowledge/article/{id}` | 删除文章 |

## JWT 认证

除公开接口外，其他接口都需要携带 JWT Token。

当前公开接口：

- `/api/user/login`；
- `/api/user/add`。

请求头示例：

```text
Token: your-token
```

调用 `/api/user/logout` 后，当前 Token 会加入内存黑名单，并在原 Token 到期后自动清理。当前实现不跨服务实例共享，后端重启后黑名单会丢失；集群或生产环境建议改为 Redis 等共享存储。

JWT 配置位于 `application.yml`：

```yaml
jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000
  header: Authorization
  token-prefix: "Bearer "
```

JWT 密钥必须通过环境变量提供，且长度不能小于 32 个字符：

```powershell
$env:JWT_SECRET = "replace-with-a-random-secret-at-least-32-characters"
```

如果固定密钥曾经提交到公开仓库，应立即轮换，不能只删除配置文件中的明文。

## SSE 流式咨询

流式接口：

```text
POST /api/psychological-chat/stream
Content-Type: application/json
Accept: text/event-stream
```

请求体示例：

```json
{
  "sessionId": "session_1",
  "userMessage": "最近学习压力比较大"
}
```

正常内容事件：

```text
event: message
data: {"code":"200","msg":"操作成功","data":{"type":"normal","content":"..."}}
```

AI 回复结束后，服务端发送：

```text
event: done
data: {}
```

业务聊天记录保存到 `consultation_message` 表。Spring AI 当前使用内存窗口记忆，默认保留最近 30 条消息，应用重启后不会保留该内存上下文。

## 参数校验

新增和修改接口使用 Jakarta Validation：

- `@NotBlank`：字符串不能为空；
- `@NotNull`：对象不能为空；
- `@Size`：限制字符串长度；
- `@Min`、`@Max`：限制数值范围；
- `@Valid`：触发 DTO 参数校验。

参数校验异常由 `GlobalExceptionHandler` 统一处理，并返回 `Result` 格式：

```json
{
  "code": "400",
  "msg": "参数错误",
  "data": "具体错误信息"
}
```

## 编码和开发规范

- Java、YAML、XML 和 Markdown 文件统一使用 UTF-8 编码；
- Controller 只负责接收请求和返回结果；
- Service 负责业务逻辑；
- Mapper 继承 MyBatis-Plus 的 `BaseMapper`；
- SQL 统一放在 Mapper XML 中；
- DTO 负责请求参数和校验；
- VO 负责响应数据；
- Entity 负责数据库表映射；
- Converter 使用 MapStruct 完成 DTO、Entity 和 VO 转换；
- 新增类和方法按照现有项目 JavaDoc 风格编写注释。

## 编译验证

```bash
mvn -DskipTests compile
```

如果出现 Java 版本错误，请确认编译和运行使用的是同一个 JDK 版本。项目当前要求 Java 21。
