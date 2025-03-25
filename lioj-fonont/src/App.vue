<template>
  <div class=" layout-demo">
    <a-layout style="height: 400px;">
      <a-layout-header>
        <!-- t -->
        <Appheader></Appheader>
      </a-layout-header>
      <a-layout-content>
        <!-- 内容 -->
        <div class="center">
          <Appcenter></Appcenter>
        </div>
      </a-layout-content>




      <a-layout-footer>
        <!-- w -->
        <Appfooter></Appfooter>

      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import Appheader from "./views/Appheader.vue";
import Appcenter from "./views/Appcenter.vue"
import Appfooter from "./views/Appfooter.vue"
import { onMounted } from "vue";

// import { useStore } from 'vuex'
// const store = useStore()
// console.log("vuex", store);
onMounted(() => {
  console.log("欢迎来到我的oj");

  document.body.setAttribute('arco-theme', 'dark');
})
</script>

<style scoped>
.center {
  padding: 1em 0;
  background: #1A1A1A;
  border-top: .5px solid white;
  min-height: 70vh;
}

.layout-demo {
  width: 100vw;
  height: 100vh;
  overflow-x: hidden;
  background: #1A1A1A;

  /* background: #232324; */
  /* 定义整个滚动条的宽度 */
  &::-webkit-scrollbar {
    width: 5px;
  }

  /* 滚动条轨道(背景)的样式 */
  &::-webkit-scrollbar-track {
    background-color: #2C2C2C;
  }

  /* 滚动条滑块的样式 */
  &::-webkit-scrollbar-thumb {
    background-color: #9F9F9F;
    border-radius: 10px;
    /* 可选：为滚动条滑块添加圆角 */
  }
}

.layout-demo :deep(.arco-layout-header) {
  /* background-color: red; */
  height: 100px;
  margin-bottom: .1px;


}

.layout-demo :deep(.arco-layout-content) {
  /* background-color: var(--color-primary-light-4); */





}

.layout-demo :deep(.arco-layout-footer) {
  height: 64px;
  /* background-color: black; */
  height: 100px;
  position: static;
  bottom: 0;
  left: 0;
  right: 0;

}
</style>