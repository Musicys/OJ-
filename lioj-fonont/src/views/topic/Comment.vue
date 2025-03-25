<template>

    <a-comment datetime="2025-11-14" align="right">
        <template #author>
            <span>{{ pops.data.usercoment.userName }}</span>
            <a-tag style="color: black; background: #388079; margin: 0 1em;"> {{ pops.data.usercoment.userRole ==
                "admin" ? "管理员" :
                pops.data.usercoment.userRole == "user" ? "普通用户" : "已注销"

            }}</a-tag>
        </template>

        <template #actions>
            <span class="action" key="heart" @click="onLikeChange">
                <span v-if="pops.data.like">
                    <IconHeartFill :style="{ color: '#f53f3f' }" />
                </span>
                <span v-else>
                    <IconHeart />
                </span>
                {{ pops.data.good }}
            </span>

            <span class="action" @click="updateInput(true)" style="cursor: pointer;" key="reply">
                <IconMessage /> 回复
            </span>
        </template>
        <template #avatar>
            <router-link target="_blank" :to="{ path: `/user/${pops.data.usercoment.id}` }">
                <a-avatar>
                    <img alt="avatar" :src="pops.data.usercoment.userAvatar" />
                </a-avatar>

            </router-link>
        </template>
        <template #content>
            <div>
                {{

                    pops.data.content
                }}
            </div>
        </template>
        <a-comment align="right" v-if="Isinput" :avatar="state.state.user.user.userAvatar">
            <template #actions>
                <a-button key="0" style="color: black;" @click="updateInput(false)" type="secondary"> 取消 </a-button>
                <a-button key="1" @click="sendComment()" type="primary"> 发送</a-button>
            </template>
            <template #content>
                <a-input style="background: #1D2527;" v-model="fromValue"
                    :placeholder="`@${pops.data.usercoment.userName}`" />
            </template>
        </a-comment>
        <slot></slot>

    </a-comment>

    <div class="henx"></div>
</template>

<script setup lang="ts">
import { message } from "ant-design-vue"
import { onMounted, ref, Ref } from "vue"
import { useStore } from 'vuex'
import { QuestionCommentControllerService } from "@/generated";
import { useRoute } from 'vue-router';
const route = useRoute()
const fromValue = ref("")

const emit = defineEmits(["PostCommentLsit"]);

const state = useStore();

const pops = defineProps(["data"])


const Isinput: Ref<Boolean> = ref(false)
const updateInput = (boller: boolean) => {

    Isinput.value = boller

}

const sendComment = async () => {

    if (fromValue.value.length == 0) {
        message.success("还没输入内容呢！")
        return
    }



    const form = {
        content: fromValue.value,
        pid: pops.data.id,
        "questionid": route.params.id,
        "commentid": pops.data.usercoment.id,
        "commentids":pops.data.id
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
        emit("PostCommentLsit")
    }
    else {
        message.error("发送失败！")

    }





}
const onLikeChange = async () => {

    if (!pops.data.like) {


        let res = await QuestionCommentControllerService.addLikeUsingGet(pops.data.id);
        if (res.code == 0) {
            pops.data.like = true
            message.success("点赞成功")
            pops.data.good += 1
        }
        else {
            message.error("系统繁忙")
        }


    }
    else {


        let res = await QuestionCommentControllerService.delLikeUsingGet(pops.data.id);
        if (res.code == 0) {
            pops.data.like = false
            message.success("取消点赞")
            pops.data.good -= 1
        }
        else {
            message.error("系统繁忙")
        }



    }
}

const onclickComentBox = () => {

}


onMounted(() => {
    /**
     * 
     *             {
              "id": "4",
              "usercoment": {
                "id": null,
                "userName": "管理员",
                "userAvatar": "admin",
                "userRole": "图片"
              },
              "like": false,
              "good": 2,
              "content": "真的吗",
              "name": "管理员",
              "userid": "1895117906623131650",
              "createtime": "2025-03-16"
            },
     * 
     */

    // console.log("Son组件 用户名称", pops.data.usercoment.userName);
    // console.log("Son组件 用户头像", pops.data.usercoment.userAvatar);
    // console.log("Son组件 用户评论", pops.data.content);
    // console.log("Son组件 用户时间", pops.data.createtime);
    // console.log("Son组件 用户点赞", pops.data.like);
    // console.log("Son组件 回复名称", pops.data.name, pops.data.userid);
    // console.log("Son组件 用户权限", pops.data.usercoment.userRole);
    // console.log(roe);

})




</script>

<style scoped>
.henx {
    width: 100%;
    border-bottom: .5px solid white;
}
</style>