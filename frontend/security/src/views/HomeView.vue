<template>
  <div class="flex justify-center h-screen">
    <header class="bg-white">
      <!-- buttons -->
      <nav class="contents">
        <ul class="ml-4 xl:w-48 flex items-center justify-end">
          <li class="ml-2 lg:ml-4 relative inline-block">
            <button @click="carregarLogin()">
              <svg
                class="h-9 lg:h-10 p-2 text-gray-500 svg-inline--fa fa-user fa-w-14 fa-9x"
                aria-hidden="true"
                focusable="false"
                data-prefix="far"
                data-icon="user"
                role="img"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 448 512"
              >
                <path
                  fill="currentColor"
                  d="M313.6 304c-28.7 0-42.5 16-89.6 16-47.1 0-60.8-16-89.6-16C60.2 304 0 364.2 0 438.4V464c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48v-25.6c0-74.2-60.2-134.4-134.4-134.4zM400 464H48v-25.6c0-47.6 38.8-86.4 86.4-86.4 14.6 0 38.3 16 89.6 16 51.7 0 74.9-16 89.6-16 47.6 0 86.4 38.8 86.4 86.4V464zM224 288c79.5 0 144-64.5 144-144S303.5 0 224 0 80 64.5 80 144s64.5 144 144 144zm0-240c52.9 0 96 43.1 96 96s-43.1 96-96 96-96-43.1-96-96 43.1-96 96-96z"
                ></path>
              </svg>
            </button>
          </li>
        </ul>
      </nav>
    </header>
  </div>
</template>

<script>
import uHttp from '../util/uHttp'
import uApi from '../util/uApi'

export default {
  name: 'HomeView',

  setup() {
    return {}
  },

  mounted() {
    setTimeout(() => {
      console.log(this.$cookies.get('security-jwt'))
    }, 1000)
  },

  methods: {
    carregarLogin() {
      uHttp.httpPost(uApi.SIGNOUT, null, (resposta) => {
        console.log('signout: ', resposta)
        this.$router.push({ path: '/login' })
      })
    }
  }
}
</script>

<style lang=""></style>
