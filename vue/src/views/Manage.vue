<template>
        <el-container style="min-height: 100vh" >
            <el-aside :width="sideWidth +'px'" style="background-color: rgb(15,81,255)">
                <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"  />
            </el-aside>

            <el-container>
                <el-header style="border-bottom:1px solid #d51414">
                    <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user" />
                </el-header>

                <el-main style="padding-top: 15px">
                    <router-view @refreshUser="getUser" />

                </el-main>
            </el-container>
        </el-container>
</template>
<script>

import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

    export default {
      name:'Manage',
        data() {
            return {
                collapseBtnClass:'el-icon-s-fold',
                isCollapse:false,
                sideWidth:200,
                logoTextShow:true ,
                user:{}
            }  },
        components:{
          Aside,
          Header
        },
        created(){
          this.getUser()
        },
        methods:{
            collapse(){     // 點擊收縮按鈕觸發
                this.isCollapse = !this.isCollapse
                if(this.isCollapse){        // 收縮
                    this.sideWidth=64   // 收縮後尺寸
                    this.collapseBtnClass='el-icon-s-unfold'
                    this.logoTextShow=false
                }else {                     // 展開
                    this.sideWidth=200
                    this.collapseBtnClass='el-icon-s-fold'
                    this.logoTextShow=true
                }
            },

        getUser(){
                let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username :""
          this.request.get("/user/username/" +  username ).then(res=>{
              this.user = res.data
          })

        },
        }
    }
</script>

