import api from './axios'

export interface FinanceTransactionResponse {
  id: number
  orderId: number
  type: string
  amount: number
  issuedAt: string
}

export const getTransactions = () => api.get<{ data: FinanceTransactionResponse[] }>('/finance')
