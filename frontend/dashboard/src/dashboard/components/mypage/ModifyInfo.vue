<script setup>
import { useTrashcanStore } from "@/dashboard/stores/trashcan";
import { ref } from "vue";
import { Modal } from "bootstrap";
import { modifyTrashcanInfo } from "@/dashboard/js/remote";
import { cloneDeep } from "lodash";
import ModifyCoordMap from "@/dashboard/components/mypage/ModifyCoordMap.vue";

const trashcanStore = useTrashcanStore();

const modifyInfoModal = ref();

function checkSelectTrashcan() {
  if (trashcanStore.selectTrashcanList.length == 1) {
    trashcanStore.trashcanInfo = cloneDeep(trashcanStore.trashcanList[trashcanStore.selectTrashcanList[0]]);
    const modalInstance = new Modal(modifyInfoModal.value);
    modalInstance.show();
  }
}

function modifyInfo() {
  modifyTrashcanInfo();
}
</script>

<template>
  <button type="button" class="btn" @click="checkSelectTrashcan">
    정보 변경
  </button>

  <div
    class="modal fade"
    id="modifyInfo"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel"
    aria-hidden="true"
    ref="modifyInfoModal"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">제품 정보</h1>
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
              v-model="trashcanStore.trashcanInfo.serialNumber"
              aria-describedby="basic-addon1"
              disabled="true"
            />
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text info-input" id="nickname-label"
              >별칭</span
            >
            <input
              type="text"
              class="form-control"
              v-model="trashcanStore.trashcanInfo.nickname"
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
              v-model="trashcanStore.trashcanInfo.latitude"
              aria-describedby="basic-addon1"
              disabled="true"
            />
            <span class="input-group-text info-input" id="longitude-label"
              >경도</span
            >
            <input
              type="text"
              class="form-control"
              v-model="trashcanStore.trashcanInfo.longitude"
              aria-describedby="basic-addon1"
              disabled="true"
            />
          </div>
          <div>
            <ModifyCoordMap />
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn" data-bs-dismiss="modal">
            취소
          </button>
          <button
            type="button"
            class="btn"
            data-bs-dismiss="modal"
            @click="modifyInfo"
          >
            수정
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.btn:hover {
  font-weight: bold;
  background-color: rgba(20, 200, 20, 0.1);
}
.info-input {
  width: 4rem;
  justify-content: center;
}
</style>