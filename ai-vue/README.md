# 心理健康AI助手 - 前端

基于 Vue 3 + Vite 构建的心理健康AI助手前端应用，提供 AI 心理咨询、情绪日志记录、心理健康知识库等功能，同时包含管理后台用于数据分析和内容管理。

## ✨ 功能特性

### 用户端

- 🏠 **首页** - 温暖的欢迎页面，引导用户使用各项功能
- 🤖 **AI 咨询** - 与"宁渡AI助手"进行实时对话，支持流式响应（SSE），自动进行情绪分析，提供情绪花园可视化、温暖建议和治愈小行动
- 📔 **情绪日志** - 记录每日情绪评分（1-10分）、主要情绪类型、情绪触发因素、今日感想，以及睡眠质量和压力水平等生活指标
- 📚 **知识库** - 浏览心理健康文章，支持分类筛选和推荐阅读

### 管理后台

- 📊 **数据分析** - 综合数据概览（用户数、情绪日志数、咨询会话数、平均情绪评分），情绪趋势分析图表，会话统计分析
- 📝 **知识文章管理** - 文章的增删改查、分类管理、封面上传、状态控制
- 💬 **咨询记录** - 查看用户咨询会话列表及详细消息记录
- 😊 **情绪日志管理** - 查看和管理用户提交的情绪日记

### 系统功能

- 🔐 **用户认证** - 登录 / 注册，基于 Token 的身份验证
- 🛡️ **权限控制** - 路由守卫区分普通用户（userType=1）和管理员（userType=2）

## 🛠️ 技术栈

| 技术 | 说明 |
| --- | --- |
| [Vue 3](https://vuejs.org/) | 渐进式 JavaScript 框架 |
| [Vite](https://vite.dev/) | 下一代前端构建工具 |
| [Vue Router](https://router.vuejs.org/) | 官方路由管理 |
| [Pinia](https://pinia.vuejs.org/) | 新一代状态管理 |
| [Element Plus](https://element-plus.org/) | Vue 3 UI 组件库 |
| [Axios](https://axios-http.com/) | HTTP 请求库 |
| [ECharts](https://echarts.apache.org/) | 数据可视化图表 |
| [WangEditor](https://www.wangeditor.com/) | 富文本编辑器 |
| [Sass](https://sass-lang.com/) | CSS 预处理器 |
| [@microsoft/fetch-event-source](https://github.com/Azure/fetch-event-source) | SSE 流式请求（AI 对话） |

## 📁 项目结构

```
ai-vue/
├── public/                  # 静态资源
├── src/
│   ├── api/                 # API 接口
│   │   ├── admin.js         # 管理端接口
│   │   └── frontend.js      # 用户端接口
│   ├── assets/              # 项目资源
│   │   └── images/          # 图片资源
│   ├── components/          # 公共组件
│   │   ├── ArticleDialog.vue
│   │   ├── AuthLayout.vue
│   │   ├── BackendLayout.vue
│   │   ├── FrontendLayout.vue
│   │   ├── MarkdownRenderer.vue
│   │   ├── Navbar.vue
│   │   ├── PageHead.vue
│   │   ├── RichTextEditor.vue
│   │   ├── Sidebar.vue
│   │   └── TableSearch.vue
│   ├── config/              # 项目配置
│   │   └── index.js
│   ├── router/              # 路由配置
│   │   └── index.js
│   ├── stores/              # Pinia 状态管理
│   │   └── admin.js
│   ├── utils/               # 工具函数
│   │   └── request.js       # Axios 封装（拦截器、Token 注入）
│   ├── views/               # 页面视图
│   │   ├── home.vue         # 首页
│   │   ├── consultation.vue # AI 咨询
│   │   ├── emotionDiary.vue # 情绪日志
│   │   ├── frontendKnowledge.vue # 知识库
│   │   ├── articleDetail.vue # 文章详情
│   │   ├── dashboard.vue    # 数据分析
│   │   ├── knowledge.vue    # 文章管理
│   │   ├── consultations.vue # 咨询记录
│   │   ├── emotional.vue    # 情绪日志管理
│   │   ├── login.vue        # 登录
│   │   └── register.vue     # 注册
│   ├── App.vue
│   ├── main.js
│   └── style.css
├── index.html
├── vite.config.js
└── package.json
```

## 🚀 快速开始

### 环境要求

- Node.js >= 16
- npm >= 7

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm run dev
```

应用将在 `http://127.0.0.1:3000` 启动。

### 构建生产版本

```bash
npm run build
```

### 预览生产构建

```bash
npm run preview
```

## ⚙️ 配置说明

项目通过 Vite 环境变量配置接口代理和文件访问地址。复制 `.env.example` 为 `.env.local`，再按实际环境修改：

```dotenv
VITE_API_PROXY_TARGET=http://127.0.0.1:1236
VITE_FILE_BASE_URL=http://127.0.0.1:1236
```

- `VITE_API_PROXY_TARGET`：开发环境 `/api` 代理目标；
- `VITE_FILE_BASE_URL`：图片等文件资源的服务地址。

> 生产环境部署时，需通过 Nginx 等反向代理工具配置 `/api` 路径转发至后端服务。

## 🔗 相关项目

- [ai-springboot](../ai-springboot) - 后端服务，基于 Spring Boot 构建

## 📄 License

MIT
