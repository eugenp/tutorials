<script setup lang="ts">
import type { UserService } from '@/user.service';
import { inject } from 'vue';
import LoginForm from './LoginForm.vue';
import LogoutForm from './LogoutForm.vue';

// inject the singleton defined in main.js
const user = inject('UserService') as UserService;

</script>

<template>
  <span>
    <LoginForm v-if="!user.current.value.isAuthenticated"></LoginForm>
    <LogoutForm v-if="user.current.value.isAuthenticated"></LogoutForm>
  </span>
</template>

<style scoped>
</style>
