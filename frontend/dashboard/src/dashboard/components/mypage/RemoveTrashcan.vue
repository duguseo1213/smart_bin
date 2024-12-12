<script setup>
import { useTrashcanStore } from "@/dashboard/stores/trashcan";
import { removeSubscribeTrashcan } from "@/dashboard/js/remote";
import { ref } from "vue";
import { Modal } from "bootstrap";

const trashcanStore = useTrashcanStore();

const removeTrashcanModal = ref();

function checkSelectTrashcan() {
  if (trashcanStore.selectTrashcanList.length > 0) {
    const modalInstance = new Modal(removeTrashcanModal.value);
    modalInstance.show();
  } else {
  }
}

function removeTrashcan() {
  removeSubscribeTrashcan();
}
</script>

<template>
  <button type="button" class="btn" @click="checkSelectTrashcan">
    등록 해제
  </button>

  <div
    class="modal fade"
    id="removeTrashcan"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel"
    aria-hidden="true"
    ref="removeTrashcanModal"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">
            등록 해제 제품
          </h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div
            v-for="(trashcan, idx) in trashcanStore.selectTrashcanList"
            class="remove-item"
          >
            {{ trashcanStore.trashcanList[trashcan].nickname }} ({{
              trashcanStore.trashcanList[trashcan].serialNumber
            }})
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
            @click="removeTrashcan"
          >
            등록 해제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.remove-item {
  font-weight: normal;
  text-align: left;
  padding-left: 0.5rem;
  padding-bottom: 0.5rem;
  letter-spacing: 0.03rem;
}
.btn:hover {
  font-weight: bold;
  background-color: rgba(20, 200, 20, 0.1);
}
</style>
