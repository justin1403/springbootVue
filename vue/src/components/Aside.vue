<script>
    import {defineComponent} from 'vue'
    import home from "@/views/Home.vue";

    export default defineComponent({
        name: "Aside",
        computed: {
            home() {
                return home
            }
        },
        props:{
            isCollapse:Boolean,
            logoTextShow:Boolean,
        },
        data(){
            return{
                menus:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")) : [],
                opens:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : [],
            }
        },
    })
</script>

<template>
    <el-menu :default-openeds="opens" style="min-height: 100%;overflow: hidden"
             background-color="rgb(48,65,86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition=false
             :collapse=isCollapse
             router
    >
        <div style="heigh:60px ;line-height:60px;text-align:center">
            <img src="../assets/logo.png" alt="" style="width:20px;position:relative;top: 5px">
            <b style="color:white" v-show="logoTextShow"> 後檯管理系統</b>
        </div>
        <div v-for="item in menus" :key="item.id">
            <div v-if="item.path">
                <el-menu-item :index="item.path"> <i :class="item.icon"></i>
        <!--            <template slot="title">-->
<!--                        <span  slot="title" >主頁</span>-->
                        <span  slot="title" >{{item.name}}</span>
        <!--            </template>-->
                </el-menu-item>

            </div>
            <div v-else>
                <el-submenu :index="item.id + ''">
                    <template slot="title">
                        <i :class="item.icon"></i>
<!--                          收縮隱藏"系統管理"-->
                        <span slot="title" v-show="logoTextShow">{{item.name}}</span>
                    </template>
                    <div v-for="subItem in item.children" :key="subItem.id">
                        <el-menu-item :index="subItem.path"> <i :class="subItem.icon"></i>
                            <span  slot="title" >{{subItem.name}}</span>
                        </el-menu-item>
                    </div>
                </el-submenu>
            </div>
        </div>
    </el-menu>
</template>

<style scoped>

</style>