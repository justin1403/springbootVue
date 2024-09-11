<script>
    import {defineComponent} from 'vue'

    export default defineComponent({
        name: "Header",
        props:{
            user:Object,
            collapseBtnClass:String,
            collapse: ''
        },
        computed:{
            currentPathName(){
                return this.$store.state.currentPathName;
            }
        },
        data(){
          return{
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
<div style="font-size: 12px;line-height: 60px;display: flex">
    <div style="flex:1;font-size: 20px">
        <span :class="collapseBtnClass" style="cursor: pointer;font-size:18px" @click="collapse" ></span>
            <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px" >
                <el-breadcrumb-item :to=" '/' ">首頁</el-breadcrumb-item>
                <el-breadcrumb-item :v-model="currentPathName" > {{currentPathName}} </el-breadcrumb-item>
            </el-breadcrumb>
    </div>
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