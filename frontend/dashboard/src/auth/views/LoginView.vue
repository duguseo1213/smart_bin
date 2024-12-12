<script setup>
import { ref } from "vue";
import { cloneDeep } from "lodash";
import AuthLayout from "@/auth/layouts/AuthLayout.vue";
import "@/auth/assets/css/account.css";
import { login } from "@/auth/js/auth";

const loginData = ref({ email: "", password: "" });
const saveEmail = ref(false);

if (localStorage.getItem("teongbinEmail")) {
  saveEmail.value = true;
  loginData.value.email = localStorage.getItem("teongbinEmail");
}

function toggleSaveEmail() {
  saveEmail.value = !saveEmail.value;
}

function handleLogin() {
  const data = cloneDeep(loginData.value);
  login(data, saveEmail.value)
}
</script>

<template>
  <AuthLayout
    ><div class="account-header">
      <div>Login to TeongBin Monitoring Platform</div>
    </div>

    <div class="account-main">
      <div>
        <div class="input-item">
          <input
            type="text"
            inputmode="email"
            v-model="loginData.email"
            class="box input-field"
            placeholder=""
          />
          <label class="input-label">Email address</label>
        </div>
        <div class="input-item">
          <input
            type="password"
            inputmode="text"
            v-model="loginData.password"
            class="box input-field"
            placeholder=""
            autoComplete="off"
          />
          <label class="input-label">Password</label>
        </div>
        <button class="input-item save-email-btn" @click="toggleSaveEmail">
          <div>
            <i class="bi bi-check-circle" v-if="!saveEmail"></i>
            <i
              class="bi bi-check-circle-fill save-email-check"
              v-if="saveEmail"
            ></i>
          </div>

          <div class="save-email-label">이메일 저장</div>
        </button>
        <button type="button" @click="handleLogin" class="box login-btn">
          로그인
        </button>
      </div>
      <div class="link-container">
        <div></div>
        <router-link to="/user/find" class="link">Forgot password?</router-link>
      </div>
      <div class="link-container">
        <div class="notice">Don't have an account?</div>
        <router-link to="/user/join" class="link">Sign Up</router-link>
      </div>
    </div></AuthLayout
  >
</template>

<style scoped>
main {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}
section {
  width: 400px;
  padding: 40px;
  display: flex;
  justify-content: center;
}
.save-email-btn {
  display: flex;
  font-size: 1rem;
  background: none;
  border: none;
  width: fit-content;
}
.save-email-label {
  padding-left: 0.5rem;
}
.login-btn {
  width: 320px;
  background-color: rgb(20, 200, 20);
  border: none;
  font-weight: bold;
  color: white;
}
.link-container {
  display: flex;
  justify-content: space-between;
  padding: 1rem;
  padding-bottom: 0;
}
.notice {
  display: flex;
  align-items: center;

  font-size: 1rem;
}
.save-email-check {
  color: rgb(20, 200, 20);
}
.link {
  text-decoration: none;
  color: rgb(20, 200, 20);
  font-size: 1rem;
}
</style>
