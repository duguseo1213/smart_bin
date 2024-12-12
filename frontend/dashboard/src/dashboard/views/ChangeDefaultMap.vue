<script setup>
import NaverMap from "@/dashboard/components/home/NaverMap.vue";
import MyPageLayout from "@/dashboard/layouts/MyPageLayout.vue";
import {
  getShortcutList,
  getTrashcanList,
  getUserInfo,
  postDefaultMapSetting,
} from "@/dashboard/js/remote";
import { ref } from "vue";

const $mapRef = ref();

function getSetting() {
  postDefaultMapSetting($mapRef.value.getSetting());
}

getUserInfo();
getTrashcanList();
getShortcutList();
</script>

<template>
  <MyPageLayout>
    <div class="map-wrapper">
      <NaverMap ref="$mapRef" />
    </div>
    <button @click="getSetting">설정하기</button>
  </MyPageLayout>
</template>

<style scoped>
.map-wrapper {
  padding: 1rem;
}
button {
  background: none;
  border: none;
}
</style>
