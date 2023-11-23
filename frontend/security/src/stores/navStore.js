import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const userNavStore = defineStore('navStore', () => {
  const isValidNav = ref(false)

  const setNavState = (data) => (isValidNav.value = data)

  const getNavState = computed(() => {
    return isValidNav.value
  })

  return {
    getNavState,
    setNavState
  }
})
