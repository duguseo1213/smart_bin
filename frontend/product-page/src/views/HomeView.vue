<template>
  <section style="position:relative;" class="mb-5">
    <img src="@/assets/main_image2.webp" alt="main_img" data-aos-duration="800">
    <div class="overlay-text fade-in">
      <h1>텅빈과 함께하는</h1>
      <h1>스마트한 쓰레기통 관리</h1>
      <h4 style="margin-top: 50px;">
        <div class="row text-center justify-content-center my-3">
          <button class="join-button btn btn-warning" onclick="window.location.href='https://i11c101.p.ssafy.io/main/'"
            style="width: 200px; height: 75px;  color: #151619;">
            <b id="start">시작하기</b>
          </button>
        </div>
        <!-- <div class="row text-center justify-content-center my-3">
          <button class="join-button btn" onclick="window.location.href='https://i11c101.p.ssafy.io/main/'"
            style="width: 200px; height: 75px;  color: white; background-color: #0055FF;">
            <b id="start">시작하기</b>
          </button>
        </div>
        <div class="row text-center justify-content-center my-3">
          <button class="join-button btn btn-warning" onclick="window.location.href='https://i11c101.p.ssafy.io/main/'"
            style="width: 200px; height: 75px;  color: white; background-color: #00AAFF;">
            <b id="start">시작하기</b>
          </button>
        </div> -->
      </h4>
      <div class="scroll-button" @click="scrollToContent">
        <img src="@/assets/chevron-down.svg" alt="Scroll Down" class="scroll-icon" />
      </div>
    </div>
  </section>
  <div class="container-content">
    <ArticleComponent />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ArticleComponent from '@/components/ArticleComponent.vue';

const scrollToContent = () => {
  const contentSection = document.querySelector('.container-content');
  contentSection.scrollIntoView({ behavior: 'smooth' });
}
</script>

<style scoped>
.overlay-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  text-align: center;
  text-shadow: 3px 3px 4px rgba(0, 0, 0, 0.5);
}

.overlay-text h1 {
  margin: 20px 0;
  font-size: 4em;
  /* text-shadow: 20px black; */
}

.overlay-text h4 {
  margin-top: 50px;
}

.join-button {
  font-size: 1.5em;
}

.scroll-button {
  margin-top: 110px;

  cursor: pointer;
  animation: bounce 2s infinite;
}

.scroll-icon {
  width: 64px;
  /* 아이콘의 너비 조정 */
  height: 64px;
  /* 아이콘의 높이 조정 */
  stroke: #3c2424;
}

@keyframes bounce {

  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-15px);
  }
}

.fade-in {
  opacity: 0;
  animation: fadeIn 1s 0.5s forwards;
}

@keyframes fadeIn {
  to {
    opacity: 1;
  }
}

#start {
  font-family: "Orbit", sans-serif;
  /* font-weight: 400; */
  font-style: normal;
  letter-spacing: 4px;
}
</style>
