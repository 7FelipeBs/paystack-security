import './assets/main.css'
import './assets/icons/fontAwesome'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import VueCookies from 'vue3-cookies'
import Notifications from 'notiwind'
///////// COOKIES /////////
import { globalCookiesConfig } from 'vue3-cookies'

globalCookiesConfig({
  expireTimes: '1d',
  path: '/',
  domain: 'localhost',
  secure: true,
  sameSite: 'Strict'
})

////////// ICONS //////////
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(VueCookies)
app.use(Notifications)

app.component('font-awesome-icon', FontAwesomeIcon)
app.mount('#app')
