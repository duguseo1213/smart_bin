<script setup>
import { onMounted, ref, watch } from "vue";
import { cloneDeep } from "lodash";
import { addTrashcan } from "@/dashboard/js/remote";
import Map from "@/dashboard/components/mypage/Map.vue";
import { useTrashcanStore } from "@/dashboard/stores/trashcan";
import { useUserStore } from "@/dashboard/stores/user";
import { Modal } from "bootstrap";

const trashcanStore = useTrashcanStore();
const userStore = useUserStore();
const addTrashcanModal = ref();
const modalInstance = ref();

onMounted(() => {
  modalInstance.value = new Modal(addTrashcanModal.value);
});

const trashcanData = ref({
  serialNumber: "",
  latitude: userStore.userInfo.latitude,
  longitude: userStore.userInfo.longitude,
  nickname: "",
});

userStore.$subscribe(() => {
  resetInput();
});

watch(
  () => trashcanStore.newTrashcanPosition,
  (position) => {
    trashcanData.value.latitude = position._lat;
    trashcanData.value.longitude = position._lng;
  }
);

async function addNewTrashcan() {
  const data = cloneDeep(trashcanData.value);

  const success = await addTrashcan(data);

  if(success) {
    resetInput();
    modalInstance.value.toggle();
  }
}

function resetInput() {
  trashcanData.value.serialNumber = "";
  trashcanData.value.nickname = "";
  trashcanData.value.latitude = userStore.userInfo.latitude;
  trashcanData.value.longitude = userStore.userInfo.longitude;
}
</script>

<template>
  <button
    type="button"
    class="btn"
    data-bs-toggle="modal"
    data-bs-target="#addProduct"
  >
    제품 등록
  </button>

  <div
    class="modal fade"
    id="addProduct"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel"
    aria-hidden="true"
    ref="addTrashcanModal"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">
            제품 및 설치 정보
          </h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="input-group mb-3">
            <span class="input-group-text info-input" id="serial-number-label"
              >S/N</span
            >
            <input
              type="text"
              class="form-control"
              v-model="trashcanData.serialNumber"
              aria-describedby="basic-addon1"
            />
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text info-input" id="nickname-label"
              >별칭</span
            >
            <input
              type="text"
              class="form-control"
              v-model="trashcanData.nickname"
              aria-describedby="basic-addon1"
            />
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text info-input" id="latitude-label"
              >위도</span
            >
            <input
              type="text"
              class="form-control"
              v-model="trashcanData.latitude"
              aria-describedby="basic-addon1"
              disabled="true"
            />
            <span class="input-group-text info-input" id="longitude-label"
              >경도</span
            >
            <input
              type="text"
              class="form-control"
              v-model="trashcanData.longitude"
              aria-describedby="basic-addon1"
              disabled="true"
            />
          </div>
          <div>
            <Map />
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn"
            data-bs-dismiss="modal"
            @click="resetInput"
          >
            취소
          </button>
          <button type="button" class="btn" @click="addNewTrashcan">
            등록
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.info-input {
  width: 4rem;
  justify-content: center;
}
.btn:hover {
  font-weight: bold;
  background-color: rgba(20, 200, 20, 0.1);
}
</style>
