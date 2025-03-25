<template>
    <a-layout class="layout">
        <a-layout-content class="content">
            <a-result status="404" title="404" sub-title="抱歉，您访问的页面不存在">
                <template #extra>
                    <a-button style="background: #3A3A3A;" type="primary" @click="goHome">返回首页</a-button>
                </template>
            </a-result>
        </a-layout-content>
    </a-layout>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
const router = useRouter()

const goHome = () => {
    router.push('/')
}
</script>

<style scoped>
.layout {
    height: 100vh;
}

.content {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>