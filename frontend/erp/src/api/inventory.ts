import api from './axios'

export interface StockResponse {
  productId: number
  productName: string
  price: number
  quantity: number
}

export interface RestockRequest {
  productId: number
  quantity: number
}

export const getStocks = () => api.get<{ data: StockResponse[] }>('/inventory')
export const restock = (data: RestockRequest) => api.post('/inventory/restock', data)
