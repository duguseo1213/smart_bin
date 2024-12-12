import axios from "axios";
import { useShortcutStore } from "@/dashboard/stores/shortcut";
import { useTrashcanStore } from "@/dashboard/stores/trashcan";
import { useUserStore } from "@/dashboard/stores/user";
import { useAuthStore } from "@/auth/stores/auth";
import { cloneDeep } from "lodash";
import router from "@/router";
import {
  toastAlreadyRegisterTrashcan,
  toastExpireToken,
  toastSuccessAddShortcut,
  toastSuccessAddTrashcan,
  toastSuccessChangeUserInfo,
  toastSuccessModifyTrashcanInfo,
  toastSuccessRemoveShortcut,
  toastSuccessRemoveTrashcan,
  toastSuccessRenameShortcut,
  toastSuccessSetDefaultMap,
} from "@/dashboard/js/toast";

const shortcutStore = useShortcutStore();
const trashcanStore = useTrashcanStore();
const userStore = useUserStore();
const authStore = useAuthStore();

function logoutUser() {
  sessionStorage.removeItem("teongbinToken");
  sessionStorage.removeItem("teongbinUserName");
  authStore.loginState = false;
  
  toastExpireToken();

  setTimeout(() => {
    router.push("/user/login");
  }, 1000);
}

const apiClient = axios.create();

apiClient.interceptors.request.use(
  (config) => {
    config.headers.Authorization = sessionStorage.getItem("teongbinToken");
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.data.status == 401) {
      logoutUser();
    } else {
    }

    return Promise.reject(error);
  }
);

export function getUserInfo() {
  apiClient
    .get("/api/v1/user/profile")
    .then((res) => {
      userStore.userInfo = res.data.data;
    })
    .catch((error) => {});
}

export function changeUserInfo(name, password) {
  const data = {
    password: password,
    name: name,
  };
  apiClient
    .post("/api/v1/user/update", data)
    .then((res) => {
      toastSuccessChangeUserInfo();
      getUserInfo();
    })
    .catch((error) => {});
}

export async function addTrashcan(data) {
  return apiClient
    .post("/api/v1/trash/new", data)
    .then((res) => {
      toastSuccessAddTrashcan();
      getTrashcanList();
      return true;
    })
    .catch((error) => {
      toastAlreadyRegisterTrashcan();
      return false;
    });
}

export async function getTrashcanList() {
  let success = true;
  await apiClient
    .get("/api/v1/trash/user/trashcan")
    .then((res) => {
      trashcanStore.trashcanList = res.data.data;
      getTrashcanRest();
    })
    .catch((error) => {
      success = false;
    });

  return success;
}

export function getTrashcanRest() {
  apiClient
    .get("/api/v1/trash/user/trashcan/rest")
    .then((res) => {
      const trashcanStateList = res.data.data;
      if (trashcanStateList.length == trashcanStore.trashcanList.length) {
        trashcanStateList.forEach((trashcanState) => {
          const trashcan = trashcanStore.trashcanList.find(
            (trashcan) => trashcan.trashcanId === trashcanState.trashcanId
          );
          if (trashcan) {
            trashcan.restPercent = trashcanState.restPercent;
          }
        });
      }
    })
    .catch((error) => {});
}

export function modifyTrashcanInfo() {
  const data = cloneDeep(trashcanStore.trashcanInfo);

  const postData = {
    latitude: data.latitude,
    longitude: data.longitude,
    nickname: data.nickname,
  };

  apiClient
    .post(`/api/v1/trash/${data.trashcanId}/update`, postData)
    .then((res) => {
      toastSuccessModifyTrashcanInfo();
      getTrashcanList();
      trashcanStore.selectTrashcanList.splice(0, 1);
    })
    .catch((error) => {});
}

export async function removeSubscribeTrashcan() {
  const idList = [];
  trashcanStore.selectTrashcanList.forEach((trashcanIdx) => {
    idList.push(trashcanStore.trashcanList[trashcanIdx].trashcanId);
  });

  const deletePromises = idList.map((id) => {
    return apiClient.post(`/api/v1/trash/${id}/delete`, null);
  });

  try {
    await Promise.all(deletePromises);
    trashcanStore.selectTrashcanList.splice(
      0,
      trashcanStore.selectTrashcanList.length
    );
    toastSuccessRemoveTrashcan();
    getTrashcanList();
  } catch (error) {}
}

function getRandomNum(seed) {
  return (((seed * seed * seed + 9 * seed * seed + seed) * 29) % 32) * 6;
}

export async function addShortcut(setting) {
  const success = await apiClient
    .post("/api/v1/user/shortcut/new", setting)
    .then((res) => {
      toastSuccessAddShortcut();
      getShortcutList();
      return true;
    })
    .catch((error) => {
      return false;
    });

  return success;
}

export function getShortcutList() {
  apiClient
    .get("/api/v1/user/shortcut")
    .then((res) => {
      shortcutStore.shortcutList = res.data.data;
      shortcutStore.colorList.splice(0, shortcutStore.colorList.length);
      shortcutStore.shortcutList.forEach((shortcut, idx) => {
        const r = getRandomNum(idx);
        const g = getRandomNum(r);
        const b = getRandomNum(g);
        shortcutStore.colorList.push({
          red: r,
          green: g,
          blue: b,
        });
      });
    })
    .catch((error) => {});
}

export function postRenameShortcut(shortcut, newNickname) {
  const data = {
    nickname: newNickname,
  };
  apiClient
    .post(`/api/v1/user/shortcut/${shortcut.shortcut_id}/update`, data)
    .then((res) => {
      toastSuccessRenameShortcut();
      getShortcutList();
    })
    .catch((res) => {});
}

export function postRemoveShortcut(shortcut) {
  apiClient
    .post(`/api/v1/user/shortcut/${shortcut.shortcut_id}/delete`, null)
    .then((res) => {
      toastSuccessRemoveShortcut();
      getShortcutList();
    })
    .catch((error) => {});
}

export function postDefaultMapSetting(setting) {
  const data = {
    latitude: setting.center._lat,
    longitude: setting.center._lng,
    zoom_level: setting.zoom,
  };
  apiClient
    .post("/api/v1/user/update", data)
    .then((res) => {
      toastSuccessSetDefaultMap();
    })
    .catch((error) => {});
}
