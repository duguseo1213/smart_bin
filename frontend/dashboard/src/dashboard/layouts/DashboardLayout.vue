<script setup>
import Clock from "@/dashboard/components/Clock.vue";
import MainMenu from "@/dashboard/components/MainMenu.vue";
</script>

<template>
  <header>
    <img src="@/dashboard/assets/images/logo.png" class="logo" />
    <Clock />
  </header>

  <MainMenu />
  <main>
    <slot> </slot>
  </main>
</template>

<style scoped>
header {
  display: flex;
  align-items: center;
}
.logo {
  width: 10%;
  padding: 0 1rem;
  margin: 0 1rem;
}
main {
  float: left;
  margin: 1rem;
  border-radius: 5px;
  width: 80%;
}
</style>
