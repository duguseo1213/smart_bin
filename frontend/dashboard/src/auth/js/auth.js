import axios from "axios";
import router from "@/router";
import {
  toastAlreadyRegister,
  toastSendAuthCode,
  toastSuccessChangePassword,
  toastSuccessLogin,
  toastVerifyEmail,
  toastWrongAuthCode,
  toastWrongEmail,
  toastWrongLoginData,
} from "@/auth/js/toast";
import { useAuthStore } from "@/auth/stores/auth";

export async function sendAuthCode(email) {
  const data = { email: email };
  return await axios
    .post("/api/v1/user/email", data)
    .then((res) => {
      toastSendAuthCode();
      return true;
    })
    .catch((error) => {
      toastWrongEmail();
      return false;
    });
}

export async function verifyAuthCode(email, authCode) {
  const data = { email: email, code: authCode };
  return await axios
    .post("/api/v1/user/verify", data)
    .then((res) => {
      toastVerifyEmail();
      return true;
    })
    .catch((error) => {
      toastWrongAuthCode();
      return false;
    });
}

export async function signup(data) {
  let success = true;
  await axios
    .post("/api/v1/user/signup", data)
    .then((res) => {})
    .catch((error) => {
      success = false;
      toastAlreadyRegister();
    });

  return success;
}

export async function login(data, saveEmail) {
  await axios
    .post("/api/v1/user/login", data)
    .then((res) => {
      sessionStorage.setItem("teongbinToken", res.headers.authorization);
      sessionStorage.setItem("teongbinLoginState", true);
      if (saveEmail) {
        localStorage.setItem("teongbinEmail", data.email);
      } else {
        localStorage.removeItem("teongbinEmail");
      }

      toastSuccessLogin();
      setTimeout(() => {
        const authStore = useAuthStore();
        authStore.loginState = true;
        router.push("/");
      }, 1000);
    })
    .catch((error) => {
      if (error.response.status == 401) {
        toastWrongLoginData();
      }
    });
}

export async function changePassword(email, password) {
  const data = {
    email: email,
    password: password,
  };
  await axios
    .post("/api/v1/user/passwordchange", data)
    .then((res) => {
      toastSuccessChangePassword();
      setTimeout(() => {
        router.push("/");
      }, 1000);
    })
    .catch((error) => {});
}