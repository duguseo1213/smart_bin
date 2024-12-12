<script setup>
import { useTrashcanStore } from "@/dashboard/stores/trashcan";
import { useMapStore } from "@/dashboard/stores/map";
import { ref } from "vue";

const trashcanStore = useTrashcanStore();
const mapStore = useMapStore();
const lackList = ref([]);

trashcanStore.$subscribe((mutation, state) => {
  const list = state.trashcanList.filter(
    (trashcan) => trashcan.restPercent >= 70
  );
  lackList.value = list;
});
</script>

<template>
  <section class="product-state-section">
    <div class="product-state-container">
      <div v-for="(trashcan, idx) in trashcanStore.trashcanList" :key="idx">
        <button
          v-if="
            mapStore.bounds._min._lat <= trashcan.latitude &&
            trashcan.latitude <= mapStore.bounds._max._lat &&
            mapStore.bounds._min._lng <= trashcan.longitude &&
            trashcan.longitude <= mapStore.bounds._max._lng
          "
          @click="$emit('moveCenter', trashcan)"
          class="product-state-item list-btn"
        >
          <div class="text-info-container">
            <div class="trashcan-text-info nickname">
              {{ trashcan.nickname }}
            </div>
            <div class="trashcan-text-info">{{ trashcan.restPercent }} %</div>
          </div>
          <div>
            <i
              class="bi bi-trash2-fill trashcan-img"
              :class="{
                'trashcan-lack': trashcan.restPercent >= 70,
                'trashcan-half':
                  30 <= trashcan.restPercent && trashcan.restPercent < 70,
                'trashcan-enough': trashcan.restPercent < 30,
              }"
            ></i>
          </div>
        </button>
      </div>
    </div>
    <div
      v-if="lackList.length > 0"
      class="toast-container position-fixed bottom-0 end-0 p-3 toast-width"
    >
      <div
        id="liveToast"
        class="toast show"
        role="alert"
        aria-live="assertive"
        aria-atomic="true"
        ref="toastElement"
      >
        <div class="toast-header">
          <strong class="me-auto warning">WARNING</strong>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="toast"
            aria-label="Close"
          ></button>
        </div>
        <div class="toast-body warning-list">
          <div class="toast-name" v-for="(trashcan, idx) in lackList">
            <button class="warning-item list-btn" @click="$emit('moveCenter', trashcan)">
              {{ trashcan.nickname }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.product-state-section {
  width: 100%;
}
.product-state-container {
  height: 500px;
  overflow-y: scroll;
  scrollbar-width: none;
  ::-webkit-scrollbar {
    display: none;
    width: 0px;
  }
}
.product-state-item {
  display: flex;
  margin: 1rem;
  margin-bottom: 0;
}
.list-btn{ 
  background: none;
  border: none;
}
.text-info-container {
  align-content: center;
}
.trashcan-text-info {
  width: auto;
  min-width: 6rem;
  max-width: 6rem;
  white-space: nowrap;
  align-items: center;
  overflow-x: scroll;
  margin-right: 1rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
.nickname {
  font-size: 1rem;
}
.trashcan-img {
  font-size: 3rem;
}
.trashcan-enough {
  color: greenyellow;
}
.trashcan-half {
  color: yellow;
}
.trashcan-lack {
  color: red;
}
.warning-list {
  justify-content: left;
}
.toast-width {
  max-width: 14rem;
}
.toast-name {
  white-space: nowrap;
  overflow-x: hidden;
  font-size: 1.5rem;
  display: flex;
}
.warning {
  font-size: 1.5rem;
  color: red;
}
</style>
