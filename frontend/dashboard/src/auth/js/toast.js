import { toast } from "vue3-toastify";

const successTime = 1000;
const errorTime = 2000;

export function toastSendAuthCode() {
  toast.success("인증번호 전송이 완료되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastWrongEmail() {
  toast.error("잘못된 이메일을 입력하였습니다.", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastVerifyEmail() {
  toast.success("인증이 완료되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastWrongAuthCode() {
  toast.error("잘못된 인증번호입니다.", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastAlreadyRegister() {
  toast.error("이미 등록된 이메일입니다.", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastPasswordNotFit() {
  toast.error("비밀번호는 8~16자로 설정해주세요.", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastPasswordMissmatch() {
  toast.error("비밀번호가 일치하지 않습니다.", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastEmailVerifyNotComplete() {
  toast.error("이메일 인증이 완료되지 않았습니다.", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastEmailMissMatch() {
  toast.error("인증에 성공한 이메일과\n다른 이메일이 입력되었습니다.", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastNotTypeCompanyName() {
  toast.error("사명이 입력되지 않았습니다.", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastWrongLoginData() {
  toast.error("아이디 또는 비밀번호가\n잘못되었습니다", {
    autoClose: errorTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessLogin() {
  toast.success("로그인 성공", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessChangePassword() {
  toast.success("비밀번호 변경이 완료되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}
