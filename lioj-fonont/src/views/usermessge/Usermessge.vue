<template>
    <div v-if="IsMyMessge" class="messge">

        <!-- 编辑 -->

        <div class="cart">
            <div class="cart-left">
                <img :src="store.state.user.user.userAvatar" alt="">
            </div>
            <div class="cart-right">

                <div class="tilte">
                    {{ store.state.user.user.userName }}
                </div>
                <div class="desc">
                    {{ store.state.user.user.userProfile }}
                </div>
                <div class="tut">
                    <a-button>编辑</a-button>
                </div>
            </div>

        </div>



    </div>
    <div v-else class="xinx">
        <!--展示 -->

    </div>
</template>

<script setup lang="ts">
import { useStore } from 'vuex';
import { ref, Ref, onMounted, watch } from 'vue';
import { Question, QuestionAddRequest, QuestionControllerService, QuestionQueryRequest } from '@/generated';
import { useRouter } from 'vue-router';
const store = useStore()
const IsMyMessge = ref(false)
const pops = defineProps(["id"])


const Initial = () => {

    if (pops.id == store.state.user.user.id) {
        IsMyMessge.value = true
    }
    console.log(IsMyMessge.value);

}
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
        title: '提交编号',
        dataIndex: 'questionId',
    },
    {
        title: '题目语言',
        dataIndex: 'language',
    },





];

const seach = ref({
    // "answer": "",
    // "content": "",
    // "current": 0,
    "pageSize": 10,
    // "sortField": "",
    // "sortOrder": "",

    // "tags": ["java"],
    // "title": "",

    // "userId": 0,
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
        console.log(data.value);



    }
    else {
        //报错
        return false;
    }
    return true

}




onMounted(() => {
    List()
    Initial()
})


</script>

<style scoped>
.messge {
    width: 65%;
    margin: auto;
    color: white;
}

.cart {
    width: 100%;
    margin: 1em auto;
    height: 250px;
    display: flex;
    padding: 1em;
    background: #383F3F;
    border: .1px solid rgba(255, 255, 255, .6);
    border-radius: 15px;
}

.cart-left>img {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    border: 10px solid#3DB7FD;

}

.cart-right {
    margin-left: 1em;
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: start;
    flex-direction: column;



    .tilte {
        margin-top: 1.2em;
        font-size: 1.5em;
    }

    .desc {
        margin-top: 2em;
        min-height: 100px;
        width: 90%;
        font-size: 1.2em;
    }

    .tut {

        display: flex;
        justify-content: end;
        align-items: center;
        margin-top: 2em;
    }
}

.cart-right>div {
    width: 95%;
}
</style>
