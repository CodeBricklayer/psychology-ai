<template>
  <div class="container">
    <div class="title">
      <div class="back-home">
        <el-icon>
          <Back />
        </el-icon>
        <span>
          返回首页
        </span>
      </div>
      <div class="title-text">
        <h2>登录您的账户</h2>
        <p>请输入您的登录信息</p>
      </div>
    </div>

    <div class="form-container">
      <el-form ref="ruleFormRef" :model="formData" :rules="rules" label-position="top">
        <el-form-item label="用户名或邮箱" prop="username">
          <el-input v-model="formData.username" size="large" placeholder="请输入用户名或邮箱" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password" size="large" placeholder="请输入密码" type="password" show-password />
        </el-form-item>
        <el-button class="btn" type="primary" size="large" :loading="loading"
          @click="submitForm(ruleFormRef)">登录</el-button>
      </el-form>
      <div class="footer">
        <p>
          还没有账户？
          <router-link to="./register">去注册</router-link>
        </p>

      </div>
    </div>
  </div>
</template>
<script setup>
import { reactive, ref } from 'vue'
import { login } from '@/api/admin'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const ruleFormRef = ref()
const loading = ref(false)
const authStore = useAuthStore()

const formData = reactive({
  username: '',
  password: ''
})
const rules = reactive({
  username: [{ required: true, message: '请输入用户名或邮箱', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

// 登录
const router = useRouter()
const submitForm = async (formEl) => {
  if (!formEl) return
  const valid = await formEl.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    const data = await login(formData)
    if (!data?.token || !data?.userInfo) {
      ElMessage.error('登录响应数据不完整')
      return
    }
    authStore.setSession(data)
    ElMessage.success('登录成功')
    const redirect = router.currentRoute.value.query.redirect
    await router.push(redirect || (authStore.isAdmin ? '/back/dashboard' : '/'))
  } catch {
    // 请求拦截器已统一提示错误
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.container {
  width: 384px;

  .title {
    .back-home {
      margin-bottom: 60px;
    }

    .title-text {
      text-align: center;

      h2 {
        font-size: 30px;
        margin-bottom: 10px;
      }

      p {
        font-size: 18px;
        color: #6b7280;
      }
    }
  }

  .form-container {
    margin-top: 30px;

    .btn {
      margin-top: 40px;
      width: 100%;
    }

    .footer {
      padding: 30px;
      text-align: center;
    }
  }

}
</style>
