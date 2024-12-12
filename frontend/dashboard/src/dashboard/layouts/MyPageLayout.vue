<script setup>
import DashboardLayout from "@/dashboard/layouts/DashboardLayout.vue";
import MyPageMenu from "@/dashboard/components/mypage/MyPageMenu.vue";
</script>

<template>
  <DashboardLayout>
    <MyPageMenu> </MyPageMenu>
    <slot> </slot>
  </DashboardLayout>
</template>

<style scoped></style>
