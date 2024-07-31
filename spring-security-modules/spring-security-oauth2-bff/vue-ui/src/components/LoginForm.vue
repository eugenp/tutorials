<script setup lang="ts">
import type { UserService } from '@/user.service'
import { inject, onMounted, ref, type Ref } from 'vue'
import { useRoute } from 'vue-router'

enum LoginExperience {
  IFRAME,
  DEFAULT
}

interface LoginOptionDto {
  label: string
  loginUri: string
  isSameAuthority: boolean
}

// inject the singleton defined in main.js
const user = inject('UserService') as UserService
const loginUri: Ref<string> = ref('')
const loginExperiences: Ref<Array<LoginExperience>> = ref([LoginExperience.DEFAULT])
const selectedLoginExperience: Ref<string> = ref(LoginExperience[LoginExperience.DEFAULT])
const isLoginModalDisplayed: Ref<boolean> = ref(false)
const iframeSrc: Ref<string> = ref('')
const iframe: Ref<HTMLIFrameElement | undefined> = ref()
const route = useRoute()

onMounted(async () => {
  // Fetch login options from the BFF
  const response = await fetch('/bff/login-options')
  const opts = (await response.json()) as LoginOptionDto[]
  if (opts.length) {
    loginUri.value = opts[0].loginUri
    if (opts[0].isSameAuthority) {
      loginExperiences.value = [LoginExperience.IFRAME, LoginExperience.DEFAULT]
    }
    selectedLoginExperience.value = LoginExperience[loginExperiences.value[0]]
  }
})

function login() {
  if (!loginUri.value) {
    return
  }
  const url = new URL(loginUri.value)

  url.searchParams.append(
    'post_login_success_uri',
    `${import.meta.env.VITE_REVERSE_PROXY}${import.meta.env.BASE_URL}${route.path}`
  )
  url.searchParams.append(
    'post_login_failure_uri',
    `${import.meta.env.VITE_REVERSE_PROXY}${import.meta.env.BASE_URL}/login-error`
  )
  const loginUrl = url.toString()
  if (selectedLoginExperience.value === LoginExperience[LoginExperience.IFRAME]) {
    iframeSrc.value = loginUrl
    isLoginModalDisplayed.value = true

    if (iframe.value) {
      iframe.value.onload = () => {
        user.refresh()
      }
    }
  } else {
    window.location.href = loginUrl
  }
}

function onIframeLoad() {
  user.refresh()
}
</script>

<template>
  <span>
    <select v-if="loginExperiences.length > 1" v-model="selectedLoginExperience">
      <option v-for="exp in loginExperiences" :key="exp">{{ LoginExperience[exp] }}</option>
    </select>
    <button
      @click="login()"
      :disabled="!selectedLoginExperience || user.current.value.isAuthenticated"
    >
      Login
    </button>
  </span>
  <div
    class="modal-overlay"
    v-if="isLoginModalDisplayed && !user.current.value.isAuthenticated"
    @click="isLoginModalDisplayed = false"
  >
    <div class="modal">
      <iframe :src="iframeSrc" frameborder="0" :onload="onIframeLoad"></iframe>
      <button class="close-button" @click="isLoginModalDisplayed = false">Discard</button>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.modal {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  position: relative;
  width: 100%;
  max-width: 800px;
}

.modal iframe {
  width: 100%;
  height: 600px;
  border: none;
}
</style>
