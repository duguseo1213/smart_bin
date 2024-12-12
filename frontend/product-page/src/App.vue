<template>
  <div class="container-fluid" style="padding: 0;">
    <main>
      <RouterView />
    </main>

    <footer class="row custom-footer" style="margin: 0;">
      <h3 class="fs-5 fw-bold">텅빈</h3>
      <p class="mb-0">ⓒ All rights reserved</p>
    </footer>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { onMounted, onUnmounted, ref } from 'vue'
const isScrolled = ref(false)
const isScrolledColor = ref('')

const handleScroll = () => {
  if (window.scrollY > 0) {
    isScrolled.value = true
    isScrolledColor.value = 'black'
  } else {
    isScrolled.value = false
    isScrolledColor.value = 'white'
  }
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style scoped>
* {
  white-space: nowrap;
  overflow: visible;
}

.container-custom {
  display: flex;
  align-items: center;
  justify-content: end;
}

.navbar-custom {
  width: 100%;
  position: fixed;
  top: 0;
  z-index: 1000;
  padding-top: 20px;
  border-bottom: 1px solid #818a92;
  font-weight: bold;
}

.navbar-scrolled {
  background-color: white;
}

.navbar-custom .navbar-brand img {
  width: 50px;
  height: 50px;
}

.navbar-custom .navbar-nav {
  display: flex;
  flex-direction: row;
}

.navbar-custom .navbar-nav .nav-link {
  color: #ffffff;
  margin-right: 50px;
  font-size: 27px;
}

.navbar-custom .navbar-nav .nav-link:hover {
  text-decoration: underline;
  text-underline-offset: 10px;
}

.nav-link-scrolled {
  color: black;
  margin-right: 50px;
  font-size: 27px;
}

footer {
  margin-top: 30px;
  margin-bottom: 0px;
  padding-top: 20px;
  background-color: #2E3B4E; 
  color: #CCCCCC; 
  height: 100px;
  width: 100%;
  border-top: 2px solid #2E3B4E; 
  text-align: center;
  display: flex;
  align-content: start;
}

.custom-footer h3,
.custom-footer p {
  color: #CCCCCC; 
}
</style>
