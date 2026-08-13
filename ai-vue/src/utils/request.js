import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'
import { pinia } from '@/stores'

// 创建axios实例
const service = axios.create({
    baseURL: '/api', // 请求的前缀
    timeout: 15000, // 请求的超时时间
})

// 创建请求拦截器
service.interceptors.request.use(
    config => {
        // 在发送请求之前做些什么
        const token = useAuthStore(pinia).token
        if (token) {
            config.headers['token'] = token
        }
        return config
    },
    error => {
        // 对请求错误做些什么
        return Promise.reject(error)
    }
)

// 创建响应拦截器
service.interceptors.response.use(
    response => {
        // 对响应数据做点什么
        const { data, config } = response

        // 处理业务状态码
        if (data?.code === '200') {
            return data.data
        }

        const message = data?.msg || '请求失败'
        if (data?.code === '401' && !config.url.includes('/login')) {
            useAuthStore(pinia).clearSession()
            ElMessage.error(message)
            if (window.location.pathname !== '/auth/login') {
                window.location.assign('/auth/login')
            }
        } else {
            ElMessage.error(message)
        }
        return Promise.reject(new Error(message))
    },
    error => {
        const message = error.response?.data?.msg ||
            (error.code === 'ECONNABORTED' ? '请求超时，请稍后重试' : '网络连接失败')
        if (error.response?.status === 401) {
            useAuthStore(pinia).clearSession()
            if (window.location.pathname !== '/auth/login') {
                window.location.assign('/auth/login')
            }
        }
        ElMessage.error(message)
        return Promise.reject(new Error(message, { cause: error }))
    }
)

export default service
