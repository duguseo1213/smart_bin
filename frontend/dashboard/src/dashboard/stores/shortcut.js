import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useShortcutStore = defineStore('shortcut', () => {
  const shortcutList = ref([]);
  const colorList = ref([]);

  return { colorList, shortcutList }
})
