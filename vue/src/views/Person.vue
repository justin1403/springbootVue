<script>
import {defineComponent} from 'vue'
export default defineComponent({
    name: "Person",
    data(){
        return{
            avatarUrl:'',
            form:{},
            user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{},
        }
    },
    created(){
    this.load()
    },
    methods:{
        load(){
            const username = this.user.username
            if (!username){
                this.$message.error("當前無法獲取用戶信息！")
                return
            }
            this.request.get("/user/username/" + username).then(res=>{
                this.form =res.data
            })
        },
        save(){
            this.request.post("/user",this.form).then(res=>{
                if(res.code === '200'){
                    this.$message.success("保存成功")
                    this.$emit("refreshUser")
                    this.hisy()
                }else{
                    this.$message.error("保存失敗")
                }
            })
        },
        hisy(){
            window.history.go(-1)       // 返回 前一頁
        },
        handleAvatarSuccess(res){
            this.form.avatarUrl = res
        },
    }
})
</script>
<template>
    <el-card style="width: 500px">
      <el-form  label-width="80px" size="small">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
               >
              <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <el-form-item label="用戶名">
              <el-input v-model="form.username" disabled  autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密碼">
              <el-input v-model="form.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="暱稱">
              <el-input v-model="form.nickname" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="郵箱">
              <el-input v-model="form.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="電話">
              <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="地址">
              <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
                  <el-button @click="hisy">取 消</el-button>
                  <el-button type="primary" @click="save">确 定</el-button>
          </el-form-item>

      </el-form>

    </el-card>
</template>

<style >
.avatar-uploader{
    text-align: center;
    padding-bottom: 15px;
}
.avatar-uploader .el-upload {

    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 138px;
    height: 138px;
    line-height: 138px;
    text-align: center;
}
.avatar {
    width: 138px;
    height: 138px;
    display: block;
}
</style>