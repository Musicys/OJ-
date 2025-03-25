<template>
    <div class="pr">
        <div style="margin-bottom: 1em;">
            <span>
                <a-avatar>
                    <img alt="avatar" :src="state.state.user.user.userAvatar" />
                </a-avatar>

                <span> 名称: {{ state.state.user.user.userName }}</span>

            </span>

            <span> 邮箱 : {{ state.state.user.user.qqEmail }}</span>
        </div>
        <textarea @input="updateCharacterCount" v-model="value" placeholder="来评论交流吧,博主可能会邮箱回复哦！" name="" class="text"
            id="commentTextarea"></textarea>
        <div class="pr_but" style="justify-content: end;">
            <span>{{ characterCount }} / 400 字</span>
            <button @click="PostComment" class="but">提交</button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useStore } from 'vuex'
import { QuestionCommentControllerService } from '@/generated';
import { useRoute } from 'vue-router';
import { message } from 'ant-design-vue';
const route = useRoute()
const state = useStore();

const value = ref("")


const emit = defineEmits(['PostCommentLsit']);
// 定义一个响应式变量来存储字数
const characterCount = ref(0);

// 定义一个函数来更新字数
const updateCharacterCount = (event: Event) => {
    const textarea = event.target as HTMLTextAreaElement;
    characterCount.value = textarea.value.length;
};


const PostComment = async () => {
    const from = {
        content: value.value,
        questionid: route.params.id
    }

    // @ts-ignore
    let res = await QuestionCommentControllerService.editPostUsingPost1(from)

    if (res.code == 0) {
        await emit("PostCommentLsit")

        message.success("评论成功")
        value.value = ""
    }
    else {
        message.error("系统错误")
    }




}

</script>

<style scoped>
.pr {
    width: 90%;
    margin: 1em auto;
    border-radius: 15px;
    background: #1D2527;
    /* 假设 --mart-top-pr-back 的值 */
    display: flex;
    color: white;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 1em;
}

.pr .pr_but {
    align-items: center;
}

.pr .pr_but>span {
    margin-right: 1em;
}

.pr>div {
    width: 100%;
    display: flex;
    justify-content: space-between;
}

.pr>div>span {
    display: inline-block;
}

.pr .but {
    color: #333;
    /* 假设 --bk-back-color 的值 */
    background: #61A9DE;
    padding: .5em 1em;
    border-radius: 10px;
    border: none;
}

.pr .but:hover {
    cursor: pointer;
    background: #F5CF51;
}

.pr .text {
    width: 95%;
    resize: vertical;
    min-height: 120px;
    border-radius: 10px;
    color: white;
    /* 假设 --bk-font-color 的值 */
    border: none;
    padding: 1em;
    margin-bottom: .5em;
    background-color: transparent;
    font-family: inherit;
    font-size: inherit;
    line-height: inherit;
    outline: none;
    overflow: auto;
}

.pr .text:focus {
    background: #272F2F;
    /* 假设 --mart-commment-bot-back 的值 */
}
</style>