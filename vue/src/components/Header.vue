<script>
    import {defineComponent} from 'vue'

    export default defineComponent({
        name: "Header",
        props:{
            user:Object,        // 定義一個 user 屬性接受從 Manage.vue 傳進來的 user 對象
            collapseBtnClass:String,
            collapse: ''        // 收縮沒反應，在此修改
        },
        computed:{
            currentPathName(){
                return this.$store.state.currentPathName;       // 需要監聽的數據
            }
        },
        data(){
          return{
              // circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
              // user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{}
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
<!--{{currentPathName}}-->
<template>
<div style="font-size: 12px;line-height: 60px;display: flex">
    <div style="flex:1;font-size: 20px">
        <span :class="collapseBtnClass" style="cursor: pointer;font-size:18px" @click="collapse" ></span>
            <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px" >
                <el-breadcrumb-item :to=" '/' ">首頁</el-breadcrumb-item>
                <el-breadcrumb-item :v-model="currentPathName" > {{currentPathName}} </el-breadcrumb-item>
            </el-breadcrumb>
    </div>
<!--    {{user.nickname}} -->

<!--            <el-avatar :size="48" :src="circleUrl" style="margin : 5px" ></el-avatar>-->

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
</template>

<style scoped>

</style>