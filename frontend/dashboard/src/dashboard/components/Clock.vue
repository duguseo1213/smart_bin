<script setup>
import { ref } from "vue";

const time = ref();

function updateTime() {
  time.value = new Date().toLocaleTimeString("en-GB", { hour12: false });
}

time.value = new Date().toLocaleTimeString("en-GB", { hour12: false });
setInterval(updateTime, 1000);
</script>

<template>
  <div class="clock">{{ time }}</div>
</template>

<style scoped>
.clock {
  font-size: 4rem;
  letter-spacing: 0.2rem;
  text-align: center;
  vertical-align: middle;
  width: 70%;
  padding: 0 1rem;
  margin: 0 1rem;
}
</style>
