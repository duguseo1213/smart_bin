<script setup>
import { useAuthStore } from "@/auth/stores/auth";
import { useRouter } from "vue-router";

const router = useRouter();

function logout() {
  const authStore = useAuthStore();
  sessionStorage.removeItem("teongbinToken");
  authStore.loginState = false;

  router.push("/user/login");
}
</script>

<template>
  <aside>
    <div class="menu-container menu-split">
      <div>
        <div class="blank-space"></div>
        <div
          class="menu-box"
          :class="$route.path === '/' ? 'active-box' : 'nonactive'"
        >
          <RouterLink to="/" class="menu-item">Main</RouterLink>
        </div>
        <div
          class="menu-box"
          :class="$route.path.startsWith('/my') ? 'active-box' : 'nonactive'"
        >
          <RouterLink to="/my/product" class="menu-item">My Page</RouterLink>
        </div>
      </div>
      <div class="menu-box nonactive">
        <button class="logout-btn" @click="logout">Logout</button>
      </div>
    </div>
  </aside>
</template>

<style scoped>
aside {
  float: left;
  width: 140px;
  height: 585px;
  border-radius: 5px;
  margin: 1rem;
}
.blank-space {
  height: 5rem;
}
.menu-split {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.menu-container {
  padding: 1rem;
  height: 100%;
}
.menu-box {
  padding: 0.75rem 0;
  margin: 0.75rem 0;
}
.nonactive:hover {
  background-color: rgba(240, 240, 240, 0.5);
  border-radius: 5px;
}
.active-box {
  background-color: rgba(240, 240, 240);
  border-radius: 5px;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s ease-in-out, transform 0.3s ease-in-out;
}
.menu-item {
  text-decoration: none;
  color: black;
}
.logout-btn {
  background: none;
  border: none;
}
</style>
