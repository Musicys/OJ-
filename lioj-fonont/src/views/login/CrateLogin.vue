<template>
    <div class="register-container">
        <div class="register-form">
            <a-link class="back-link" @click="toLogin">返回登录</a-link>
            <icon-double-left />

            <a-form :model="form" :style="{ width: '400px' }" @submit="handleSubmit">



                <a-form-item field="email" label="用户名称">
                    <a-input v-model="form.userName" placeholder="请输入名称" />
                </a-form-item>
                <a-form-item field="email" label="邮箱">
                    <a-input v-model="form.qqEmail" placeholder="请输入邮箱" />
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" @click="sendVerificationCode" :disabled="isCodeSending">{{ codeButtonText
                        }}</a-button>
                </a-form-item>
                <a-form-item field="account" label="账号">
                    <a-input v-model="form.userAccount" placeholder="请输入账号" />
                </a-form-item>
                <a-form-item field="password" label="密码">
                    <a-input-password v-model="form.userPassword" placeholder="请输入密码" />
                </a-form-item>
                <a-form-item field="password" label="确认密码">
                    <a-input-password v-model="form.checkPassword" placeholder="确认密码" />
                </a-form-item>
                <a-form-item field="password" label="验证码">
                    <a-input v-model="form.verificationCode" placeholder="请输入验证码" />
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" html-type="submit" class="register-btn">注册</a-button>
                </a-form-item>
            </a-form>



        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import Upload from '@/components/Upload.vue';
import { UserControllerService, FileControllerService } from "@/generated"
import { message } from 'ant-design-vue';
const router = useRouter();
const form = ref({
    qqEmail: "",
    userAccount: "",
    userPassword: "",
    checkPassword: "",
    verificationCode: "",
    userName: ""
})
const toLogin = () => {
    router.push('/user/login');
};

const codeButtonText = ref("发送验证码")
const isCodeSending = ref(false)



// let LoginImg = null;


// const setLoginImg = (e) => {
//     LoginImg = e;


// }


// const postFile = async () => {


//     let res = await FileControllerService.uploadFileUsingPost('user_avatar', LoginImg.file)

//     console.log(res);


// }





const sendVerificationCode = async () => {
    if (!form.value.qqEmail) {
        message.error("请输入邮箱")
        return
    }

    isCodeSending.value = true
    codeButtonText.value = "发送中"


    let res = await UserControllerService.userSetVerificationCodeUsingPost({ qqEmail: form.value.qqEmail })
    if (res.code == 0) {
        // message.success(res.data)
        console.log(res);
        message.success("发送成功")

    }

    codeButtonText.value = "60秒后重试"

    let count = 60
    const timer = setInterval(() => {
        count--
        codeButtonText.value = `${count}秒后重试`
        if (count === 0) {
            clearInterval(timer)
            isCodeSending.value = false
            codeButtonText.value = "发送验证码"
        }
    }, 1000)
}

const handleSubmit = async () => {
    if (form.value.userPassword !== form.value.checkPassword) {
        message.error("两次输入的密码不一致")
        return
    }

    let res = await UserControllerService.userRegisterUsingPost({
        ...form.value
    })
    if (res.code == 0) {
        message.success("注册成功")







        router.push({
            path: `/user/login?username=${form.userAccount}`
        })
    }
    else {
        message.error(res.message)
    }
}
</script>

<style scoped>
.register-container {
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

.register-logo {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-bottom: 20px;
}

.back-icon {
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 24px;
    cursor: pointer;
}

.back-link {
    position: absolute;
    top: 20px;
    right: 60px;
    font-size: 14px;
    color: #1890ff;
    cursor: pointer;
}

.register-form {
    background: #272F2F;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    animation: fadeIn 0.5s ease;
}

.register-btn {
    width: 100%;
    height: 40px;
    font-size: 16px;
    transition: all 0.3s ease;
}

.register-btn:hover {
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