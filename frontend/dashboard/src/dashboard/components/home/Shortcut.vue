<script setup>
import { onMounted, ref, watch } from "vue";
import { useShortcutStore } from "@/dashboard/stores/shortcut";
import {
  addShortcut,
  getShortcutList,
  postRemoveShortcut,
  postRenameShortcut,
} from "@/dashboard/js/remote";
const shortcutStore = useShortcutStore();

const props = defineProps({
  shortcut: Object,
});

const shortcutContainer = ref();

const handleWheel = (event) => {
  if (event.deltaY !== 0) {
    event.preventDefault();
    shortcutContainer.value.scrollLeft += event.deltaY;
  }
};

onMounted(() => {
  shortcutContainer.value.addEventListener("wheel", handleWheel);
});

function colorStyle(idx) {
  const color = shortcutStore.colorList.at(idx);
  return {
    color: `rgb(${color.red}, ${color.green}, ${color.blue})`,
  };
}

const hoverTimeout = ref();
const leaveTimeout = ref();
const select = ref();
const rename = ref("");
const renameInputVisible = ref(false);

function mouseoverHandler(idx) {
  if (leaveTimeout.value) {
    clearTimeout(leaveTimeout.value);
  }
  if (hoverTimeout.value) {
    clearTimeout(hoverTimeout.value);
  }
  
  if (select.value != idx) {
    hoverTimeout.value = setTimeout(() => {
    renameInputVisible.value = false;
    select.value = idx;
  }, 500);
  }
  
}

function mouseleave() {
  if (renameInputVisible.value) {
    return;
  }
  if (leaveTimeout.value) {
    clearTimeout(leaveTimeout.value);
  }
  if (hoverTimeout.value) {
    leaveTimeout.value = setTimeout(() => {
      clearTimeout(hoverTimeout.value);
      select.value = -1;
      rename.value = "";
      if (renameInputVisible.value) {
        toggleRenameInput();
      }
    }, 500);
  }
}

function toggleRenameInput() {
  renameInputVisible.value = !renameInputVisible.value;
}

function renameShortcut(shortcut) {
  postRenameShortcut(shortcut, rename.value);
  toggleRenameInput();
  rename.value = "";
}

function removeShortcut(shortcut) {
  postRemoveShortcut(shortcut);
}

const shortcutInputVisible = ref(false);
const menuText = ref("숏컷등록");
const shortcutName = ref("");

watch(
  () => props.shortcut,
  async (setting) => {
    if (setting) {
      const shortcut = {
        nickname: shortcutName.value,
        latitude: setting.center._lat,
        longitude: setting.center._lng,
        zoom_level: setting.zoom,
      };

      toggleInput();
      shortcutName.value = "";
      addShortcut(shortcut);
    }
  }
);

function toggleInput() {
  shortcutInputVisible.value = !shortcutInputVisible.value;
  if (shortcutInputVisible.value) {
    menuText.value = "등록취소";
  } else {
    menuText.value = "숏컷등록";
  }
}

getShortcutList();
</script>

<template>
  <div class="shortcut-section">
    <div class="shortcut-container scroll-container" ref="shortcutContainer">
      <div v-for="(shortcut, idx) in shortcutStore.shortcutList" :key="idx">
        <div
          class="shortcut-box"
          @mouseover="mouseoverHandler(idx)"
          @mouseleave="mouseleave()"
        >
          <button
            class="shortcut-item"
            @click="$emit('changeSetting', shortcut)"
          >
            <div :style="colorStyle(idx)">
              {{ shortcut.nickname }}
            </div>
          </button>
          <div class="manage-shortcut-container" v-if="select == idx">
            <button>
              <i
                class="bi bi-pencil-square manage-img"
                @click="toggleRenameInput()"
              ></i>
            </button>
            <div
              v-if="renameInputVisible"
              class="input-group mb-3 rename-popup"
            >
              <input
                type="text"
                class="form-control"
                v-model="rename"
                placeholder="nickname"
                aria-label="nickname"
                aria-describedby="button-addon2"
              />
              <button
                class="btn btn-outline-secondary"
                type="button"
                id="button-addon2"
                :disabled="rename == ''"
                @click="renameShortcut(shortcut)"
              >
                변경
              </button>
            </div>
            <button>
              <i
                class="bi bi-x-square manage-img"
                @click="removeShortcut(shortcut)"
              ></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="shortcut-menu-container">
      <button @click="toggleInput" class="shortcut-menu-item shortcut-btn">
        {{ menuText }}
      </button>
      <div v-if="shortcutInputVisible" class="input-group mb-3 popup">
        <input
          type="text"
          class="form-control"
          v-model="shortcutName"
          placeholder="nickname"
          aria-label="nickname"
          aria-describedby="button-addon2"
        />
        <button
          class="btn btn-outline-secondary"
          type="button"
          id="button-addon2"
          :disabled="shortcutName == ''"
          @click="$emit('getSetting')"
        >
          등록
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.shortcut-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 0;
}
.shortcut-container {
  display: flex;
  align-items: center;
  width: 860px;
  position: relative;
  margin: 1rem;
}
.shortcut-box {
  display: flex;
  margin-right: 1rem;
}
.shortcut-item {
  float: left;
  justify-content: center;
  max-width: 8rem;
  font-size: 1.2rem;
  min-width: 8rem;
  white-space: nowrap;
  align-items: center;
  font-weight: bold;
  overflow-x: hidden;
}
.manage-shortcut-container {
  display: flex;
  padding: 0;
  height: 2rem;
  align-items: center;
}
button {
  border: none;
  background: none;
}
.manage-img {
  justify-content: center;
  font-size: 1rem;
}
.shortcut-menu-container {
  position: relative;
  display: inline-block;
  justify-content: center;
  align-items: center;
  width: 20%;
}
.shortcut-menu-item {
  padding: 1rem 0;
}
.popup {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

.rename-popup {
  position: relative;
  left: 0;
  top: 0.7rem;
  background-color: #fff;
  width: 12rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

.scroll-container {
  overflow-x: scroll;
  position: relative;
  padding-bottom: 6px;
  margin-bottom: 6px;
}

.scroll-container::-webkit-scrollbar {
  height: 4px;
}

.scroll-container::-webkit-scrollbar-track {
  background: transparent;
}

.scroll-container::-webkit-scrollbar-thumb {
  background: transparent;
}

.scroll-container:hover::-webkit-scrollbar-thumb {
  background: #888;
}

.scroll-container:hover::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
