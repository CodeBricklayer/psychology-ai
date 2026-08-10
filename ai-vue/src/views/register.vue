<template>
    <div class="container">
        <div class="title">
            <div class="title-text">
                <h2>创建您的账号</h2>
                <p>请填写注册信息</p>
            </div>
        </div>
        <div class="form-container">
            <el-form label-position="top" ref="submitFormRef" :model="formData" :rules="rules" label-width="120px">
                <el-form-item label="用户名或邮箱" prop="username">
                    <el-input v-model="formData.username" placeholder="请输入用户名或邮箱" size="large" />
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="formData.email" placeholder="请输入邮箱" size="large" />
                </el-form-item>
                <el-form-item label="昵称" prop="nickname">
                    <el-input v-model="formData.nickname" placeholder="请输入昵称（可选）" size="large" />
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="formData.phone" placeholder="请输入手机号" size="large" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="formData.password" type="password" placeholder="请输入密码" size="large"
                        show-password />
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input v-model="formData.confirmPassword" type="password" placeholder="请再次输入密码" size="large"
                        show-password />
                </el-form-item>
                <el-form-item>
                    <el-button class="btn" type="primary" @click="submitForm(submitFormRef)" size="large">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { register } from '@/api/frontend'
import { useRouter } from 'vue-router'

const router = useRouter()

const formData = reactive({
    username: '',
    email: '',
    nickname: '',
    phone: '',
    password: '',
    confirmPassword: '',
    gender: 0, // 性别（1 男 2 女）
    userType: 1 // 用户类型（1 普通用户 2 管理员）
})

const rules = reactive({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { max: 20, message: '用户名最多20个字符', trigger: 'blur' }
    ],
    phone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { type: 'number', trigger: 'blur' ,message: '请输入正确的手机号'},
        { min: 11, max: 11, message: '手机号长度必须为11位', trigger: 'blur' },
        {
            validator: (rule, value, callback) => {
                if (!/^1[3456789]\d{9}$/.test(value)) {
                    callback(new Error('请输入正确的手机号'))
                } else {
                    callback()
                }
            }, trigger: 'blur'
        }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 20, message: '密码长度必须在6到20个字符之间', trigger: 'blur' },
        {
            validator: (rule, value, callback) => {
                if (!/^[a-zA-Z0-9_]{6,20}$/.test(value)) {
                    callback(new Error('密码只能包含字母、数字和下划线'))
                } else {
                    callback()
                }
            }, trigger: 'blur'
        },
    ],
    confirmPassword: [
        { required: true, message: '请再次输入密码', trigger: 'blur' },
        {
            validator: (rule, value, callback) => {
                if (value !== formData.password) {
                    callback(new Error('两次输入密码不一致'))
                } else {
                    callback()
                }
            }, trigger: 'blur'
        }
    ],
    email: [
        { required: true, message: '请输入邮箱', trigger: 'blur' },
        { type: 'email', trigger: 'blur' ,message: '请输入正确的邮箱格式'},
        { max: 50, message: '邮箱最多50个字符', trigger: 'blur' }
        ,
        {
            validator: (rule, value, callback) => {
                if (!/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(value)) {
                    callback(new Error('请输入正确的邮箱格式'))
                } else {
                    callback()
                }
            }, trigger: 'blur'
        }
    ],
})

const submitFormRef = ref(null)

const submitForm = async (formEl) => {
    if (!formEl) {
        return
    }
    formEl.validate().then(() => {
        register(formData).then(({ data, code }) => {
            if (!data) {
                ElMessage.success('注册成功')
                // 注册成功后，跳转到登录页
                router.push('/auth/login')
            }
            if (code !== '401') {
                ElMessage.error(data.msg)
            }
        })
    })
}

</script>


<style scoped lang="scss">
.container {
    width: 384px;

    .flex-box {
        display: flex;
        align-items: center;
    }

    .title {
        .title-text {
            text-align: center;

            h2 {
                font-size: 36px;
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