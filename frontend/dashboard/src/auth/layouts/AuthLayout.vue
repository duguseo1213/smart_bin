<script setup></script>

<template>
  <main>
    <section>
      <div class="account">
        <slot> </slot>
      </div>
    </section>
  </main>
</template>

<style scoped>
main {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: rgb(254, 254, 254);
}
section {
  width: 400px;
  padding: 40px;
  display: flex;
  justify-content: center;
}
.account {
  width: 320px;
}
</style>
