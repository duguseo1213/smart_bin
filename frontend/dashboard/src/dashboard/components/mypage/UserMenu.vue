<script setup>
import AddTrashcan from "@/dashboard/components/mypage/AddTrashcan.vue";
import ModifyInfo from "@/dashboard/components/mypage/ModifyInfo.vue";
import RemoveTrashcan from "@/dashboard/components/mypage/RemoveTrashcan.vue";
import { useUserStore } from "@/dashboard/stores/user";
import { useTrashcanStore } from "@/dashboard/stores/trashcan";

const userStore = useUserStore();
const trashcanStore = useTrashcanStore();
</script>

<template>
  <div>
    <table>
      <tbody>
        <tr>
          <th>사명</th>
        </tr>
        <tr>
          <td>{{ userStore.userInfo.name }}</td>
        </tr>
        <tr>
          <th>제품 수</th>
        </tr>
        <tr>
          <td>{{ trashcanStore.trashcanList.length }}개</td>
        </tr>
        <tr>
          <th>
            <AddTrashcan />
          </th>
        </tr>
        <tr>
          <th>
            <ModifyInfo />
          </th>
        </tr>
        <tr>
          <th>
            <RemoveTrashcan />
          </th>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
table {
  width: 8rem;
}
th {
  font-size: 1.5rem;
  padding: 2rem 0.5rem;
  padding-bottom: 0rem;
  background-color: inherit;
}
td {
  font-size: 1.5rem;
  padding: 0.5rem;
}
</style>
