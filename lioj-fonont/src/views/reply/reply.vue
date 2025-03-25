<template>
    <div class="reply-container" @scroll="onScroll">
        <div class="list">
            <ReplyCart v-for="i in replyList" :key="i.id" :data="i"></ReplyCart>
        </div>
        <div v-if="loading" class="loading">加载中...</div>
        <div v-if="CommentScrall" class="loading">加载完成...</div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { QuestionCommentControllerService } from '@/generated'
import ReplyCart from './ReplyCart.vue'
const replyList = ref([])
const loading = ref(false)
const CommentScrall = ref(false)
const pageInfo = ref({
    current: 1,
    pageSize: 10
})

const loadMore = async () => {
    if (loading.value) return
    loading.value = true
    try {
        const res = await QuestionCommentControllerService.listPostRequestedByPageUsingPost({
            ...pageInfo.value
        })
        if (res.code === 0) {
            replyList.value = [...replyList.value, ...res.data.records]
            if (res.data.records.length == 0) {
                //加载完成
                CommentScrall.value = true
            }
            else {

                pageInfo.value.current++
            }






        }
    } catch (error) {
        console.error(error)
    } finally {
        loading.value = false
    }
}

const onScroll = (e) => {
    const element = e.target
    if (Math.ceil(element.scrollTop + element.clientHeight) + 5 > element.scrollHeight) {
        loadMore()
    }
}

onMounted(() => {
    loadMore()
})
</script>

<style scoped>
.reply-container {
    height: 100%;
    overflow-y: auto;
    padding: 16px;
    width: 70%;
    margin: 0 auto;
    height: 80vh;

    overflow-x: auto;

    /* 定义滚动条轨道的样式 */
    &::-webkit-scrollbar {
        width: 10px;
        /* 滚动条宽度 */
    }

    /* 定义滚动条轨道的背景色 */
    &::-webkit-scrollbar-track {
        background-color: #2C2C2C;
        /* 背景色 */
        border: 1px solid white;
        /* 边框颜色 */
    }

    /* 定义滚动条滑块的样式 */
    &::-webkit-scrollbar-thumb {
        background-color: #D1D1D1;
        /* 滑块颜色 */
        border: 1px solid white;
        /* 边框颜色 */
    }

    /* 当鼠标悬停在滚动条上时改变滑块的颜色 */
    &::-webkit-scrollbar-thumb:hover {
        background-color: #C0C0C0;
        /* 鼠标悬停时滑块颜色 */
    }
}

.reply-item {
    padding: 12px 0;
    border-bottom: 1px solid #eee;
}





.loading {
    text-align: center;
    padding: 16px;
    color: #999;
}
</style>