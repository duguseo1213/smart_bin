import { toast } from "vue3-toastify";

const successTime = 1000;
const errorTime = 2000;
const warningTime = 2000;

export function toastExpireToken() {
  toast.warning("로그인 시간이 만료되었습니다.", {
    autoClose: warningTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessChangeUserInfo() {
  toast.success("개인정보 변경이 완료되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessAddTrashcan() {
  toast.success("제품 등록이 완료되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessModifyTrashcanInfo() {
  toast.success("제품 정보 변경이 완료되었습니다.", {
    autoClose: 1000,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessRemoveTrashcan() {
  toast.success("선택한 제품 등록이 해제되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessSetDefaultMap() {
  toast.success("기본맵 설정이 변경되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessAddShortcut() {
  toast.success("숏컷 등록이 완료되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessRenameShortcut() {
  toast.success("숏컷이름 변경이 완료되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastSuccessRemoveShortcut() {
  toast.success("숏컷삭제가 완료되었습니다.", {
    autoClose: successTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}

export function toastEnterUsername() {
  toast.warning("유저이름을 입력해주세요", {
    autoClose: warningTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  })
}

export function toastAlreadyRegisterTrashcan() {
  toast.warning("이미 등록된 시리얼넘버입니다", {
    autoClose: warningTime,
    position: toast.POSITION.BOTTOM_RIGHT,
    pauseOnHover: false,
  });
}