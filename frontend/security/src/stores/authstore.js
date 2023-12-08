import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import uHttp from '../util/uHttp'
import uApi from '../util/uApi'

export const useAuthStore = defineStore('authStore', () => {
  const token = ref({})

  const getUser = computed(() => {
    return token.value
  })

  const signin = (data, callback, callbackError) =>
    uHttp.httpPost(
      uApi.SIGNIN,
      data,
      (response) => {
        token.value = response.data
        if (callback) callback(response)
      },
      (error) => {
        if (callbackError) callbackError(error)
      }
    )

  const signout = (data, callback, callbackError) =>
    uHttp.httpPost(
      uApi.SIGNOUT,
      data,
      (response) => {
        token.value = response.data
        if (callback) callback(response)
      },
      (error) => {
        if (callbackError) callbackError(error)
      }
    )

  const signup = (data, callback, callbackError) =>
    uHttp.httpPost(
      uApi.SIGNUP,
      data,
      (response) => {
        token.value = response.data
        if (callback) callback(response)
      },
      (error) => {
        if (callbackError) callbackError(error)
      }
    )

  const generateRefresh = (data, callback) =>
    uHttp.httpPostPromise(uApi.REFRESH_TOKEN, data, callback)

  const refresh = (data, callback) =>
    uHttp.httpPostPromise(uApi.REFRESH_TOKEN, data, (response) => {
      token.value = response.data
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
