import { ref } from "vue";
import { defineStore } from "pinia";

export const useMapStore = defineStore("map", () => {
  const shortcutMarkerList = ref([]);
  const trashcanMarkerList = ref([]);
  const bounds = ref({
    _min: {
      _lat: 0,
      _lng: 0,
    },
    _max: {
      _lat: 0,
      _lng: 0,
    },
  });

  return { shortcutMarkerList, trashcanMarkerList, bounds };
});
