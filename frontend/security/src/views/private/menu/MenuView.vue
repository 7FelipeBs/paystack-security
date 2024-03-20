<template>
  <div v-if="isValidNav" class="flex justify-between mb-20 shadow-xl w-full h-16 bg-white">
    <ul class="flex my-auto ml-6">
      <li class="my-auto mr-6">
        <button class="txt-chip-cover" @click="openHomePage()">
          <font-awesome-icon
            class="text-2xl hover:scale-125 transition duration-300"
            icon="fa-solid fa-house"
          />
        </button>
      </li>

      <li class="my-auto mr-6">
        <button class="txt-chip-cover text-1xl hover:scale-125 transition duration-300">
          <span>Pricing</span>
        </button>
      </li>

      <li class="my-auto mr-6">
        <button class="txt-chip-cover text-1xl hover:scale-125 transition duration-300">
          <span>About</span>
        </button>
      </li>

      <li class="my-auto mr-6">
        <button class="txt-chip-cover text-1xl hover:scale-125 transition duration-300">
          <span>Contact</span>
        </button>
      </li>
    </ul>

    <ul class="flex my-auto mr-6">
      <li class="mr-6">
        <button class="txt-chip-cover" @click="openUserConfigPage()">
          <font-awesome-icon
            class="text-2xl hover:scale-125 transition duration-300"
            icon="fa-solid fa-user-gear"
          />
        </button>
      </li>

      <li class="mr-4">
        <button class="txt-chip-cover" @click="signout(callback)">
          <font-awesome-icon
            class="text-2xl hover:scale-125 transition duration-300"
            icon="fa-solid fa-arrow-right-from-bracket"
          />
        </button>
      </li>
    </ul>
  </div>
</template>

<script lang="js">
import { userNavStore } from '../../../stores/navStore'
import { useAuthStore } from '../../../stores/authstore'
import { useCookies } from 'vue3-cookies'

export default {
  name: 'AppView',

  setup() {
    const { cookies } = useCookies()

    const signout = (callback) => {
      let refresh = cookies.get('refresh-token')
      useAuthStore().signout(refresh, callback)
    }

    return {
      signout,
      cookies
    }
  },

  methods: {
    callback(response) {
      if (response.status === 200) {
        userNavStore().setNavState(false)
        this.$router.push({ path: '/login' })
      }
    },

    openUserConfigPage() {
      this.$router.push({ path: '/userconfig' })
    },

    openHomePage() {
      this.$router.push({ path: '/' })
    }
  },

  computed: {
    isValidNav() {
      return userNavStore().getNavState
    }
  }
}
</script>
