<template>
  <div class="space-y-5">
    <div class="border-b border-b-gray-200">
      <ul class="-mb-px flex items-center justify-center text-center gap-4 text-md font-medium">
        <li class="flex-1 txt-cobalt text-tab" @click="openTab('profile')">
          <span>Profile</span>
        </li>
        <li class="flex-1 txt-cobalt text-tab" @click="openTab('security')">
          <span>Security</span>
        </li>
        <li class="flex-1 txt-cobalt text-tab" @click="openTab('notification')">
          <span>Notification</span>
        </li>
      </ul>
    </div>

    <div>
      <div v-if="profileOpen">
        <span>contexto 1</span>
      </div>

      <div v-if="securityOpen">
        <span>contexto 2</span>
      </div>

      <div v-if="notificationOpen">
        <span>contexto 3</span>
      </div>
    </div>
  </div>
</template>

<script lang="js">
export default {
  name: 'AppView',

  setup() {
    return {
      arrayTabChoose: {
        profile: 'profile',
        security: 'security',
        notification: 'notification'
      }
    }
  },

  methods: {
    openTab(tabChoose) {
      this.profileTab = this.arrayTabChoose[tabChoose] === 'profile'
      this.securityTab = this.arrayTabChoose[tabChoose] === 'security'
      this.notificationTab = this.arrayTabChoose[tabChoose] === 'notification'
    }
  },

  computed: {
    profileTab: {
      get() {
        return this.profileTab
      },
      set(value) {
        this.$emit('update:modelValue', value)
      }
    },

    securityTab: {
      get() {
        return this.securityTab
      },
      set(value) {
        this.$emit('update:modelValue', value)
      }
    },

    notificationTab: {
      get() {
        return this.notificationTab
      },
      set(value) {
        this.$emit('update:modelValue', value)
      }
    }
  }
}
</script>
<style>
.text-tab {
  padding: 1.2em;
  cursor: pointer;
}

.text-tab:hover {
  background-color: rgb(0, 255, 255);
}
</style>
