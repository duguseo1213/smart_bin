<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <div class="app-box">
    <RouterView />
  </div>
</template>

<style scoped>
.app-box {
  font-size: 1.5rem;
  padding: 1rem 2.5rem;
  vertical-align: middle;
  text-align: center;
  min-width: 1400px;
  height: 100%;
  background-color: rgb(254, 254, 254);
}
</style>
