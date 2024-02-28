import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { UserService } from './user.service'

const app = createApp(App)
app.provide("UserService", new UserService());

app.use(router)

app.mount('#app')
