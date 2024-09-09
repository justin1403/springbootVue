<template>
        <el-container style="min-height: 100vh" >
            <el-aside :width="sideWidth +'px'" style="background-color: rgb(15,81,255)">
                <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"  />
            </el-aside>

            <el-container>
                <el-header style="border-bottom:1px solid #d51414">
<!--                   傳入 user 對象到 Header 組件裡面 -->
                    <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user" />
                </el-header>

                <el-main style="padding-top: 15px">
<!--                        表示當前頁面的子路牌會在 <router-view /> 裡面展示-->
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
                logoTextShow:true ,  // false 只顯示 logo
                // user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{},
                user:{}
            }  },
        components:{
          Aside,
          Header
        },
        created(){
          // 從後台獲取最新的 User 數據
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

            // 獲取用戶的最新數據
        getUser(){

                let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username :""
                // 從後台獲取數據
          this.request.get("/user/username/" +  username ).then(res=>{
              // 重新賦值後台的最新 User 數據
              this.user = res.data
          })

        },






        }




    }
</script>

