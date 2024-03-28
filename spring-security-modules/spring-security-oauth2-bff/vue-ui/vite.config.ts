import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  base: '/vue-ui',
  server: {
    host: "0.0.0.0",
    port: 4202,
    //https: {
    //  cert: 'C:/Users/ch4mp/.ssh/bravo-ch4mp_self_signed.pem',
    //  key: 'C:/Users/ch4mp/.ssh/bravo-ch4mp_req_key.pem',
    //},
    //open: 'https://localhost:7080/vue-ui',
  },
})
