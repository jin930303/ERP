import api from './axios'

export interface OrderItemRequest {
  productId: number
  quantity: number
}

export interface OrderItemResponse {
  productId: number
  productname: string
  quantity: number
  unitPrice: number
  totalPrice: number
}

export interface OrderResponse {
  orderId: number
  status: string
  orderedAt: string
  items: OrderItemResponse[]
  totalPrice: number
}

export const createOrder = (data: OrderItemRequest) =>
  api.post<{ data: OrderResponse }>('/orders', data)
export const cancelOrder = (orderId: number) => api.put(`/orders/${orderId}/cancel`)
export const getOrders = () => api.get<{ data: OrderResponse[] }>('/orders')
