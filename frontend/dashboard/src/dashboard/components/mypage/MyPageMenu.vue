<script setup></script>

<template>
  <div class="submenu-container">
    <div
      class="submenu-box"
      :class="$route.path == '/my/product' ? 'active-box' : 'nonactive'"
    >
      <router-link to="/my/product" class="submenu-item">나의 제품</router-link>
    </div>
    <div
      class="submenu-box"
      :class="$route.path == '/my/pwd' ? 'active-box' : 'nonactive'"
    >
      <router-link to="/my/pwd" class="submenu-item">개인정보 변경</router-link>
    </div>
    <div
      class="submenu-box"
      :class="$route.path == '/my/map' ? 'active-box' : 'nonactive'"
    >
      <router-link to="/my/map" class="submenu-item">
        기본위치 설정
      </router-link>
    </div>
  </div>
</template>

<style scoped>
.submenu-container {
  display: flex;
  padding: 1rem;
}
.submenu-box {
  padding: 0 1rem;
  margin: 0 0.5rem;
}
.nonactive:hover {
  background-color: rgba(247, 247, 247, 0.8);
  border-radius: 5px;
}
.active-box {
  background-color: rgba(247, 247, 247);
  border-radius: 5px;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s ease-in-out, transform 0.3s ease-in-out;
}
.submenu-item {
  text-decoration: none;
  color: black;
}
</style>
