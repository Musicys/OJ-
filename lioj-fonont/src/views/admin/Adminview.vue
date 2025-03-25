<template>
    <div class="mine">
        <!-- sach -->
        <div style="display: flex;">
            <a-form :model="form"
                style="display: flex; flex-wrap: wrap; margin: auto; max-width: 1200px; margin: 1em auto;">

                <a-row :gutter="32" style="width: 100%;display: flex;">
                    <a-col :span="6">


                        <a-input v-model="form.id" placeholder="输入题目编号" />

                    </a-col>
                    <a-col :span="6">

                        <a-input v-model="form.title" placeholder="输入题目标题" />


                    </a-col>
                    <a-col :span="6">
                        <a-input-tag v-model="form.tags" placeholder="题目标签" />



                    </a-col>
                    <a-col :span="6">
                        <a-button type="primary" :loading="loading" style=" width: 60px; background: #303030;"
                            @click="loadingSeach">搜索</a-button>
                        <a-button type="primary" style="margin-left: 1em;  width: 60px; background: #303030;"
                            @click="goupdateqv">添加</a-button>

                    </a-col>
                </a-row>



            </a-form>

        </div>
        <a-table :data="data" :pagination="false" style="margin-top: 30px;max-width: 1200px; margin: auto;">
            <template #columns>

                <a-table-column v-for="(i, index) in columns" :key="index" :title="i.title"
                    :data-index="i.dataIndex"></a-table-column>


                <a-table-column title="题目标签" data-index="tags">
                    <template #cell="{ record }">

                        <a-tag v-for="i in record.tags">{{ i }}</a-tag>

                    </template>
                </a-table-column>
                <a-table-column title="判题通过率" data-index="judgeCase">
                    <template #cell="{ record }">

                        <a-progress :percent="(record.acceptedNum / record.submitNum || 0).toFixed(2)"
                            :style="{ width: '50%' }" />
                    </template>
                </a-table-column>
                <a-table-column title="判题用例" data-index="judgeCase">
                    <template #cell="{ record }">
                        <div style="font-size: .6em;">

                            <a-trigger trigger="click">
                                <a-button style="font-size: 1em;">查看</a-button>
                                <template #content>
                                    <div class="demo-arrow" style="background: #2E2E30;
                                        width: 150px;
                                        box-shadow: .1px .1px .1px #888888;
                                        font-size: .6em;

                                        border-radius: 10px;
                                        border: 1px solid rgba(.1, 255, 255, 255);
                                        padding: 5px;
                                        ">
                                        <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;"
                                            v-for="i in record.judgeCase">{{ i.input }}->{{
                                                i.output
                                            }}</a-tag>
                                    </div>
                                </template>
                            </a-trigger>

                        </div>


                    </template>
                </a-table-column>

                <a-table-column title="判题规则" data-index="judgeConfig">
                    <template #cell="{ record }">
                        <!-- {{ record.judgeConfig }} -->
                        <div style="font-size: .6em;">

                            <a-trigger trigger="click">
                                <a-button style="font-size: 1em;">查看</a-button>
                                <template #content>
                                    <div class="demo-arrow" style="background: #2E2E30;
                                        width: 80px;
                                        box-shadow: .1px .1px .1px #888888;
                                        font-size: .6em;
                                        border-radius: 10px;
                                        border: 1px solid rgba(.1, 255, 255, 255);
                                        padding: 5px;
                                        display: flex;
                                        justify-content: center;
                                        align-items: center;
                                        flex-direction: column;
                                        ">
                                        <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;">

                                            时间：{{ record.judgeConfig.timeLimit }}
                                        </a-tag>
                                        <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;">

                                            内存：{{ record.judgeConfig.stackLimit }}
                                        </a-tag>
                                        <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;">

                                            堆栈：{{ record.judgeConfig.memoryLimit }}
                                        </a-tag>
                                    </div>
                                </template>
                            </a-trigger>

                        </div>


                    </template>
                </a-table-column>





                <a-table-column title="题目内容" data-index="content">
                    <template #cell="{ record }">

                        <a-typography-paragraph style="max-width:100px ; cursor: pointer;" underline :ellipsis="{
                            rows: 1,
                            showTooltip: true,
                        }">
                            {{ record.content }}
                        </a-typography-paragraph>
                    </template>
                </a-table-column>

                <a-table-column title="操作">
                    <template #cell="{ record }">
                        <a-button @click="updateQues(record)"
                            style="font-size: .6em; border-radius: 15px; border: 1px solid rgba(255,255,255,.3); margin-right: 1em; background: #303030;"
                            type="primary" icon="<icon-pen-fill />" status="success">修改</a-button>

                        <a-button @click="deletes(record)"
                            style="font-size: .6em; border-radius: 15px; margin-right: 1em; border: .5px solid rgba(255,255,255,.3);  background: #303030;"
                            type="primary" status="danger">删除</a-button>

                    </template>
                </a-table-column>
            </template>
        </a-table>
        <div style="display: flex;margin-top: 1em;">
            <a-pagination style="margin: auto; display: flex;" @change="pag" :total="page.total" :page="seach.current"
                :page-size="seach.pageSize" />
        </div>
    </div>

</template>

<script setup lang="ts">
import { ref, Ref, onMounted, watch } from 'vue';
import { Question, QuestionAddRequest, QuestionControllerService, QuestionQueryRequest } from '@/generated';
import { useRouter } from 'vue-router';
import { Modal, message } from 'ant-design-vue';
import { Dialog } from '@arco-design/web-vue';
const [modal, contextHolder] = Modal.useModal();
const router = useRouter()
const form = ref({
    title: '',
    tags: ['java'],
    "id": '',
});

const data = ref([])



const loading = ref(false)
const loadingSeach = async () => {
    loading.value = true
    seach.value.current = 1
    await List(form.value)
    loading.value = false




}



const pag = (current) => {

    if (loading.value) {
        return
    }

    seach.value.current = current
    List()
}



const columns = [
    {
        title: '题目编号',
        dataIndex: 'id',
    },
    {
        title: '题目标题',
        dataIndex: 'title',
    },



];

const seach = ref({
    // "answer": "",
    // "content": "",
    // "current": 0,
    "pageSize": 10,
    "sortField": "updateTime",
    "sortOrder": "descend",

    // "tags": ["java"],
    // "title": "",

    // "userId": 0,
    "current": 1
})
const page = ref({
    total: 0
})





const List = async (list = {}) => {



    let res = await QuestionControllerService.listQuestionByPageUsingPost(


        {
            ...list,


            ...seach.value
        }
    )





    if (res.code == 0) {
        if (res.data.total) {
            page.value.total = res.data.total


        }


        data.value = res.data.records.map(item => {

            if (item.tags) {
                item.tags = JSON.parse(item.tags)
                item.judgeCase = JSON.parse(item.judgeCase)
                item.judgeConfig = JSON.parse(item.judgeConfig)


            }



            return item
        })



    }
    else {
        //报错
        return false;
    }
    return true

}

const deletes = async (list) => {



    let res = await QuestionControllerService.deleteQuestionUsingPost({ id: list.id })

    if (res.code == 0) {
        await List()
        message.success("删除成功")


    }
    else {
        message.error("删除失败")
    }





}

const goupdateqv = () => {
    router.push({
        "path": `/vo/admin/update/none/false`
    })
}
const updateQues = (list) => {
    router.push({
        "path": `/vo/admin/update/${list.id}/true`
    })
}


onMounted(() => {
    List()

})



</script>

<style scoped></style>