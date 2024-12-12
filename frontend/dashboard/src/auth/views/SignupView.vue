<script setup>
import AuthLayout from "@/auth/layouts/AuthLayout.vue";
import { cloneDeep } from "lodash";
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { Modal } from "bootstrap";
import "@/auth/assets/css/account.css";
import { sendAuthCode, signup, verifyAuthCode } from "@/auth/js/auth";
import {
  toastEmailMissMatch,
  toastEmailVerifyNotComplete,
  toastNotTypeCompanyName,
  toastPasswordMissmatch,
  toastPasswordNotFit,
} from "@/auth/js/toast";

const router = useRouter();
const signupData = ref({ email: "", name: "", password: "" });
const passwordConfirm = ref("");
const signupModal = ref();

const authStep = ref(1);
const authCode = ref("");
const authEmail = ref("");

watch(
  () => signupData.value.email,
  (email) => {
    if (
      signupData.value.email != authEmail.value ||
      authEmail.value.length == 0
    ) {
      authStep.value = 1;
    } else {
      authStep.value = 3;
    }
  }
);

async function clickSendAuthCode() {
  const success = await sendAuthCode(signupData.value.email);
  if (success) {
    authStep.value = 2;
  }
}

async function clickVerifyAuthCode() {
  const verifyEmail = signupData.value.email;
  const success = await verifyAuthCode(signupData.value.email, authCode.value);
  if (success) {
    authStep.value = 3;
    authEmail.value = verifyEmail;
  }
}

async function clickSignup() {
  if (signupData.value.password < 8 || signupData.value.password.length > 16) {
    toastPasswordNotFit();
    return false;
  } else if (signupData.value.password != passwordConfirm.value) {
    toastPasswordMissmatch();
    return false;
  } else if (authEmail.value == "") {
    toastEmailVerifyNotComplete();
    return false;
  } else if (authEmail.value != signupData.value.email) {
    toastEmailMissMatch();
    return false;
  } else if (signupData.value.name == "") {
    toastNotTypeCompanyName();
    return false;
  }

  const data = cloneDeep(signupData.value);

  const success = await signup(data);
  if (success) {
    const modalInstance = new Modal(signupModal.value);
    modalInstance.show();
  }
}

function gotoLogin() {
  router.push("/user/login");
}
</script>

<template>
  <AuthLayout>
    <div class="account-header">
      <div>Sign Up to TeongBin Monitoring Platform</div>
    </div>
    <div class="account-main">
      <form>
        <div class="input-item">
          <input
            type="text"
            inputmode="email"
            v-model="signupData.email"
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
        <div class="form-text pwd-notice" id="basic-addon4">
          ※ 비밀번호는 8~16자로 설정해주세요.
        </div>
        <div class="input-item input-error" >
          <input
            type="password"
            inputmode="text"
            v-model="signupData.password"
            class="box input-field"
            placeholder=""
            autoComplete="off"
            :class="{
              'input-error':
                (0 < signupData.password.length &&
                  signupData.password.length < 8) ||
                signupData.password.length > 16,
            }"
          />
          <label class="input-label">비밀번호</label>
        </div>
        <div class="input-item">
          <input
            type="password"
            inputmode="text"
            v-model="passwordConfirm"
            class="box input-field"
            placeholder=""
            autoComplete="off"
            :class="{
              'input-error':
                0 < passwordConfirm.length &&
                signupData.password != passwordConfirm,
            }"
          />
          <label class="input-label">비밀번호 재입력</label>
        </div>
        <div class="input-item">
          <input
            type="text"
            inputmode="text"
            v-model="signupData.name"
            class="box input-field"
            placeholder=""
          />
          <label class="input-label">회사명</label>
        </div>
        <button type="button" @click="clickSignup" class="box submit-btn">
          계정 생성
        </button>

        <div
          class="modal fade"
          id="signupAlert"
          data-bs-backdrop="static"
          data-bs-keyboard="false"
          tabindex="-1"
          aria-labelledby="signupAlertLabel"
          aria-hidden="true"
          ref="signupModal"
        >
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="signupAlertLabel">
                  회원가입 성공
                </h1>
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div class="modal-body">
                <div class="navigator-text">
                  {{ signupData.email }}의 회원가입이 완료되었습니다
                </div>
                <div class="navigator-text">
                  확인을 눌러 로그인 페이지로 이동하세요
                </div>
              </div>
              <div class="modal-footer">
                <button
                  type="button"
                  class="btn btn-primary"
                  data-bs-dismiss="modal"
                  @click="gotoLogin"
                >
                  확인
                </button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </AuthLayout>
</template>

<style scoped>
.navigator-text {
  font-size: 1.2rem;
}
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
.pwd-notice {
  font-size: 1rem;
  text-align: left;
}
</style>
