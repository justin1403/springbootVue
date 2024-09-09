<script>
import {defineComponent} from 'vue'

export default defineComponent({
    name: "Password",
    data(){
        return{
            form:{},
            user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
            rules:{
                password:[
                    { required:true,message:'請輸入原密碼',trigger:'blur'},
                    { min:3,message: '長度不少於 3 位',trigger: 'blur'}
                ],
                newPassword:[
                    { required:true,message:'請輸入新密碼',trigger:'blur'},
                    { min:3,message: '長度不少於 3 位',trigger: 'blur'}
                ],
                confirmPassword:[
                    { required:true,message:'請輸入密碼',trigger:'blur'},
                    { min:3,message: '長度不少於 3 位',trigger: 'blur'}
                ],
            }
        }
    },
    created() {
        this.form.username = this.user.username
    },
    methods:{
        save(){
            this.$refs.pass.validate((valid)=>{
                if (valid) {
                    if (this.form.newPassword !== this.form.confirmPassword) {
                        this.$message.error("2 次輸入的新密碼不相同")
                        return false
                    }
                    this.request.post("/user/password", this.form).then(res=>{
                        if (res.code === '200') {
                            this.$message.success("修改成功")
                            this.$store.commit("logout")
                        } else {
                            this.$message.error(res.msg)
                        }
                    })

                }
            })
        }
    }
})
</script>

<template>
  <el-card style="">
      <el-form label-width="120px" size="small" :model="form" :rules="rules" ref="pass">
          <el-form-item label="原密碼" prop="password">
              <el-input v-model="form.password" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item label="新密碼" prop="newPassword">
              <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item label="確認新密碼" prop="confirmPassword">
              <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="save">確 定</el-button>
          </el-form-item>
      </el-form>
  </el-card>

</template>

<style scoped>

</style>