// import Vue from 'vue'
// // , {nextTick}
// import VueRouter from 'vue-router'
// import store from "@/store";
//
// Vue.use(VueRouter)
//
// const routes = [
//
//     {
//         path:'/',
//         name:'Manage',
//         component:()=>import('@/views/Manage.vue'),
//         redirect:'/home',
//
//         children:[
//             {path:'home',name:'首頁' ,component:()=>import('@/views/Home.vue')},
//             // {path:'course',name:'課程管理',component:()=>import('@/views/Course.vue')},
//             {path:'user',name:'用戶管理',component:()=>import('@/views/User.vue')},
//             {path:'role',name:'角色管理',component:()=>import('@/views/Role.vue')},
//             {path:'menu',name:'菜單管理',component:()=>import('@/views/Menu.vue')},
//             {path:'person',name:'個人信息',component:()=>import('@/views/Person.vue')},
//             {path:'file',name:'文件管理',component:()=>import('@/views/File.vue')},
//             {path:'course',name:'課程管理',component:()=>import('@/views/Course.vue')},
//             // path:'/',name:'Manage',component:()=>import('../views/Manage.vue'),
//         ]
//     },
//     {
//         path: '/login',
//         name: 'Login',
//         component: ()=>import('@/views/Login.vue')
//     },
//     {
//         path: '/register',
//         name: 'Register',
//         component: ()=>import('@/views/Register.vue')
//     },
//     {
//         path: '/404',
//         name: '404',
//         component: ()=>import('@/views/404.vue')
//     },
//
//
//
//
//     // {
//     //     path: '/about',
//     //     name: 'about',
//     //     // route level code-splitting
//     //     // this generates a separate chunk (about.[hash].js) for this route
//     //     // which is lazy-loaded when the route is visited.
//     //     component: () => import(/* webpackChunkName: "about" */ '@/views/AboutView.vue')
//     // }
// ]
//
// const router = new VueRouter({
//     mode: 'history',
//     base: process.env.BASE_URL,
//     routes
// })
//
// // 提供一個重置路由方法
// export const resetRouter = () => {
//     router.matcher = new VueRouter({
//         mode: 'history',
//         base: process.env.BASE_URL,
//         routes
//     })
// }
//
//
//
// export const setRoutes = () =>{
//     const storeMenus = localStorage.getItem("menus");
//     if(storeMenus){
//         // 獲取當前的路由對象名稱數組
//         const currentRouteNames = router.getRoutes().map(v=>v.name)
//         if (!currentRouteNames.includes('Manage')){
//         // 拼裝動態路由
//         const manageRoute = { path: '/',name:'Manage',component:()=>import('../views/Manage.vue'),redirect:"/home",children:[
//                 {path:'person',name:'個人信息',component:()=>import('@/views/Person.vue')},
//                 {path:'password',name:'修改密碼',component:()=>import('@/views/Password.vue')},
//             ]}
//         const menus = JSON.parse(storeMenus)
//         menus.forEach(item => {
//             if(item.path){  // 當且權當 path 不為空的時候才去設置路由
//                 let itemMenu = {path:item.path.replace("/",""),name:item.name,component:()=>import('../views/'+item.pagePath+'.vue')}
//                 manageRoute.children.push(itemMenu)
//
//             } else if(item.children.length) {
//                 item.children.forEach(item => {
//                     if (item.path) {
//                         let itemMenu = { path: item.path.replace("/", ""),name:item.name,component: () => import('../views/' + item.pagePath + '.vue')}
//                         manageRoute.children.push(itemMenu)
//                     }
//                 })
//             }
//         })
//
//
//         // 動態添加到現在的路由對象中去
//         router.addRoute(manageRoute)
//     }
//     }
// }
//
//
// // 解决 Vue 重複點連相同路由
// // const originalPush = VueRouter.prototype.push;
// // VueRouter.prototype.push = function push(location){
// //     return originalPush.call(this,location).catch((error)=>error);
// // }
//
// // 重置 我就再 set 一次路由
// setRoutes()
//
//
// //路由守衛
// router.beforeEach((to,from,next)=>{
//     localStorage.setItem("currentPathName",to.name)   // 設置當前的路由名稱，為了在 Header 組件中去使用
//     store.commit("setPath")   // 觸發 store 的數據更新
//
//
//     // 未找到路由的情況
//     if(!to.matched.length){
//         const storeMenus = localStorage.getItem("menus")
//         if(storeMenus){
//             next("/404")
//         }else {
//             // 跳回登錄頁面
//             next("/login")
//         }
//     }
//     // 其他的情況都放行
//     next()  //放行路由
//
// })
//
//
// export default router
