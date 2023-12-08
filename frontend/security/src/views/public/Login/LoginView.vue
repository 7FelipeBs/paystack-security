<template>
  <div class="bg-white dark:bg-gray-900">
    <div class="flex justify-center h-screen">
      <div
        class="hidden bg-cover lg:block lg:w-1/3"
        style="
          background-image: url(https://cdn.pixabay.com/photo/2023/10/07/23/39/girl-8301168_1280.png);
        "
      >
        <div class="flex items-center h-full px-20 bg-gray-900 bg-opacity-50">
          <div>
            <h1 class="text-6xl font-bold text-white">PayStack</h1>

            <h3 class="max-w-xl mt-3 text-gray-200">
              Welcome to PayStack, the largest course platform in Brazil
            </h3>
          </div>
        </div>
      </div>
      <Signup v-model="isSignUpScreen" @update:modelValue="isSignUpScreen = $event" />
      <Signin v-model="isSignInScreen" @update:modelValue="isSignInScreen = $event" />
    </div>
  </div>
</template>

<script lang="js">
import Signup from './Signup/Signup.vue'
import Signin from './Signin/Signin.vue'
import { ref } from 'vue'

export default {
  name: 'LoginView',

  setup() {
    const isSignInScreen = ref(true)
    const isSignUpScreen = ref(false)
    return {
      isSignInScreen,
      isSignUpScreen
    }
  },

  methods: {},

  watch: {
    isSignInScreen: {
      handler(value) {
        if (!value) this.isSignUpScreen = true
      }
    },

    isSignUpScreen: {
      handler(value) {
        if (!value) this.isSignInScreen = true
      }
    }
  },
  components: { Signup, Signin }
}
</script>

<style lang=""></style>
