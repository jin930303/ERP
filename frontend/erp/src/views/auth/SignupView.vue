<template>
  <div class="signup-container">
    <el-card class="signup-card">
      <template #header>
        <h2 class="signup-title">회원가입</h2>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
        <el-form-item label="아이디" prop="loginId">
          <el-input v-model="form.loginId" placeholder="아이디를 입력하세요" />
        </el-form-item>

        <el-form-item label="비밀번호" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="8자 이상 입력하세요"
            show-password
          />
        </el-form-item>

        <el-form-item label="이름" prop="name">
          <el-input v-model="form.name" placeholder="이름을 입력하세요" />
        </el-form-item>

        <el-form-item label="테넌트 ID" prop="tenantId">
          <el-input-number v-model="form.tenantId" :min="1" style="width: 100%" />
        </el-form-item>

        <el-alert
          v-if="errorMsg"
          :title="errorMsg"
          type="error"
          show-icon
          :closable="false"
          style="margin-bottom: 16px"
        />

        <el-button type="primary" class="signup-btn" :loading="loading" @click="handleSignup">
          회원가입
        </el-button>

        <div class="login-link">
          이미 계정이 있으신가요?
          <router-link to="/login">로그인</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { signup } from '@/api/auth'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)
const errorMsg = ref('')

const form = reactive({
  loginId: '',
  password: '',
  name: '',
  tenantId: 1,
})

const rules: FormRules = {
  loginId: [{ required: true, message: '아이디를 입력하세요', trigger: 'blur' }],
  password: [
    { required: true, message: '비밀번호를 입력하세요', trigger: 'blur' },
    { min: 8, message: '8자 이상 입력하세요', trigger: 'blur' },
  ],
  name: [{ required: true, message: '이름을 입력하세요', trigger: 'blur' }],
  tenantId: [{ required: true, message: '테넌트 ID를 입력하세요', trigger: 'blur' }],
}

async function handleSignup() {
  if (!formRef.value) return
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  errorMsg.value = ''

  try {
    await signup(form)
    ElMessage.success('회원가입이 완료되었습니다.')
    router.push('/login')
  } catch (e: any) {
    errorMsg.value = e.response?.data?.message || '회원가입에 실패했습니다.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.signup-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f2f5;
}

.signup-card {
  width: 400px;
}

.signup-title {
  text-align: center;
  color: #409eff;
  margin: 0;
}

.signup-btn {
  width: 100%;
  margin-top: 8px;
}

.login-link {
  text-align: center;
  margin-top: 16px;
  font-size: 14px;
  color: #606266;
}
</style>
