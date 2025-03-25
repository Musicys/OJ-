<template>
    <div class="login-container">
        <a-link class="back-link" href="/">返回主页</a-link>
        <div class="login-form">
            <div style="text-align: center; margin-bottom: 24px;">
                <img src="https://tse3-mm.cn.bing.net/th/id/OIP-C.5NSM0Kb1mSR3J6K31nHiIgAAAA?rs=1&pid=ImgDetMain"
                    alt="logo" style="width: 80px; height: 80px; border-radius: 50%;" />
            </div>
            <a-form :model="form" :style="{ width: '400px' }" @submit="handleSubmit">
                <a-form-item field="name" label="账号">
                    <a-input v-model="form.userAccount" placeholder="请输入账号" />
                </a-form-item>
                <a-form-item field="post" label="密码">
                    <a-input-password v-model="form.userPassword" placeholder="请输入密码" />
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" html-type="submit" class="login-btn">登录</a-button>
                    <a-link @click="toRegister" style="margin-left: 25px; width: 2em; white-space: nowrap;">注册</a-link>
                </a-form-item>
            </a-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue"
import { UserControllerService } from "@/generated"
import store from "@/store";
import { message } from "ant-design-vue"
import { useRouter } from "vue-router";
const router = useRouter()
const form = reactive({
    "userAccount": "",
    "userPassword": ""
});
const toLogin = () => {
    router.push('/');
};

const toRegister = () => {
    router.push('/user/createUser');
};

const handleSubmit = async (data: any) => {
    let res = await UserControllerService.userLoginUsingPost(form)
    console.log(res.code);
    if (res.code == 0) {
        await store.dispatch('increment')
        message.success("登录成功")
        router.push({
            path: "/"
        })
    }
    else {
        message.error("密码错误")
    }
};
</script>

<style scoped>
.back-link {
    position: absolute;
    top: 20px;
    right: 60px;
    font-size: 14px;
    color: #1890ff;
    cursor: pointer;
}

.login-container {
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #1A1A1A;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 122;
}

.login-form {
    background: #272F2F;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    animation: fadeIn 0.5s ease;
}

.login-btn {
    width: 100%;
    height: 40px;
    font-size: 16px;
    transition: all 0.3s ease;
}

.login-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>