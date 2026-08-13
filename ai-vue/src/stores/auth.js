import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

const readUserInfo = () => {
    const value = localStorage.getItem('userInfo')
    if (!value) {
        return null
    }
    try {
        return JSON.parse(value)
    } catch {
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        return null
    }
}

export const useAuthStore = defineStore('auth', () => {
    const token = ref(localStorage.getItem('token'))
    const userInfo = ref(readUserInfo())

    if (!userInfo.value) {
        token.value = null
        localStorage.removeItem('token')
    }

    const isLoggedIn = computed(() => Boolean(token.value && userInfo.value))
    const isAdmin = computed(() => userInfo.value?.userType === 2)

    const setSession = (session) => {
        token.value = session.token
        userInfo.value = session.userInfo
        localStorage.setItem('token', session.token)
        localStorage.setItem('userInfo', JSON.stringify(session.userInfo))
    }

    const clearSession = () => {
        token.value = null
        userInfo.value = null
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
    }

    return {
        token,
        userInfo,
        isLoggedIn,
        isAdmin,
        setSession,
        clearSession
    }
})
