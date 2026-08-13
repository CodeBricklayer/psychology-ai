# 心理健康 AI 助手

心理健康 AI 助手是一个前后端分离的心理健康服务项目，提供用户认证、AI 心理咨询、情绪日记、心理健康知识库和管理后台等功能。

## 项目结构

```text
psychology-ai/
├── ai-springboot/    # Spring Boot 后端项目
├── ai-vue/           # Vue 3 前端项目
├── README.md         # 项目总说明
└── .gitignore
```

## 子项目说明

详细的技术栈、目录结构、配置说明和接口文档请查看对应目录下的 README：

- [后端项目说明](ai-springboot/README.md)
- [前端项目说明](ai-vue/README.md)

## 主要功能

- 用户登录、注册和 Token 认证；
- AI 心理咨询和 SSE 流式回复；
- 咨询会话和历史消息管理；
- 情绪日记新增、修改和查询；
- 心理健康知识文章浏览和管理；
- 管理后台数据分析；
- 知识文章封面图片上传。

## 技术架构

```text
Vue 3 + Vite
        │
        │ HTTP / SSE
        ▼
Spring Boot + Spring Security + MyBatis-Plus + Spring AI
        │
        ▼
MySQL
```

## 运行环境

- JDK 21；
- Maven；
- Node.js 和 npm；
- MySQL 8.0 或更高版本；
- 可用的 AI 模型 API Key。

## 快速启动

### 1. 启动后端

进入后端目录：

```bash
cd ai-springboot
```

根据本地环境修改数据库配置，并配置 `OPENAI_API_KEY`，然后执行：

```bash
mvn clean compile
mvn spring-boot:run
```

后端默认地址：

```text
http://localhost:1236/api
```

### 2. 启动前端

进入前端目录：

```bash
cd ai-vue
npm install
npm run dev
```

前端默认地址：

```text
http://127.0.0.1:3000
```

前端 API 请求统一使用 `/api` 前缀，具体代理地址配置在：

```text
ai-vue/vite.config.js
```

如果本地运行后端，需要将 Vite 代理目标修改为本地后端地址：

```js
proxy: {
  '/api': {
    target: 'http://localhost:1236',
    changeOrigin: true
  }
}
```

## 前后端联调

前端通过 Axios 请求普通接口，通过 `fetch-event-source` 请求 AI 流式接口。

普通接口示例：

```text
前端请求：/api/user/login
后端接口：/api/user/login
```

AI 流式接口：

```text
POST /api/psychological-chat/stream
Accept: text/event-stream
```

后端返回的 SSE 事件包括：

- `message`：AI 回复片段；
- `done`：本次 AI 回复结束。

## 编码规范

- Java、Vue、YAML、XML 和 Markdown 文件统一使用 UTF-8 编码；
- 后端按照 Controller、Service、Mapper、Entity、DTO、VO、Converter 分层；
- 后端数据库操作使用 MyBatis-Plus；
- 复杂 SQL 放在 Mapper XML 文件中；
- 前后端接口路径保持 `/api` 前缀约定；
- 新增代码的注释和命名遵循对应子项目已有代码风格。

## 验证命令

后端编译：

```bash
cd ai-springboot
mvn -DskipTests compile
```

前端构建：

```bash
cd ai-vue
npm run build
```

## 注意事项

- 编译和启动后端时必须使用 Java 21，避免出现 class file version 不匹配；
- 启动后端前需要确认 MySQL 数据库和表结构已经准备完成；
- 不要将真实 API Key 提交到 Git；
- 修改后端端口或上下文路径时，需要同步检查前端代理配置；
- 前后端的详细配置和接口说明请分别查看子目录 README。
