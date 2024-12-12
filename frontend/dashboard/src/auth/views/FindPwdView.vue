<script setup>
import { ref, watch } from "vue";
import AuthLayout from "@/auth/layouts/AuthLayout.vue";
import "@/auth/assets/css/account.css";
import { changePassword, sendAuthCode, verifyAuthCode } from "@/auth/js/auth";
import { toastEmailMissMatch, toastPasswordMissmatch } from "@/auth/js/toast";

const authStep = ref(1);
const email = ref("");
const authCode = ref("");
const password = ref("");
const passwordConfirm = ref("");
const authEmail = ref("");

watch(
  () => email.value,
  (email) => {
    if (email != authEmail.value || authEmail.value.length == 0) {
      authStep.value = 1;
    } else {
      authStep.value = 3;
    }
  }
);

async function clickSendAuthCode() {
  const success = await sendAuthCode(email.value);
  if (success) {
    authStep.value = 2;
  }
}

async function clickVerifyAuthCode() {
  const verifyEmail = email.value;
  const success = await verifyAuthCode(email.value, authCode.value);
  if (success) {
    authEmail.value = verifyEmail;
    authStep.value = 3;
  }
}

function clickChangePassword() {
  if(password.value.length < 8 && password.value.length > 16) {
    toastPasswordNotFit();
    return false;
  } else if (password.value != passwordConfirm.value) {
    toastPasswordMissmatch();
    return false;
  } else if (authEmail.value != email.value) {
    toastEmailMissMatch();
    return false;
  }

  changePassword(authEmail.value, password.value);
}
</script>

<template>
  <AuthLayout>
    <div class="account-header">
      <div>Sign Up to TeongBin Monitoring Platform</div>
    </div>
    <div class="account-main">
      <div class="input-item">
        <input
          type="text"
          inputmode="email"
          v-model="email"
          class="box input-field"
          placeholder=""
        />
        <label class="input-label">이메일 주소</label>
      </div>
      <div class="input-item">
        <div class="auth-item">
          <input
            type="text"
            inputmode="email"
            v-model="authCode"
            class="box input-field"
            placeholder=""
          />
          <label class="input-label">인증번호</label>
        </div>
        <div class="auth-item">
          <button
            type="button"
            @click="clickSendAuthCode"
            class="box submit-btn"
            v-if="authStep == 1"
          >
            인증번호 발송
          </button>
          <button type="button" class="box submit-btn" v-if="authStep == 2">
            <div v-if="authCode.length == 6" @click="clickVerifyAuthCode">
              인증 요청
            </div>
            <div v-if="authCode.length != 6" @click="clickSendAuthCode">
              재발송
            </div>
          </button>
          <button
            type="button"
            class="box submit-btn"
            :disabled="true"
            v-if="authStep == 3"
          >
            인증완료
          </button>
        </div>
      </div>

      <form>
        <div v-if="authStep == 3">
          <div class="input-item">
            <input
              type="password"
              inputmode="text"
              v-model="password"
              class="box input-field"
              placeholder=""
              autocomplete="off"
            />
            <label class="input-label">새로운 비밀번호 입력</label>
          </div>
          <div class="input-item">
            <input
              type="password"
              inputmode="text"
              v-model="passwordConfirm"
              class="box input-field"
              placeholder=""
              autocomplete="off"
              :class="{
                'input-error':
                  0 < passwordConfirm.length && password != passwordConfirm,
              }"
            />
            <label class="input-label">새로운 비밀번호 확인</label>
          </div>
          <button
            type="button"
            @click="clickChangePassword"
            class="box submit-btn"
          >
            비밀번호 변경
          </button>
        </div>
      </form>
    </div>
  </AuthLayout>
</template>

<style scoped>
.input-item {
  display: flex;
  justify-content: space-between;
}
.auth-item {
  width: 45%;
}
.input-error {
  color: red;
}
</style>
