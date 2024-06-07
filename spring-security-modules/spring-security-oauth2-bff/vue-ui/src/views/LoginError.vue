<script setup lang="ts">
function message() {
  const searchStr = window.location.search
  const params = searchStr ? new URLSearchParams(searchStr.substring(1)) : new URLSearchParams();
  return params.get('error');
}
</script>

<template>
  <main>
    <h1></h1>
    <p>{{ message() }}</p>
  </main>
</template>
