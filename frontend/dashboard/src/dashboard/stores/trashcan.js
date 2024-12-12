import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useTrashcanStore = defineStore('trashcan', () => {
  const trashcanList = ref([]);
  const selectTrashcanList = ref([]);
  const trashcanInfo = ref({
    trashcanId: -1,
    serialNumber: "",
    latitude: 0,
    longitude: 0,
    nickname: "",
  });

  return { trashcanList, selectTrashcanList, trashcanInfo }
})
