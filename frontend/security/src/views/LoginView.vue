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

      <div class="flex items-center w-full max-w-md px-6 mx-auto lg:w-2/6">
        <div class="flex-1">
          <div class="text-center">
            <h2 class="text-4xl font-bold text-center text-gray-700 dark:text-white">PayStack</h2>

            <p class="mt-3 text-gray-500 dark:text-gray-300">Welcome</p>
          </div>

          <div class="mt-8">
            <div>
              <div>
                <label for="user" class="block mb-2 text-sm text-gray-600 dark:text-gray-200"
                  >Username</label
                >
                <input
                  type="user"
                  name="user"
                  id="user"
                  placeholder="Username"
                  class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-md dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                />
              </div>

              <div class="mt-6">
                <div class="flex justify-between mb-2">
                  <label for="password" class="text-sm text-gray-600 dark:text-gray-200"
                    >Password</label
                  >
                  <a
                    href="#"
                    class="text-sm text-gray-400 focus:text-blue-500 hover:text-blue-500 hover:underline"
                    >Forgot password?</a
                  >
                </div>

                <input
                  type="password"
                  name="password"
                  id="password"
                  placeholder="Password"
                  class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-md dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                />
              </div>

              <div class="mt-12">
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
              </div>

              <div class="mt-6">
                <button
                  class="w-full px-4 py-2 tracking-wide text-white transition-colors duration-200 transform bg-blue-500 rounded-md hover:bg-blue-400 focus:outline-none focus:bg-blue-400 focus:ring focus:ring-blue-300 focus:ring-opacity-50"
                  @click="teste()"
                >
                  Sign in
                </button>
              </div>
            </div>

            <p class="mt-6 text-sm text-center text-gray-400">
              Don&#x27;t have an account yet?
              <a href="#" class="text-blue-500 focus:outline-none focus:underline hover:underline"
                >Sign up</a
              >.
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import uHttp from '../util/uHttp'
export default {
  name: 'LoginView',

  setup() {
    return {}
  },

  methods: {
    teste() {
      uHttp.httpGet('teste', 'datas')
    }
  }
}
</script>

<style lang=""></style>
