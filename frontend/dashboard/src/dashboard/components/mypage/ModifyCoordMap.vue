<script setup>
import { useTrashcanStore } from "@/dashboard/stores/trashcan";
import { useUserStore } from "@/dashboard/stores/user";
import { onMounted, ref } from "vue";

const userStore = useUserStore();
const trashcanStore = useTrashcanStore();
const map = ref();
const marker = ref();

onMounted(() => {
  initMap();
});

function initMap() {
  map.value = new naver.maps.Map("modifyCoordMap", {
    zoom: userStore.userInfo.zoom_level,
  });

  userStore.$subscribe(() => {
    map.value.setZoom(userStore.userInfo.zoom_level);
  });

  trashcanStore.$subscribe(() => {
    if (trashcanStore.selectTrashcanList.length == 1) {
      const center = new naver.maps.LatLng(
        trashcanStore.trashcanInfo.latitude,
        trashcanStore.trashcanInfo.longitude
      );

      map.value.setCenter(center);

      if (marker.value != null) marker.value.setMap(null);
      marker.value = new naver.maps.Marker({
        map: map.value,
        position: center,
        draggable: true,
      });

      naver.maps.Event.addListener(marker.value, "dragend", (event) => {
        marker.value.setPosition(event.coord);
        trashcanStore.trashcanInfo.latitude = event.coord._lat;
        trashcanStore.trashcanInfo.longitude = event.coord._lng;
      });

      naver.maps.Event.addListener(map.value, "click", (event) => {
        marker.value.setPosition(event.coord);
        trashcanStore.trashcanInfo.latitude = event.coord._lat;
        trashcanStore.trashcanInfo.longitude = event.coord._lng;
      });
    }
  });
}
</script>

<template>
  <section class="map-section">
    <div id="modifyCoordMap" class="map-size"></div>
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