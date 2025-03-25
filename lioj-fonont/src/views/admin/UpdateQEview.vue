<template>
    <div class="mine">
        <h1>{{ Isxx ? "修改" : "添加" }}题目</h1>

        <br>
        <a-form class="forms" :model="form" style=" width:80%; min-width:1080px;margin: auto;  " @submit="submit">

            <a-input v-model="form.title" placeholder="亲输入题目标题" />


            <MdEditor title="题目描述" :ste="form.content" @custom-event="setformcontent"></MdEditor>


            <MdEditor title="题目解析" :ste="form.answer" @custom-event="setformanswer"></MdEditor>



            <a-input-tag v-model="form.tags" placeholder="题目标签 输入后回车" />


            <!-- <a-input v-model="" placeholder="please enter your username..." /> -->




            <span style="font-size: .8em; width: 120px;">内存：</span>
            <a-input-number placeholder="亲输入题目内存限制" v-model="form.judgeConfig.memoryLimit" />


            <span style="font-size: .8em; width: 120px;">堆栈：</span>
            <a-input-number placeholder="亲输入堆栈限制" v-model="form.judgeConfig.stackLimit" />


            <span style="font-size: .8em; width: 120px;">时间：</span>
            <a-input-number placeholder="亲输入时间限制" v-model="form.judgeConfig.timeLimit" />











            <a-row v-for="(post, index) of form.judgeCase" :key="index" :field="`判题标准[${index}]`" :gutter="10"
                style="width: 100%;display: flex;  justify-content: start; margin: .5em auto;">
                <a-col :span="8">
                    <a-input v-model="post.input" placeholder="输入案例" />
                </a-col>
                <a-col :span="8">
                    <a-input v-model="post.output" placeholder="输出案例" />
                </a-col>
                <a-col :span="6">
                    <a-button @click="handleDelete(index)" style="width: 100%; "
                        :style="{ marginLeft: '10px' }">删除</a-button>

                </a-col>

            </a-row>



            <a-button @click="handleAdd">添加判题标准</a-button>






            <div style="margin: 1em auto; width: 40%; display: flex; justify-content: space-between;">
                <a-button html-type="submit" style="flex: 1; margin-right:2em ;">{{ Isxx ? "修改" : "添加" }}</a-button>
                <a-button @click="goaccss" style="flex: 1;">返回</a-button>
            </div>

        </a-form>
    </div>

</template>

<script setup lang="ts">
import { QuestionControllerService, QuestionUpdateRequest } from '@/generated';
// QuestionAddRequest
import { QuestionAddRequest } from '@/generated';
import { onMounted, ref, Ref } from "vue"
import MdEditor from '@/components/MdEditor.vue';
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';
const routers = useRouter()
let pops = defineProps(["id", "boller"])


const Isxx = ref(true)

const goaccss = () => {
    routers.push({
        path: "/ascss"
    })
}

const handleAdd = () => {
    form.value.judgeCase?.push({
        "input": null,
        "output": null
    })
};
const handleDelete = (index) => {
    form.value.judgeCase?.splice(index, 1)
}


const form: Ref<QuestionAddRequest> = ref({

    "answer": "",
    "content": "",
    "judgeCase": [
        {
            "input": null,
            "output": null
        }
    ],
    "judgeConfig": {
        "memoryLimit": null,
        "stackLimit": null,
        "timeLimit": null
    },
    "tags": ["java"],
    "title": ""



})



const setformcontent = (str) => {


    form.value.content = str

}
const setformanswer = (str) => {


    form.value.answer = str

}

const List = async () => {




    if (Isxx) {
        let res = await QuestionControllerService.getQuestionByIdUsingGet(pops.id)
        if (res.code == 0) {
            form.value = res.data as QuestionAddRequest
            // @ts-ignore
            form.value.judgeCase = JSON.parse(form.value.judgeCase)
            // @ts-ignore
            form.value.judgeConfig = JSON.parse(form.value.judgeConfig)
            // @ts-ignore
            form.value.tags = JSON.parse(form.value.tags)

        }




    }
    else {
        return
    }
}


const submit = async () => {

    let res = null


    if (Isxx.value) { //修改

        res = await QuestionControllerService.updateQuestionUsingPost(form.value)
    }
    else {

        res = await QuestionControllerService.addQuestionUsingPost(
            form.value
        )


    }


    if (res != null && res.code == 0) {
        //请求成功
        routers.push({ path: "/ascss" })


        message.success("操作完成")


    }
    else {
        message.error("清检测字段信息")
    }
    //添加



}
onMounted(() => {
    List()
    Isxx.value = JSON.parse(pops.boller)

})

</script>

<style scoped>
.mine {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

h1 {
    color: rgba(255, 255, 255, .6);
    text-align: center;
    margin-bottom: 30px;
}

.a-form {
    background: #f5f5f5;
    padding: 30px;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.a-form-item {
    margin-bottom: 24px;
}

.a-input,
.a-select,
.a-input-number {
    width: 100%;
    border-radius: 4px;
    border: 1px solid #e5e5e5;
    padding: 8px 12px;
    transition: all 0.3s;
}

.a-input:hover,
.a-select:hover,
.a-input-number:hover {
    border-color: #00aeec;
}

.a-button {
    background: #00aeec;
    color: #fff;
    border: none;
    border-radius: 4px;
    padding: 8px 16px;
    transition: all 0.3s;
}

.a-button:hover {
    background: #0098d1;
}

.judge-case-container {
    margin-top: 24px;
    padding: 16px;
    background: #fff;
    border-radius: 4px;
    border: 1px solid #e5e5e5;
}

.judge-case-item {
    display: flex;
    align-items: center;
    margin-bottom: 16px;
}

.judge-case-item .a-input {
    flex: 1;
    margin-right: 16px;
}
</style>