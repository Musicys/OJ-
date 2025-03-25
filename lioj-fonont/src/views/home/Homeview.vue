<template>
    <div class="mine">
        <!-- sach -->
        <div style="display: flex;">
            <a-form :model="form" style="display: flex; flex-wrap: wrap; margin:1em auto; max-width: 1000px;">

                <a-row :gutter="32" style="width: 100%;display: flex;">
                    <a-col :span="7">


                        <a-input v-model="form.id" placeholder="输入题目编号" />

                    </a-col>
                    <a-col :span="7">

                        <a-input v-model="form.title" placeholder="输入题目标题" />


                    </a-col>
                    <a-col :span="7">

                        <a-input-tag v-model="form.tags" placeholder="题目标签 输入后回车" />


                    </a-col>
                    <a-col :span="3">
                        <a-button style="width: 120px; background: #3A3A3A;" type="primary" :loading="loading"
                            @click="loadingSeach">搜索</a-button>

                    </a-col>
                </a-row>



            </a-form>

        </div>
        <a-table :stripe="true" :data="data" :pagination="false"
            style="margin-top: 30px;max-width: 1000px; margin: auto;">
            <template #columns>

                <a-table-column v-for="(i, index) in columns" :key="index" :title="i.title"
                    :data-index="i.dataIndex"></a-table-column>


                <a-table-column title="题目标签" data-index="tags">
                    <template #cell="{ record }">

                        <a-tag color="green" style="margin-left: .2em;" v-for="i in record.tags">{{ i }}</a-tag>

                    </template>
                </a-table-column>

                <a-table-column title="题目通过率" data-index="tags">
                    <template #cell="{ record }">

                        <a-progress :percent="(record.acceptedNum / record.submitNum || 0).toFixed(2)"
                            :style="{ width: '50%' }" />

                    </template>
                </a-table-column>
                <a-table-column title="操作">
                    <template #cell="{ record }">
                        <a-button @click="startpoc(record.id)">开始</a-button>
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
const routers = useRouter()
const form = ref({
    title: '',
    tags: [],
    "id": '',
});


const startpoc = (id) => {
    console.log(id);

    routers.push({
        path: `sumbitQv/${id}`
    })
}
const data = ref([])

const loading = ref(false)
const loadingSeach = async () => {
    loading.value = true
    seach.value.current = 1
    await List(form.value)
    loading.value = false




}



const pag = async (current) => {

    if (loading.value) {
        return
    }
    // alert("123")
    seach.value.current = current
    await List()
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
    "pageSize": 20,
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



    let res = await QuestionControllerService.listQuestionVoByPageUsingPost(


        {
            ...list,


            ...seach.value
        }
    )





    if (res.code == 0) {
        if (res.data.total) {
            page.value.total = res.data.total


        }


        data.value = res.data.records


    }
    else {
        //报错
        return false;
    }
    return true

}




onMounted(() => {
    List()

})



</script>

<style scoped></style>