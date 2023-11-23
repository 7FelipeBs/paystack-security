import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import uHttp from '../util/uHttp'
import uApi from '../util/uApi'

export const useAuthStore = defineStore('authStore', () => {
  const user = ref({})

  const getUser = computed(() => {
    return user.value
  })

  const signin = (data, callback) =>
    uHttp.httpPost(uApi.SIGNIN, data, (response) => {
      user.value = response.data
      if (callback) callback(response)
    })

  const signout = (callback) =>
    uHttp.httpPost(uApi.SIGNOUT, null, (response) => {
      user.value = response.data
      if (callback) callback(response)
    })

  const signup = (data, callback) =>
    uHttp.httpPost(uApi.SIGNUP, data, (response) => {
      user.value = response.data
      if (callback) callback(response)
    })

  const generateRefresh = (data) => uHttp.httpPost(uApi.GENERATE_TOKEN_REFRESH, data)

  const refresh = (data, callback) =>
    uHttp.httpPost(uApi.REFRESH_TOKEN, data, (response) => {
      user.value = response.data
      if (callback) callback(response)
    })

  return {
    signin,
    signout,
    signup,
    generateRefresh,
    refresh,
    getUser
  }
})
