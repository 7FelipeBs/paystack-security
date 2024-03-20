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
    },

    {
      path: '/userconfig',
      name: 'userconfig',
      component: modules.default.UserConfig
    }
  ]
})

function validTokenLogin(response, cookies, next) {
  if (response.data) {
    let cookiesArray = response.data.split(';')
    let data = new Date(cookiesArray[1])
    let token = cookiesArray[0]

    cookies.set('refresh-token', token).set('refresh-token', token, data)
    userNavStore().setNavState(true)

    return true
  }

  cookies.remove('refresh-token')
  userNavStore().setNavState(false)
  next({ name: 'login' })
  return false
}

function validTokenIndex(response, cookies, next) {
  cookies.remove('refresh-token')
  userNavStore().setNavState(false)
  next({ name: 'index' })
}

router.beforeEach(async (to, from, next) => {
  const { cookies } = useCookies()

  let token = cookies.get('refresh-token')

  if (!token && to.name !== 'login') {
    if (from.path === '/' && to.path === '/') {
      return next({ name: 'login' })
    }
  }

  if (token && to.name !== 'login') {
    await useAuthStore().refresh(token, (response) => {
      return validTokenLogin(response, cookies, next)
    })
  }

  if (token && to.name === 'login') {
    await useAuthStore().refresh(token, (response) => {
      return validTokenIndex(response, cookies, next)
    })
  }

  next()
})

export default router
