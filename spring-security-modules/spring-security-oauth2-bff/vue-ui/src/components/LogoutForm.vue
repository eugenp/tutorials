<script setup lang="ts">
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();

async function logout(xsrfToken: string) {
    const response = await fetch('/bff/logout', {
      method: 'POST',
      headers: {
        'X-XSRF-TOKEN': xsrfToken,
        'X-POST-LOGOUT-SUCCESS-URI': `${import.meta.env.VITE_REVERSE_PROXY}${import.meta.env.BASE_URL}/`
      }
    })
    const location = response.headers.get('Location')
    if (location) {
      window.location.href = location
    }
  }

</script>

<template>
  <button @click="logout(cookies.get('XSRF-TOKEN'))">Logout</button>
</template>

<style scoped>
</style>
