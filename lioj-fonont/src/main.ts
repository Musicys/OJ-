import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ArcoVue from "@arco-design/web-vue";
import "@arco-design/web-vue/dist/arco.css";
import "@/assets/css/index.css"
import 'bytemd/dist/index.css'
//路由权限挂载
import "@/access"



// 额外引入图标库
import ArcoVueIcon from '@arco-design/web-vue/es/icon';

import '@arco-design/web-vue/dist/arco.css';
import { message } from "ant-design-vue";
message.config({
    top: `100px`,
    duration: 1,
    maxCount: 3,
    rtl: true,
    prefixCls: 'my-message',
});


//全局拦截器
import "@/util/axios"

const app = createApp(App);
app.use(store).use(router).use(ArcoVue);

app.use(ArcoVue);
app.use(ArcoVueIcon);
app.mount("#app");
