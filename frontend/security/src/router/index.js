import { createRouter, createWebHistory } from 'vue-router'
// import { useAuthStore } from '../stores/authStore'
import * as modules from './import'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: modules.default.LoginView
    },

    {
      path: '/',
      name: 'index',
      component: modules.default.HomeView
    }
  ]
})

// router.beforeEach(async (to, from, next) => {
//   let isAuthenticated = false
//   if (!isAuthenticated && to.name !== 'Login') {
//     return { name: 'login' }
//   } else next()
// })

export default router
