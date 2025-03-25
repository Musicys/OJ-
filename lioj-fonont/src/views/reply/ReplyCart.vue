<template>


    <div class="reply">
        <div class="user-info">
            <img :src="data.usercoment.userAvatar" class="avatar" />
            <span class="username">{{ data.usercoment.userName }}</span>

            <span style="color: wheat;">：回复了我的评论</span>


            <a-tag v-if="!data.requested" style="background: #FF79C6; margin-left: 1em;">
                最新
            </a-tag>

        </div>
        <div class="content"><span>{{ data.content }}</span> <span>

                <a-typography-paragraph style="max-width:50px ; cursor: pointer;" underline :ellipsis="{
                    rows: 1,
                    showTooltip: true,
                }">
                    {{ data.questionComment
                        .content }}
                </a-typography-paragraph>
            </span></div>
        <div class="footer">
            <span class="time">{{ data.createtime }}</span>


            <a-button @click="updateInput(true)">回复</a-button>
        </div>

        <a-comment align="right" v-if="Isinput" :avatar="state.state.user.user.userAvatar">
            <template #actions>
                <a-button key="0" style="color: black;" @click="updateInput(false)" type="secondary"> 取消 </a-button>
                <a-button key="1" @click="sendComment()" type="primary"> 发送</a-button>
            </template>
            <template #content>
                <a-input style="background: #1D2527;" v-model="fromValue"
                    :placeholder="`@${data.usercoment.userName}`" />
            </template>
        </a-comment>
    </div>

</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { message } from "ant-design-vue"
import { QuestionCommentControllerService } from "@/generated"
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
const route = useRoute()
const { data } = defineProps(["data"])
const Isinput = ref(false)

const fromValue = ref("")

const state = useStore()

const sendComment = async () => {
    if (fromValue.value.length == 0) {
        message.success("还没输入内容呢！")
        return
    }



    const form = {
        content: fromValue.value,
        pid: data.pid,
        "questionid": data.questionId,
        "commentid": data.usercoment.id,
        "commentids": data.id
    }
    // @ts-ignore
    //子评论 
    let res = await QuestionCommentControllerService.editPostUsingPost1(form)
    console.log(form);

    if (res.code == 0) {


        message.success("评论成功！")
        fromValue.value = ""
        Isinput.value = false
        //评论 重新渲染 //

    }
    else {
        message.error("发送失败！")

    }
}
const updateInput = (boler) => {
    Isinput.value = boler
}
//
const initial = async () => {

    if (data.requested) {
        return
    }
    await QuestionCommentControllerService.addRequestedUsingGet(data.id)

}
onMounted(() => {
    console.log("用户数据", data);
    initial()
})
</script>

<style scoped>
.time {
    font-size: 12px;
    color: white;

}

.reply {
    border-bottom: .5px solid rgba(255, 255, 255, .6);
    margin-bottom: 1em;
    padding-bottom: 1em;
}

.user-info {
    display: flex;
    align-items: center;
    margin-bottom: 8px;
}

.avatar {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    margin-right: 8px;
}

.username {
    font-size: 14px;
    color: white;
}

.content {
    font-size: 14px;
    color: white;
    line-height: 1.5;
    display: flex;
    justify-content: space-between;

    &>span {
        display: block;
        margin-right: 1em;
    }

    &>span:nth-child(1) {
        flex: 1;
    }

    &>span:nth-child(2) {
        width: 50px;

    }
}

.footer {
    margin-top: 8px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>