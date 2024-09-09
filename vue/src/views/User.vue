<script>
    import {defineComponent} from 'vue'
    import course from "@/views/Course.vue";

    export default defineComponent({
        name: "User",
        computed: {
            course() {
                return course
            }
        },
        data(){
            return{
                tableData:[],   //
                total:0,    // 資料總筆數
                pageNum:1,  // 分頁數
                pageSize:10, // 分頁數量
                username:"",
                email:"",
                address:"",
                form:{},
                dialogFormVisible:false,
                multipleSelection:[],
                roles:[],
                courses:[],
                vis:false,
                stuCourses:[],
                stuVis:false,
            }
        },
        created() {
            // 請求分頁查詢數據
            this.load()
        },
        methods:{
            lookCourse(courses){
                this.courses=courses
                this.vis=true
            },
            lookStuCourse(stuCourses){
                this.stuCourses=stuCourses
                this.stuVis=true
            },

            load(){
                // 請求分頁查詢數據-mybatis plus-1
                // this.request.get("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username).then(res =>{
                // console.log(res)

                // 請求分頁查詢數據-mybatis plus-2
                this.request.get("/user/page", {
                    params:{
                        pageNum: this.pageNum
                        ,pageSize: this.pageSize
                        ,username: this.username
                        ,email: this.email
                        ,address: this.address
                    }
                }).then(res =>{
                    console.log(res)

                    this.tableData=res.data.records
                    this.total=res.data.total



                })



                //     // 請求分頁查詢數據-1
                // fetch("http://localhost:9090/user/page?pageNum="+this.pageNum
                //     +"&pageSize="+this.pageSize
                // // )
                //     +"&username="+this.username)
                //     .then(res=> res.json()).then(res=>{console.log(res)
                //   this.tableData=res.data
                //   this.total=res.total
                // })


                this.request.get("/role").then(res=>{
                    this.roles = res.data

                })


            },
            save(){
                this.request.post("/user",this.form).then(res=>{
                    if(res.code === '200'){
                        this.$message.success("保存成功")
                        this.dialogFormVisible=false
                        this.load()

                    }else{
                        this.$message.error("保存失敗")
                    }
                })
            },
            // 新增
            handleAdd(){
                this.dialogFormVisible=true
                this.form={}


            },
            // 修改
            handleEdit(row){
                this.form=Object.assign({},row)
                // this.form=row        // 按取消也會儲存，但不會顯示儲存成功
                this.dialogFormVisible=true
            },
            // 刪除
            del(id){
                this.request.delete("/user/"+id).then(res=>{
                    if(res.code === '200'){
                        this.$message.success("刪除成功")
                        this.dialogFormVisible=false
                        this.load()

                    }else{
                        this.$message.error("刪除失敗")
                    }
                })
            },
            // 批量選取
            handleSelectionChange(val){
                console.log(val)
                this.multipleSelection = val
            },
            delBatch(){
                // if (!this.ids.length) {
                //     this.$message.warning("请选择数据！")
                //     return
                // }
                // this.request.get("/user/delEmps/"+this.ids).then(res =>{
                //     this.$message.success("MyBatis批量删除成功！");
                //     this.load()
                // })





                let ids = this.multipleSelection.map(v => v.id)  //對像數組轉換 ID 數組 [{},{},{}] => [1,2,3]
                this.request.post("/user/del/batch",ids ).then(res=>{
                    if(res.code === '200'){
                        this.$message.success("批量刪除成功")
                        this.dialogFormVisible=false
                        this.load()

                    }else{
                        this.$message.error("批量刪除失敗")
                    }
                })
            },
            reset(){
                this.username=""
                this.email=""
                this.address=""
                this.load()
            },

            // 分頁數量
            handleSizeChange(pageSize) {
                console.log(pageSize);
                this.pageSize=pageSize
                this.load()
            },
            // 填寫當前頁數
            handleCurrentChange(pageNum) {
                console.log(pageNum);
                this.pageNum=pageNum
                this.load()
            },
            // 導出
            exp(){
                window.open("http://localhost:9090/user/export")

            },
            // 導入
            handleExcelImportSuccess(){
                this.$message.success("導入成功")
                this.load()

            },

        }
    })
</script>

<template>
    <div>
        <el-header>
            <div>
                <el-input style="width: 200px" placeholder="請輸入名稱" suffix-icon="el-icon-search"  @keyup.enter.native="load" v-model="username"> </el-input>
                <el-input style="width: 200px" placeholder="請輸入郵箱" suffix-icon="el-icon-message" @keyup.enter.native="load" class="ml-5" v-model="email"> </el-input>
                <el-input style="width: 200px" placeholder="請輸入地址" suffix-icon="el-icon-position" @keyup.enter.native="load" class="ml-5" v-model="address"> </el-input>
                <el-button type="primary" icon="el-icon-search" class="ml-5" @click="load">搜索</el-button>
                <el-button type="warning" icon="el-icon-refresh-right" @click="reset">重置</el-button>
            </div>

            <div style="padding: 10px 0">
                <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i> </el-button>
                <el-popconfirm
                    class="ml-5"
                    confirm-button-text='確定'
                    cancel-button-text='我再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您確定批量删除這些數據嗎？"
                    @confirm="delBatch"
                >
                <el-button type="danger" slot="reference">批量刪除<i class="el-icon-circle-remove-outline"></i> </el-button>
                </el-popconfirm>
                <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
                    <el-button type="primary" class="ml-5">導入<i class="el-icon-bottom "></i> </el-button>
                </el-upload>

                <el-button type="primary" @click="exp" class="ml-5" >導出<i class="el-icon-top"></i> </el-button>

            </div>
        </el-header>



        <el-main>
           <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange" style="width: 100%">
          <el-table-column type="selection" width="55" align="center"></el-table-column>
<!--          <el-table-column prop="id" label="ID" width="60" align="center"></el-table-column>-->
          <el-table-column prop="username" label="用戶名" fixed  width="100" align="center" fixed="left" ></el-table-column>
          <el-table-column prop="nickname" label="暱稱" width="100" align="center"></el-table-column>
<!--          <el-table-column prop="password" label="密碼" width="100" align="center"></el-table-column>-->
          <el-table-column prop="role" label="角色" width="150" align="center">
              <template slot-scope="scope">
              <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理員</el-tag>
              <el-tag type="warning" v-if="scope.row.role === 'ROLE_TEACHER'">教師</el-tag>
              <el-tag type="success" v-if="scope.row.role === 'ROLE_STUDENT'">學生</el-tag>
              </template>
          </el-table-column>
          <el-table-column prop="phone" label="電話" width="100" align="center"></el-table-column>
          <el-table-column prop="email" label="郵箱" max-width="240" align="center"></el-table-column>
          <el-table-column prop="address" label="地址" max-width="240"></el-table-column>
          <el-table-column label="操作" fixed="right" width="300" align="center">
              <template slot-scope="scope">
                  <el-button type="primary" @click="lookCourse(scope.row.courses)" v-if="scope.row.role === 'ROLE_TEACHER'">查看教學課程<i class="el-icon-view"></i> </el-button>
                  <el-button type="warning" @click="lookStuCourse(scope.row.stuCourses)" v-if="scope.row.role === 'ROLE_STUDENT'">查看已選課程<i class="el-icon-view"></i> </el-button>
                  <el-button type="success" @click="handleEdit(scope.row)">編輯<i class="el-icon-edit"></i> </el-button>
                  <el-popconfirm
                          class="ml-5"
                          confirm-button-text='確定'
                          cancel-button-text='我再想想'
                          icon="el-icon-info"
                          icon-color="red"
                          title="您確定删除嗎？"
                          @confirm="del(scope.row.id)"
                  >
                      <el-button type="danger" slot="reference">刪除<i class="el-icon-remove-outline"></i> </el-button>
                  </el-popconfirm>
              </template>
          </el-table-column>
      </el-table>
        </el-main>

          <!-- :hide-on-single-page="value" 當只有一頁，分頁隱藏-->

          <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :currentPage="pageNum"
                  :page-sizes="[2, 5, 10 ,20]"
                  :page-Size="pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total">
          </el-pagination>

          <el-dialog title="用戶信息" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false">
          <el-form  label-width="70px" size="small">
              <el-form-item label="用戶名">
                  <el-input v-model="form.username" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="角色">
                <el-select clearable v-model="form.role" placeholder="請選擇角色" style="width: 100%">
                    <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag" ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="暱稱">
                  <el-input v-model="form.nickname" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="密碼">
                  <el-input v-model="form.password" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="郵箱">
                  <el-input v-model="form.email" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="電話">
                  <el-input v-model="form.phone" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="地址">
                  <el-input v-model="form.address" autocomplete="off"></el-input>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer"  >
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
          </div>
      </el-dialog>

      <el-dialog title="課程信息" :visible.sync="vis" width="30%" :close-on-click-modal="false">
          <el-table :data="courses" border stripe>
              <el-table-column prop="name" label="課程名稱"></el-table-column>
              <el-table-column prop="score" label="學分"></el-table-column>
          </el-table>
      </el-dialog>



      <el-dialog title="課程信息" :visible.sync="stuVis" width="30%" :close-on-click-modal="false">
          <el-table :data="courses" border stripe>
              <el-table-column prop="name" label="課程名稱"></el-table-column>
              <el-table-column prop="score" label="學分"></el-table-column>
          </el-table>
      </el-dialog>



    </div>
</template>

<style scoped>
.headerBg{
    background-color: #42b983;
}
.el-header {
     height: 60px;
}





</style>


/*.dialog-footer2{*/
/*    //z-index: 500;*/
/*    position:fixed;*/
/*    //padding: 3px;*/
/*    bottom: 0;*/
/*    //border: 5px;*/
/*    //margin: 20px;*/
/*    //height: 99px;*/
/*    width: 100%;*/
/*    //font-size: 80px;*/
/*    text-align: center;*/
//}