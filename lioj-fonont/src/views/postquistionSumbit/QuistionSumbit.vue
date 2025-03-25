<template>
    <div class="mine">
        <!-- sach -->
        <div style="display: flex; margin-bottom: 1em;">
            <a-form :model="form" style="display: flex; flex-wrap: wrap; margin: auto; max-width: 1200px;">

                <a-row :gutter="32" style="width: 100%;display: flex;">

                    <a-col :span="6">

                        <a-input v-model="form.questionId" placeholder="输入题目编号" />


                    </a-col>



                    <a-col :span="6">
                        <a-input v-model="form.language" placeholder="输入题目语言" />



                    </a-col>





                    <a-col :span="4">
                        <a-button type="primary" :loading="loading" @click="loadingSeach">搜索</a-button>


                    </a-col>
                </a-row>



            </a-form>

        </div>
        <a-table :data="data" :pagination="false" style="margin-top: 30px;max-width: 1200px; margin: auto;">
            <template #columns>

                <a-table-column v-for="(i, index) in columns" :key="index" :title="i.title"
                    :data-index="i.dataIndex"></a-table-column>


                <a-table-column title="用户名称" data-index="judgeCase">
                    <template #cell="{ record }">
                        <div style="font-size: .8em;">

                            {{ record.usercoment.userName }}

                            <a-tag>
                                {{ record.usercoment.userRole }}
                            </a-tag>
                        </div>


                    </template>
                </a-table-column>








                <a-table-column title="耗时信息" data-index="judgeConfig">
                    <template #cell="{ record }">
                        <!-- {{ record.judgeConfig }} -->
                        <div style="font-size: .6em;">

                            <a-trigger trigger="click">
                                <a-button style="font-size: 1em;">查看</a-button>
                                <template #content>
                                    <div class="demo-arrow" style="background: #1A1A1A;
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

                                            内存：{{ record.judgeInfo.memory
                                            }}
                                        </a-tag>


                                        <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;">

                                            时间：{{ record.judgeInfo.time }}
                                        </a-tag>
                                    </div>
                                </template>
                            </a-trigger>

                        </div>


                    </template>
                </a-table-column>





                <a-table-column title="提交内容" data-index="content">
                    <template #cell="{ record }">

                        <a-typography-paragraph style="max-width:100px ; cursor: pointer;" underline :ellipsis="{
                            rows: 1,
                            showTooltip: true,
                        }">
                            {{ record.code }}
                        </a-typography-paragraph>
                    </template>
                </a-table-column>

                <a-table-column title="执行成果" data-index="judgeCase">
                    <template #cell="{ record }">
                        <div style="font-size: .8em;">

                            <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;">

                                {{ record.judgeInfo.message }}
                            </a-tag>

                        </div>


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

const router = useRouter()
const form = ref({
    language: null,
    questionId: null
    // status

});

const qsle = {
    "0": "等待中",
    "1": "判题中",
    "2": "成功",
    "3": "失败"
}


/**   language
    questionId
    updateTime
    status
     */
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
        dataIndex: 'questionId',
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


    "current": 1
})
const page = ref({
    total: 0
})





const List = async (list = {}) => {



    let res = await QuestionControllerService.listQuestionSubmitByPageUsingPost(


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



    }
    else {
        alert("删除失败")
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