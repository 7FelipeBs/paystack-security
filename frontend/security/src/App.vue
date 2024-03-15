<template>
  <div>
    <nav class="w-full mb-12">
      <menu-view />
    </nav>

    <main class="main-app w-full mt-20">
      <router-view />
    </main>
  </div>
</template>

<script lang="js">
import MenuView from './views/private/menu/MenuView.vue'

export default {
  name: 'AppView',

  components: { MenuView }
}
</script>

<style scoped>
nav {
  position: sticky;
  top: 0;
  z-index: 999;
}

@media (max-width: 768px) {
  nav {
    position: fixed;
    top: 0;
  }
}
</style>
