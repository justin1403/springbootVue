<script xmlns:background-color="http://www.w3.org/1999/xhtml" xmlns:background-color="http://www.w3.org/1999/xhtml">
    import {defineComponent} from 'vue'

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
                        {min: 5, max: 20, message: '長度在 5 到 20 個字符', trigger: 'blur'}
                    ],
                    confirmpassword: [
                        {required: true, message: '請輸入密碼', trigger: 'blur'},
                        {min: 5, max: 20, message: '長度在 5 到 20 個字符', trigger: 'blur'}
                    ],

                },
            }
        },

        methods:{
            login(){
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {
                        if(this.user.password!==this.user.confirmpassword){
                            this.$message.error("再次輸入密碼不一致")
                            return false
                        }
                        this.request.post("/user/register",this.user).then(res=> {
                            if (res.code === '200') {
                                this.$message.success("註冊成功")
                            } else {
                                this.$message.error(res.msg);
                            }
                        })
                    } else {
                        return false;
                    }});

            },
            resetForm(userForm) {
                this.$refs[userForm].resetFields();
            }

        }


    })
</script>

<template>
  <div class="wrapper">
      <div style="margin:50px auto ; background-color:#fff;width:350px;height:500px;padding:20px;border-radius:15px">
          <div style="margin: 5px 0 ;text-align:center;font-size:24px"><b>註 冊</b></div>
          <el-form :model="user" :rules="rules" ref="userForm">
                  <el-form-item prop="username">
          <el-input placeholder="請輸入帳號" prop="username" size="medium" style="margin: 1px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                  </el-form-item>
                  <el-form-item prop="nickname">
          <el-input  placeholder="請輸入暱稱" size="medium" style="margin: 1px 0" prefix-icon="el-icon-lock"  v-model="user.nickname"></el-input>
                  </el-form-item>
                  <el-form-item prop="email">
          <el-input  placeholder="請輸入信箱" size="medium" style="margin: 1px 0" prefix-icon="el-icon-lock" v-model="user.email"></el-input>
                  </el-form-item>
                  <el-form-item prop="phone">
          <el-input  placeholder="請輸入電話" size="medium" style="margin: 1px 0" prefix-icon="el-icon-lock" v-model="user.phone"></el-input>
                  </el-form-item>
                  <el-form-item prop="address">
          <el-input  placeholder="請輸入地址" size="medium" style="margin: 1px 0" prefix-icon="el-icon-lock"  v-model="user.address"></el-input>
                  </el-form-item>
                  <el-form-item prop="password">
          <el-input  placeholder="請輸入密碼" size="medium" style="margin: 1px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                  </el-form-item>
                  <el-form-item prop="confirmpassword">
          <el-input  placeholder="請確認密碼" size="medium" style="margin: 1px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmpassword"></el-input>
                  </el-form-item>
          <div style="margin: 2px 0 ; text-align:right">
              <el-button type="primary" size="small" autocomplete="off" @click= 'login' >註冊</el-button>
              <el-button type="warning" size="small" autocomplete="off" @click= "$router.push('/login')">返回登入</el-button>
<!--              <el-button @click="resetForm('userForm')">重置</el-button>-->
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