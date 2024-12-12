<script setup>
import { onMounted, ref, watch } from "vue";
import MyPageLayout from "@/dashboard/layouts/MyPageLayout.vue";
import { useUserStore } from "@/dashboard/stores/user";
import { changeUserInfo, getUserInfo } from "@/dashboard/js/remote";
import { toastEnterUsername } from "@/dashboard/js/toast";
import { toastPasswordMissmatch, toastPasswordNotFit } from "@/auth/js/toast";

const userStore = useUserStore();
const username = ref("");
const password = ref("");
const passwordConfirm = ref("");

watch(
  () => userStore.userInfo,
  (newUsername) => {
    username.value = userStore.userInfo.name;
  }
);

onMounted(() => {
  username.value = userStore.userInfo.name;
});

getUserInfo();

function changeInfo() {
  if (username.value == "") {
    toastEnterUsername();
    return false;
  } else if (
    password.value.length < 0 &&
    password.value.length < 8 &&
    password.value.length > 16
  ) {
    toastPasswordNotFit();
    return false;
  } else if (password.value != passwordConfirm.value) {
    toastPasswordMissmatch();
    return false;
  }

  changeUserInfo(username.value, password.value);
}
</script>

<template>
  <MyPageLayout>
    <div class="pwd-notice">※ 비밀번호는 8~16자로 설정해주세요.</div>
    <div class="pwd-table">
      <div name="changeInfo">
        <table>
          <tbody>
            <tr>
              <th>사명</th>
              <td><input type="text" v-model="username" /></td>
            </tr>
            <tr>
              <th>신규 비밀번호</th>
              <td>
                <input
                  type="password"
                  v-model="password"
                  autocomplete="off"
                  :class="{
                    'input-error':
                      (0 < password.length && password.length < 8) ||
                      password.length > 16,
                  }"
                />
              </td>
            </tr>
            <tr>
              <th>신규 비밀번호 확인</th>
              <td>
                <input
                  type="password"
                  v-model="passwordConfirm"
                  autocomplete="off"
                  :class="{
                    'input-error':
                      0 < passwordConfirm.length && password != passwordConfirm,
                  }"
                />
              </td>
            </tr>
          </tbody>
        </table>
        <button class="post-button" @click="changeInfo">변경</button>
      </div>
    </div>
  </MyPageLayout>
</template>

<style scoped>
.pwd-notice {
  padding: 3rem 0 0.5rem 2rem;
  font-size: 1rem;
  text-align: left;
}
.pwd-form {
  margin: 1rem;
}
th {
  padding: 1rem;
  width: 20rem;
  border-top: 1px solid black;
  border-bottom: 1px solid black;
  text-align: left;
}
td {
  padding: 1rem;
  width: 40rem;
  border-top: 1px solid black;
  border-bottom: 1px solid black;
}
input {
  width: 100%;
  height: 3rem;
  padding-left: 0.5rem;
}
.input-container {
  display: flex;
}
.post-button {
  padding: 1rem;
}
button {
  background: none;
  border: none;
}
</style>
