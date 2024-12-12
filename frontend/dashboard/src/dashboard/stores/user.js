import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref({
    email: "",
    name: "",
    latitude: 0,
    longitude: 0,
    zoom_level: 0,
  });

  return { userInfo }
})
