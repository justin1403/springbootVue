<script xmlns:background-color="http://www.w3.org/1999/xhtml" xmlns:background-color="http://www.w3.org/1999/xhtml">
    import {defineComponent} from 'vue'
    import {setRoutes} from "@/router";

    export default defineComponent({
        name: "Login",
        data(){
            return{
                user:{},
                rules: {
                    username: [
                        {required: true, message: '請輸入用戶名', trigger: 'blur'},
                        {min: 3, max: 5, message: '長度在 3 到 5 個字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '請輸入密碼', trigger: 'blur'},
                        {min: 1, max: 20, message: '長度在 1 到 20 個字符', trigger: 'blur'}
                    ],
                },
            }
        },

        methods:{
            login(){
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {
                        this.request.post("/user/login",this.user).then(res=> {
                            if (res.code === '200') {
                                localStorage.setItem("user",JSON.stringify(res.data))
                                localStorage.setItem("menus",JSON.stringify(res.data.menus))
                                setRoutes()
                                this.$message.success("登入成功")
                                if(res.data.role === 'ROLE_STUDENT'){
                                  this.$router.push("/front/home")
                                }else{
                                  this.$router.push("/")
                                }
                            } else {
                                this.$message.error(res.msg);
                            }
                        })
                    }});
            },
        }
    })
</script>

<template>
  <div class="wrapper">
      <div style="margin:100px auto ; background-color:#fff;width:350px;height:260px;padding:20px;border-radius:15px">
          <div style="margin: 10px 0 ;text-align:center;font-size:24px"><b>登 錄</b></div>
          <el-form :model="user" :rules="rules" ref="userForm">
                  <el-form-item prop="username">
          <el-input  prop="username" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                  </el-form-item>
                  <el-form-item prop="password">
          <el-input  size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password" @keyup.enter.native="login"></el-input>
                  </el-form-item>
          <div style="margin: 10px 0 ; text-align:right">
              <el-button type="warning" size="small" autocomplete="off" @click= "$router.push('/register')">註冊</el-button>
              <el-button type="primary" size="small" autocomplete="off" @click= 'login' >登錄</el-button>
          </div>
          </el-form>
      </div>
  </div>
</template>

<style scoped>
  .wrapper{
      height: 100vh;
      background-image: linear-gradient(to bottom right, #ee99aa, #7084e7);
      overflow: hidden;
  }
</style>