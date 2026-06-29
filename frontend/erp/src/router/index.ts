import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: () => import('@/views/auth/LoginView.vue') },
    { path: '/signup', component: () => import('@/views/auth/SignupView.vue') },
    {
      path: '/inventory',
      component: () => import('@/views/inventory/InventoryView.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/orders',
      component: () => import('@/views/order/OrderView.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/finance',
      component: () => import('@/views/finance/FinanceView.vue'),
      meta: { requiresAuth: true, requiresManager: true },
    },
  ],
})

router.beforeEach((to) => {
  const auth = useAuthStore()

  if (to.meta.requiresAuth && !auth.isLoggedIn) {
    return '/login'
  }

  if (to.meta.requiresManager && !auth.isManager) {
    return '/login'
  }
})

export default router
