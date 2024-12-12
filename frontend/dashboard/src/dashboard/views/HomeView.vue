<script setup>
import { ref } from "vue";
import DashboardLayout from "@/dashboard/layouts/DashboardLayout.vue";
import Shortcut from "@/dashboard/components/home/Shortcut.vue";
import NaverMap from "@/dashboard/components/home/NaverMap.vue";
import TrashcanList from "@/dashboard/components/home/TrashcanList.vue";

import { getUserInfo } from "@/dashboard/js/remote";

const $mapRef = ref();
const center = ref();

function getSetting() {
  center.value = $mapRef.value.getSetting();
}

function changeSetting(setting) {
  $mapRef.value.changeSetting(setting);
}

function moveCenter(trashcan) {
  $mapRef.value.moveCenter(trashcan);
}

getUserInfo(false);
</script>

<template>
  <DashboardLayout>
    <Shortcut
      @get-setting="getSetting"
      @changeSetting="changeSetting"
      :shortcut="center"
    />
    <div class="info-container">
      <NaverMap ref="$mapRef" />
      <TrashcanList @move-center="moveCenter" />
    </div>
  </DashboardLayout>
</template>

<style scoped>
.info-container {
  display: flex;
  padding: 1rem;
}
</style>
