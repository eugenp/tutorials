<script setup lang="ts">
import { inject } from 'vue';
import { User, UserService } from '../user.service'

// inject the singleton defined in main.js
const user = inject('UserService') as UserService;
function message(user: User) {
  return user.isAuthenticated 
  ? `Hi ${user.name}, you are granted with ${rolesStr(user)}.`
  : 'You are not authenticated.'
}

function rolesStr(user: User) {
  if(!user?.roles?.length) {
    return '[]'
  }
  return `["${user.roles.join('", "')}"]`
}
</script>

<template>
  <main>
    <p>{{ message(user.current.value) }}</p>
  </main>
</template>
