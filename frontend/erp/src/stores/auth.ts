import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as loginApi, type LoginRequest } from '@/api/auth'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('accessToken'))
  const role = ref<string | null>(localStorage.getItem('role'))
  const tenantId = ref<number | null>(
    localStorage.getItem('tenantId') ? Number(localStorage.getItem('tenantId')) : null,
  )
  const isLoggedIn = computed(() => !!token.value)
  const isAdmin = computed(() => role.value === 'ADMIN')
  const isManager = computed(() => role.value === 'MANAGER' || isAdmin.value)

  async function login(credentials: LoginRequest) {
    const res = await loginApi(credentials)
    const data = res.data.data

    token.value = data.accessToken
    role.value = data.role
    tenantId.value = data.tenantId

    localStorage.setItem('accessToken', data.accessToken)
    localStorage.setItem('role', data.role)
    localStorage.setItem('tenantId', String(data.tenantId))
  }

  function logout() {
    token.value = null
    role.value = null
    tenantId.value = null
    localStorage.removeItem('accessToken')
    localStorage.removeItem('role')
    localStorage.removeItem('tenantId')
  }

  return { token, role, tenantId, isLoggedIn, isAdmin, isManager, login, logout }
})
