import { RouteRecordRaw } from "vue-router";
import Homeview from "@/views/home/Homeview.vue";
import Loginview from "@/views/login/Loginview.vue";
import Mineview from "@/views/postquistionSumbit/QuistionSumbit.vue";
import Adminview from "@/views/admin/Adminview.vue";
import { ACCESS_ENUM } from "@/access/config";
import NoAuthView from "@/views/NoAuthView.vue";
import UpdateQEview from "@/views/admin/UpdateQEview.vue";
import Potopic from "@/views/topic/Potopic.vue";
import CreateUser from "@/views/login/CrateLogin.vue";
import Usermessge from "@/views/usermessge/Usermessge.vue";
import Reply from "@/views/reply/reply.vue";
import MineSumbit from "@/views/mineSumbit/MineSumbit.vue";
export default [
    {
        name: "首页",
        path: '/',

        children: [
            {
                name: "主页",
                path: '',
                component: Homeview,
            },
            {


                name: "登录",
                path: 'user/login',
                component: Loginview,
                // redirect: "/user/login"
            }
        ]
    }


    ,

    {
        name: "管理员",
        path: '/ascss',
        component: Adminview,
        meta: {
            //  
            assess: ACCESS_ENUM.ADMIN

        }
    }
    ,

    {
        name: "个人提交记录",
        path: '/minesumit',
        component: MineSumbit,
        meta: {
            //  
            assess: ACCESS_ENUM.USER

        }
    }
    ,
    {
        name: "用户信息",
        path: '/messge/:id',
        component: Usermessge,
        props: true,
        meta: {
            //  
            hideInMenu: true,

        }
    }
    ,
    {
        name: "回复板",
        path: '/reply',
        component: Reply,
        meta: {
            //  
            hideInMenu: true,

        }
    }
    ,



    {
        name: "管理题目",
        path: "/vo/admin/update/:id/:boller",
        props: true,
        component: UpdateQEview,
        meta: {
            //不展示
            hideInMenu: true,
        },
    },

    {
        name: "提交题目记录",
        path: '/qutionsbmit',
        component: Mineview,
        meta: {
            //  
            assess: ACCESS_ENUM.ADMIN

        }
    }
    ,
    {
        name: "开始答题",
        path: '/sumbitQv/:id',
        component: Potopic,
        props: true,
        meta: {
            //不展示
            hideInMenu: true,
            assess: ACCESS_ENUM.USER
        },
    }
    ,

    {
        name: "注册",
        path: "/user/createUser",
        component: CreateUser,
        meta: {
            hideInMenu: true,
        },
    }
    ,


    {
        name: "404",
        path: "/:pathMatch(.*)*",
        component: NoAuthView,
        meta: {
            //不展示
            hideInMenu: true,
        },
    }






] as Array<RouteRecordRaw>