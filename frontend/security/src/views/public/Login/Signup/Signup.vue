<template>
  <div class="flex items-center w-full max-w-md px-6 mx-auto lg:w-2/6" v-if="isScreenSelected">
    <div class="flex-1">
      <div class="text-center">
        <h2 class="text-4xl font-bold text-center text-gray-700 dark:text-white">PayStack</h2>

        <p class="mt-3 text-gray-500 dark:text-gray-300">Welcome</p>
      </div>

      <div class="mt-6">
        <c-input
          nameValue="username"
          idValue="username"
          refValue="username"
          label="Username"
          placeholder="Username"
          v-model="username"
          @update:modelValue="username = $event"
        />
      </div>

      <div class="mt-6">
        <c-input
          nameValue="email"
          idValue="email"
          refValue="email"
          label="Email"
          placeholder="Email"
          type="email"
          v-model="email"
          @update:modelValue="email = $event"
        />
      </div>

      <div class="mt-6">
        <c-input
          nameValue="password"
          idValue="password"
          refValue="password"
          label="Password"
          placeholder="Password"
          type="password"
          v-model="password"
          @update:modelValue="password = $event"
        />
      </div>

      <div class="mt-6">
        <c-input
          nameValue="confirmPassword"
          idValue="confirmPassword"
          refValue="confirmPassword"
          label="Confirm Password"
          placeholder="Confirm Password"
          type="password"
          v-model="confirmPassword"
          @update:modelValue="confirmPassword = $event"
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
      </span>
    </div>
  </div>
</template>

<script lang="js">
import { useAuthStore } from '../../../../stores/authstore'
import CInput from '../../../../components/cInput.vue'

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
      if (response.status === 200 || response.status) {
        this.isScreenSelected = false
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
  },

  components: { CInput }
}
</script>

<style lang=""></style>
