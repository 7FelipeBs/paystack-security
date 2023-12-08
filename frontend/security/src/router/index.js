import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import { userNavStore } from '../stores/navStore'
// import uHttp from '../util/uHttp'
// import uApi from '../util/uApi'
import { useCookies } from 'vue3-cookies'

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

router.beforeEach(async (to, from, next) => {
  const { cookies } = useCookies()

  let token = cookies.get('refresh-token')

  if (!token && to.name !== 'login') {
    next({ name: 'login' })
  } else if (token && to.name !== 'login') {
    await useAuthStore().refresh(token, (response) => {
      console.log('response1:', response)

      if (response.data) {
        let cookiesArray = response.data.split(';')
        let data = new Date(cookiesArray[1])
        let token = cookiesArray[0]
        cookies.set('refresh-token', token).set('refresh-token', token, data)
        userNavStore().setNavState(true)
      } else {
        cookies.remove('refresh-token')
        userNavStore().setNavState(false)
        next({ name: 'login' })
      }
    })
  } else if (token && to.name === 'login') {
    await useAuthStore().refresh(token, (response) => {
      console.log('response2:', response)

      if (response.data) {
        let cookiesArray = response.data.split(';')
        let data = new Date(cookiesArray[1])
        let token = cookiesArray[0]
        cookies.set('refresh-token', token).set('refresh-token', token, data)
        userNavStore().setNavState(true)
        next({ name: 'index' })
      } else {
        cookies.remove('refresh-token')
        userNavStore().setNavState(false)
        next({ name: 'index' })
      }
    })
  }

  next()
})

export default router
