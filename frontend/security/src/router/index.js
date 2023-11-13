import { createRouter, createWebHistory } from 'vue-router'
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
      name: 'home',
      component: modules.default.HomeView
    }
  ]
})

export default router
