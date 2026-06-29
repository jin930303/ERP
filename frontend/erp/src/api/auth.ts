import api from './axios'

export interface LoginRequest {
  loginId: string
  password: string
}

export interface SignupRequest {
  loginId: string
  password: string
  name: string
  tenantId: number
}

export interface LoginResponse {
  accessToken: string
  memberId: number
  role: string
  tenantId: number
}

export const login = (data: LoginRequest) => api.post<{ data: LoginResponse }>('/auth/login', data)
export const signup = (data: SignupRequest) => api.post('/auth/signup', data)
