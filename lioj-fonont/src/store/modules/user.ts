
import { UserControllerService } from "@/generated";

export default {
    state: () => ({
        user: {
            "userName": "未登录",
            "userRole": "notLogin",
        }
    }),
    mutations: {
        // @ts-ignore
        increment(state, user) {
            // 变更状态
            state.user = user
        }
    },
    actions: {
        // @ts-ignore
        async increment(context) {



            const loginuser = await UserControllerService.getLoginUserUsingGet()
            console.log("登录", loginuser);


            if (loginuser.code == 0) {

                context.commit('increment', loginuser.data)
            }
            else {
                context.commit('increment', {
                    "userName": "未登录",
                    "userRole": "notLogin",
                })
            }



        }

    }
} 