<template>
  <div class="flex items-center w-full max-w-md px-6 mx-auto lg:w-2/6" v-if="isScreenSelected">
    <div class="flex-1">
      <div class="text-center">
        <h2 class="text-4xl font-bold text-center text-gray-700 dark:text-white">PayStack</h2>

        <p class="mt-3 text-gray-500 dark:text-gray-300">Welcome</p>
      </div>

      <div class="mt-6">
        <div>
          <label for="username" class="block mb-2 text-sm text-gray-600 dark:text-gray-200"
            >Username</label
          >
          <input
            type="username"
            name="username"
            id="username"
            placeholder="Username"
            hidden
            class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-md dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
            v-model="username"
          />
        </div>

        <div class="mt-6">
          <label for="username" class="block mb-2 text-sm text-gray-600 dark:text-gray-200"
            >Email</label
          >
          <input
            type="email"
            name="email"
            id="email"
            placeholder="Email@address.com"
            hidden
            class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-md dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
            v-model="email"
          />
        </div>

        <div class="mt-6">
          <label for="username" class="block mb-2 text-sm text-gray-600 dark:text-gray-200"
            >Password</label
          >
          <input
            type="password"
            name="password"
            id="password"
            placeholder="Password"
            v-model="password"
            hidden
            autocomplete="new-password"
            class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-md dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
          />
        </div>

        <div class="mt-6">
          <label for="username" class="block mb-2 text-sm text-gray-600 dark:text-gray-200"
            >Confirm Password</label
          >
          <input
            type="password"
            name="confirmPassword"
            id="confirmPassword"
            placeholder="Confirm Password"
            v-model="confirmPassword"
            hidden
            autocomplete="new-password"
            class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-md dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
          />
        </div>

        <div class="mt-6">
          <button
            @click="btnSignup()"
            class="w-full px-4 py-2 tracking-wide text-white transition-colors duration-200 transform bg-blue-500 rounded-md hover:bg-blue-400 focus:outline-none focus:bg-blue-400 focus:ring focus:ring-blue-300 focus:ring-opacity-50"
          >
            Sign up
          </button>
        </div>

        <span class="mt-6 text-sm text-center text-gray-400">
          You have account?
          <button
            class="text-blue-500 focus:outline-none focus:underline hover:underline"
            @click="btnDisableScreen"
          >
            Sign in
          </button>
          .
        </span>
      </div>
    </div>
  </div>
</template>

<script lang="js">
import { useAuthStore } from '../../../../stores/authstore'
// import { userNavStore } from '../../../../stores/navStore'

import { useCookies } from 'vue3-cookies'
import { ref } from 'vue'

export default {
  name: 'SignupLoginView',

  props: {
    modelValue: {
      typeof: Boolean,
      default: false
    }
  },

  setup() {
    const userAuth = useAuthStore()
    const username = ref('')
    const email = ref('')
    const password = ref('')
    const confirmPassword = ref('')
    const { cookies } = useCookies()

    const signupAuth = (callback, callbackError) => {
      userAuth.signup(
        { username: username.value, password: password.value, email: email.value },
        callback,
        callbackError
      )
    }
    return {
      username,
      password,
      email,
      confirmPassword,
      signupAuth,
      cookies
    }
  },

  methods: {
    btnDisableScreen() {
      this.isScreenSelected = false
    },

    btnSignup() {
      this.signupAuth(this.callback, this.callbackError)
    },

    callback(response) {
      if (response.status === 200) {
        // let cookiesArray = response.data.split(';')
        // let data = new Date(cookiesArray[1])
        // let token = cookiesArray[0]
        // this.cookies.set('refresh-token', token).set('refresh-token', token, data)
        // userNavStore().setNavState(true)
        // this.$router.push({ path: '/' })
      }
    },

    callbackError() {
      this.$router.push({ path: '/login' })
    }
  },

  computed: {
    isScreenSelected: {
      get() {
        return this.modelValue
      },
      set(value) {
        this.$emit('update:modelValue', value)
      }
    }
  }
}
</script>

<style lang=""></style>
