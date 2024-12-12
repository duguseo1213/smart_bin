<script setup>
import { useTrashcanStore } from "@/dashboard/stores/trashcan";
import { useUserStore } from "@/dashboard/stores/user";
import { onMounted, ref } from "vue";

const userStore = useUserStore();
const trashcanStore = useTrashcanStore();
const map = ref();

onMounted(() => {
  initMap();
});

function initMap() {
    userStore.$subscribe(() => {
    const center = new naver.maps.LatLng(
      userStore.userInfo.latitude,
      userStore.userInfo.longitude
    );

    map.value = new naver.maps.Map("map", {
      center: center,
      zoom: userStore.userInfo.zoom_level,
    });

    const marker = new naver.maps.Marker({ map: map.value, position: center });

    naver.maps.Event.addListener(map.value, "click", (event) => {
      marker.setPosition(event.coord);
      trashcanStore.newTrashcanPosition = event.coord;
    });
  });
}
</script>

<template>
  <section class="map-section">
    <div id="map" class="map-size"></div>
  </section>
</template>

<style scoped>
.map-section {
  float: left;
  padding-right: 1rem;
}
.map-size {
  width: 460px;
  height: 360px;
}
</style>
