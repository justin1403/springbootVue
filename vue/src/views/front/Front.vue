<script>
import {defineComponent} from 'vue'

export default defineComponent({
    name: "Front",

    data(){
        return{
            // user:{}
            // circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
            user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{}
        }
    },
    watch:{
        currentPathName (newVal, oldVal){
            console.log(newVal)
        }
    },
    methods:{
        logout(){
            this.$store.commit("logout")
            this.$message.success("退出成功")
        },

    }
})
</script>

<template>
<div>
<!--        標頭-->
    <div style="display: flex;height:61px;line-height: 61px;border-bottom: 1px solid #ccc">
        <div style="width:300px;display: flex;padding-left: 30px">
            <div style="width:60px">
            <img src="../../assets/NIKE.webp" alt="" style="width:40px;position: relative;top:5px;right: 0">
            </div>
            <div style="flex:1">歡迎來到小小系統</div>
        </div>
        <div style="flex:1">
<!--        <div>-->
<!--            導航菜單-->
<!--            <ul style="list-style: none; background-color: 	#98FB98 ; " >-->
<!--                <li class="item"><a href="/">菜單1</a></li>-->
<!--                <li class="item"><a href="/">菜單2</a></li>-->
<!--                <li class="item"><a href="/">菜單3</a></li>-->
<!--                <li class="item"><a href="/">菜單4</a></li>-->

<!--            </ul>-->


            <el-menu :default-active="1" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-menu-item index="1">处理中心</el-menu-item>
                <el-submenu index="2">
                    <template slot="title">我的工作台</template>
                    <el-menu-item index="2-1">选项1</el-menu-item>
                    <el-menu-item index="2-2">选项2</el-menu-item>
                    <el-menu-item index="2-3">选项3</el-menu-item>
                    <el-submenu index="2-4">
                        <template slot="title">选项4</template>
                        <el-menu-item index="2-4-1">选项1</el-menu-item>
                        <el-menu-item index="2-4-2">选项2</el-menu-item>
                        <el-menu-item index="2-4-3">选项3</el-menu-item>
                    </el-submenu>
                </el-submenu>
                <el-menu-item index="3" disabled>消息中心</el-menu-item>
                <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>
            </el-menu>
        </div>

        <div style="width: 200px">
            <div v-if="!user.username" style="text-align: center">
            <el-button @click="$router.push('/login')">登錄</el-button>
            <el-button @click="$router.push('/register')">註冊</el-button>
            </div>

            <div v-else>
                <el-dropdown style=" cursor:pointer">
                    <div style="display: inline-block">
                        <img :src="user.avatarUrl" alt=""  style="height: 50px;width:50px;border-radius: 50%;position: relative; right:10px;margin-bottom:3px;vertical-align:middle">
                        <span>{{user.nickname}} <i class="el-icon-arrow-down" style="margin-left: 5px"></i></span>
                    </div>
                    <el-dropdown-menu slot="dropdown" style="width: 100px; text-align:center">
                        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
                            <router-link to="/person">個人信息</router-link>
                        </el-dropdown-item>
                        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
                            <span style="text-decoration: none" @click="logout">退出</span>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>



    </div>

    <div style="width: 1000px;margin: 0 auto">

    <router-view/>
    </div>

</div>
</template>

<style scoped>
.item{
    display:inline-block;
    width: 100px;
    color: #4e13ea;
    text-align: center;
    cursor: pointer;
}

.item:hover{
    background-color: #cf99ee;

}

</style>