<template>
  <div class="space-y-5" style="margin: 0px 5%">
    <div class="border-b border-b-gray-200">
      <ul class="-mb-px flex items-center justify-center text-center gap-4 text-lg font-medium">
        <li :class="getClassTabSelect('profile')" @click="openTab('profile')">
          <span>Profile</span>
        </li>
        <li :class="getClassTabSelect('security')" @click="openTab('security')">
          <span>Security</span>
        </li>
        <li :class="getClassTabSelect('notification')" @click="openTab('notification')">
          <span>Notification</span>
        </li>
      </ul>
    </div>

    <div>
      <div v-if="activeTab === 'profile'">
        <span>contexto 1</span>
      </div>

      <div v-if="activeTab === 'security'">
        <h3 class="text-xl font-bold txt-chip-cover">Two-factor authentication</h3>

        <span></span>
      </div>

      <div v-if="activeTab === 'notification'">
        <span>contexto 3</span>
      </div>
    </div>
  </div>
</template>

<script lang="js">
import { ref } from 'vue'
export default {
  name: 'AppView',

  setup() {
    const activeTab = ref('profile') // Use ref for reactive state management

    const openTab = (tabChoose) => {
      activeTab.value = tabChoose // Update the reactive state
    }

    const getClassTabSelect = (tabChoose) => {
      if (activeTab.value === tabChoose) return `flex-1 text-tab bg-tab-select `
      else return `flex-1 txt-shuttle-gray text-tab`
    }

    return {
      openTab,
      activeTab,
      getClassTabSelect
    }
  }
}
</script>
<style>
.text-tab {
  padding: 1.2em;
  cursor: pointer;
  transition: all 0.5s ease-out;
}

.text-tab:hover {
  background-color: #658cc2;
  color: white;
}

.bg-tab-select {
  background-color: #658cc2;
  color: white;
}
</style>
