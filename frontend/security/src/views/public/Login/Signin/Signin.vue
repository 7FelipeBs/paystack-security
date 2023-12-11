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
          type="password"
          nameValue="password"
          idValue="password"
          refValue="password"
          label="Password"
          placeholder="Password"
          v-model="password"
          @update:modelValue="password = $event"
        />

        <a
          href="#"
          class="text-sm text-gray-400 focus:text-blue-500 hover:text-blue-500 hover:underline"
          >Forgot password?</a
        >
      </div>

      <!-- FEATURE COMING-->
      <!-- <div class="mt-12">
              <button
                class="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-2 mb-1 uppercase shadow hover:shadow-md inline-flex items-center text-xs ease-linear transition-all duration-150"
                type="button"
              >
                <img
                  alt="github"
                  class="w-5 mr-1"
                  src="https://demos.creative-tim.com/notus-js/assets/img/github.svg"
                />Github
              </button>
              <button
                class="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-1 mb-1 uppercase shadow hover:shadow-md inline-flex items-center text-xs ease-linear transition-all duration-150"
                type="button"
              >
                <img
                  alt="github"
                  class="w-5 mr-1"
                  src="https://demos.creative-tim.com/notus-js/assets/img/google.svg"
                />Google
              </button>
            </div> -->

      <div class="mt-6">
        <button
          @click="btnSignin()"
          class="w-full px-4 py-2 tracking-wide text-white transition-colors duration-200 transform bg-blue-500 rounded-md hover:bg-blue-400 focus:outline-none focus:bg-blue-400 focus:ring focus:ring-blue-300 focus:ring-opacity-50"
        >
          Sign in
        </button>
      </div>

      <span class="mt-6 text-sm text-center text-gray-400">
        Don&#x27;t have an account yet?
        <button
          class="text-blue-500 focus:outline-none focus:underline hover:underline"
          @click="btnDisableScreen"
        >
          Sign up
        </button>
      </span>
    </div>
  </div>
</template>

<script lang="js">
import { useAuthStore } from '../../../../stores/authstore'
import { userNavStore } from '../../../../stores/navStore'
import CInput from '../../../../components/cInput.vue'
import { useCookies } from 'vue3-cookies'

import { ref } from 'vue'

export default {
  name: 'SigninLoginView',

  props: {
    modelValue: {
      typeof: Boolean,
      default: false
    }
  },

  setup() {
    const userAuth = useAuthStore()
    const username = ref('')
    const password = ref('')
    const { cookies } = useCookies()

    const signinAuth = (callback, callbackError) => {
      userAuth.signin(
        { username: username.value, password: password.value },
        callback,
        callbackError
      )
    }
    return {
      username,
      password,
      signinAuth,
      cookies
    }
  },

  methods: {
    btnDisableScreen() {
      this.isScreenSelected = false
    },

    btnSignin() {
      this.signinAuth(this.callback, this.callbackError)
    },

    callback(response) {
      if (response.status === 200) {
        let cookiesArray = response.data.split(';')
        let data = new Date(cookiesArray[1])
        let token = cookiesArray[0]

        this.cookies.set('refresh-token', token).set('refresh-token', token, data)
        userNavStore().setNavState(true)

        this.$router.push({ path: '/' })
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
