import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import routers from "./router"
const routes: Array<RouteRecordRaw> = routers;

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});



export default router;
