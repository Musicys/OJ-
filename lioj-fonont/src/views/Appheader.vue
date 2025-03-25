<template>
    <div class="menu-demo">


        <a-menu mode="horizontal" hover="none" style="height: 80px; " :selected-keys="[`${Route.path}`]"
            expand-icon-right>
            <a-menu-item>
                <div class="logo" style="user-select: noen;">
                    <img style="width: 35px;height: 35px;" src="../assets/logo.svg" alt="">
                </div>
            </a-menu-item>

            <a-menu-item v-for="(i, index) in RouteList" :key="i.path" @click="logs(i.path)">{{ i.name }}</a-menu-item>

        </a-menu>
        <a-space size="large" style="margin-right: 2em;">



            <a-dropdown>


                <a-avatar style="cursor: pointer;">
                    <img v-if="store.state.user.user.userRole == `notLogin`" src="../assets/user.jpg" alt="avatar">
                    <img v-else alt="avatar" :src="store.state.user.user.userAvatar" />
                </a-avatar>


                <template #content>
                    <a-doption @click="goLoginAndEexitLogin">

                        <a-badge :count="9" color="#00B42A" :dot="true">

                        </a-badge>
                        {{ store.state.user.user.userRole == "notLogin" ? "登录界面" :
                            "注销" }}</a-doption>
                    <a-doption @click="logs(`/messge/${store.state.user.user.id}`)"
                        v-if="store.state.user.user.userRole != `notLogin`">
                        <a-badge :count="9" color="#00B42A" :dot="true">

                        </a-badge>
                        用户信息</a-doption>

                    <a-doption style="display: flex; align-items: center;" @click="logs(`/reply`)"
                        v-if="store.state.user.user.userRole != `notLogin`">

                        <a-badge :count="9" :dot="true">

                        </a-badge>
                        查看回复
                    </a-doption>

                </template>
            </a-dropdown>


        </a-space>
    </div>
</template>

<script lang="ts" setup>
import Routers from "@/router/router"
import { ref, watch, onMounted } from "vue"
import { useRoute, useRouter } from 'vue-router'
import { ACCESS_ENUM } from "@/access/config"
import { useStore } from 'vuex'
import { message } from "ant-design-vue"
import { UserControllerService } from "@/generated"
const Route = useRoute()
const Router = useRouter()
const RouteList = ref(Routers)


const goLoginAndEexitLogin = async () => {
    if (store.state.user.user.userRole == ACCESS_ENUM.NO_LOGIN) {
        Router.push({
            path: "/user/login"
        })
    }
    else {

        let res = await UserControllerService.userLogoutUsingPost()
        if (res.code == 0) {
            message.success("退出成功")
            Router.push({
                path: "/user/login"
            })
        }
        else {
            message.error("退出失败")
        }


    }
}
const store = useStore();

const updatelist = () => {

    RouteList.value = Routers.filter((item) => {

        // @ts-ignore


        let boolle = item.meta?.hideInMenu === undefined



        // if(store.state.user.user)
        if (store.state.user.user.userRole == ACCESS_ENUM.ADMIN && boolle) {


            return true
        }





        // @ts-ignore
        return item.meta?.assess !== ACCESS_ENUM.ADMIN && boolle
    })

}
updatelist()




watch(() => store.state.user.user, (newValue, oldValue) => {

    updatelist()





})



const logs = (str: string) => {

    Router.push({
        path: str
    })
}




onMounted(() => {
    console.log("用户暑假", store.state.user.user.userName);

})

</script>
<style scoped>
.menu-demo {
    box-sizing: border-box;
    width: 100%;
    height: 100px;

    display: flex;

    justify-content: center;
    align-items: center;

    --arcoblue-6: 60, 126, 0;
    background-color: var(--color-menu-light-bg);
}
</style>