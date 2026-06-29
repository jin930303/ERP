<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <h2 class="login-title">ERP System</h2>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
        <el-form-item label="아이디" prop="loginId">
          <el-input v-model="form.loginId" placeholder="아이디를 입력하세요" />
        </el-form-item>

        <el-form-item label="비밀번호" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="비밀번호를 입력하세요"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-alert
          v-if="errorMsg"
          :title="errorMsg"
          type="error"
          show-icon
          :closable="false"
          style="margin-bottom: 16px"
        />

        <el-button type="primary" class="login-btn" :loading="loading" @click="handleLogin">
          로그인
        </el-button>

        <div class="signup-link">
          계정이 없으신가요?
          <router-link to="/signup">회원가입</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()

const formRef = ref<FormInstance>()
const loading = ref(false)
const errorMsg = ref('')

const form = reactive({
  loginId: '',
  password: '',
})

const rules: FormRules = {
  loginId: [{ required: true, message: '아이디를 입력하세요', trigger: 'blur' }],
  password: [{ required: true, message: '비밀번호를 입력하세요', trigger: 'blur' }],
}

async function handleLogin() {
  if (!formRef.value) return
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  errorMsg.value = ''

  try {
    await authStore.login(form)
    router.push('/inventory')
  } catch (e: any) {
    errorMsg.value = e.response?.data?.message || '로그인에 실패했습니다.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f2f5;
}

.login-card {
  width: 400px;
}

.login-title {
  text-align: center;
  color: #409eff;
  margin: 0;
}

.login-btn {
  width: 100%;
  margin-top: 8px;
}

.signup-link {
  text-align: center;
  margin-top: 16px;
  font-size: 14px;
  color: #606266;
}
</style>
