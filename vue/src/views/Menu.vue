<script>
    import {defineComponent} from 'vue'

    export default defineComponent({
        name: "Menu",
        data(){
            return{
                tableData:[],   //
                total:0,    // 資料總筆數
                pageNum:1,  // 分頁數
                pageSize:10, // 分頁數量
                name:"",

                form:{},
                dialogFormVisible:false,
                MenuDialogVis:false,
                multipleSelection:[],

                menuData:[
                    // {
                    //     id: 1,
                    //     label: '主頁',
                    //
                    // }, {
                    //     id: 2,
                    //     label: '系統管理',
                    //     children: [{
                    //         id: 2-1,
                    //         label: '用戶管理'
                    //     }, {
                    //         id: 2-2,
                    //         label: '角色管理'
                    //     }, {
                    //         id: 2-3,
                    //         label: '菜單管理'
                    //     }, {
                    //         id: 2-4,
                    //         label: '文件管理'
                    //     }
                    //
                    //     ]
                    //
                    // }
                ],
                props:{
                    label:'name',
                },
                options:[],




            }
        },
        created() {
            // 請求分頁查詢數據
            this.load()
        },
        methods:{
            load(){
                // 請求分頁查詢數據-mybatis plus-1
                // this.request.get("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username).then(res =>{
                // console.log(res)

                // 請求分頁查詢數據-mybatis plus-2
                this.request.get("/menu", {
                    params:{
                         name: this.name,
                    }
                }).then(res =>{
                    console.log(res)
                    this.tableData=res.data

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



            },
            save(){
                this.request.post("/menu",this.form).then(res=>{
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
            handleAdd(pid){
                this.dialogFormVisible=true

                // 請求圖標的數據
                this.request.get("/menu/icons").then(res =>{
                    this.options=res.data
                })

                this.form={}
                if(pid) {
                    this.form.pid =pid
                }


            },
            // 新增
            handleAddone(){
                this.dialogFormVisible=true
                // 請求圖標的數據
                this.request.get("/menu/icons").then(res =>{
                    this.options=res.data
                })

                this.form={}
            },
            // 修改
            handleEdit(row){
                // this.form=Object.assign({},row)
                this.form = JSON.parse(JSON.stringify(row))   // p21
                // this.form=row        // 按取消也會儲存，但不會顯示儲存成功
                this.dialogFormVisible=true

                // 請求圖標的數據
                this.request.get("/menu/icons").then(res =>{
                    this.options=res.data

                })
            },
            // 刪除
            del(id){
                this.request.delete("/menu/"+id).then(res=>{
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
                this.request.post("/menu/del/batch",ids ).then(res=>{
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
                this.name=""

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
            selectMenu(roleId){
              this.MenuDialogVis = true
            },
            // handleCheckChange(){
            //
            // }




        }
    })
</script>

<template>
    <el-container>
        <el-header>

            <div>
                <el-input style="width: 200px" placeholder="請輸入名稱" suffix-icon="el-icon-search" @keyup.enter.native="load" v-model="name"> </el-input>
                <el-button type="primary" icon="el-icon-search" class="ml-5" @click="load">搜索</el-button>
                <el-button type="warning" icon="el-icon-refresh-right" @click="reset">重置</el-button>
            </div>

            <div style="padding: 10px 0">
                <el-button type="primary" @click="handleAddone">新增<i class="el-icon-circle-plus-outline"></i> </el-button>
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

<!--                <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">-->
<!--                    <el-button type="primary" class="ml-5">導入<i class="el-icon-bottom "></i> </el-button>-->
<!--                </el-upload>-->
<!--                <el-button type="primary" @click="exp" class="ml-5" >導出<i class="el-icon-top"></i> </el-button>-->

            </div>
        </el-header>



        <el-main>
           <el-table :data="tableData" lazy border stripe :header-cell-class-name="'headerBg'"
                     row-key="id" default-expand-all  @selection-change="handleSelectionChange"  >
          <el-table-column type="selection" width="45" align="center"></el-table-column>
<!--          <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>-->
          <el-table-column prop="name" label="名稱" width="120" align="center" ></el-table-column>
          <el-table-column prop="path" label="路徑" width="100" align="center" ></el-table-column>
          <el-table-column prop="pagePath" label="頁面路徑" width="100" align="center" ></el-table-column>
          <el-table-column   label="圖標" width="180" align="center" >
               <template slot-scope="scope">
                   <i :class="scope.row.icon" style="font-size: 18px"></i>
               </template>
          </el-table-column>
          <el-table-column prop="description" label="描述"   align="center"></el-table-column>
          <el-table-column type="index" label="序號" width="50" align="center"></el-table-column>

          <el-table-column label="操作" fixed="right" width="300" align="center">
              <template slot-scope="scope">
                  <el-button type="primary" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path"  style="margin-right:5px ">新增子菜單<i class="el-icon-plus"></i> </el-button>
                  <el-button type="success" @click="handleEdit(scope.row)">編輯 <i class="el-icon-edit"></i> </el-button>
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

        <el-footer>


          <el-dialog title="菜單信息" :visible.sync="dialogFormVisible" :close-on-click-modal="false" width="30%">
          <el-form  label-width="70px" size="small">
              <el-form-item label="名稱">
                  <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="路徑">
                  <el-input v-model="form.path" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="頁面路徑">
                  <el-input v-model="form.pagePath" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="圖標">
                      <el-select clearable v-model="form.icon" placeholder="請選擇" style="width:100%" >
                          <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
                          <i :class="item.value"/>{{item.name}}
                          </el-option>
                      </el-select>
              </el-form-item>
<!--                          <i v-for="item in options" :key="item.name"  :value="item.value" :class="item.value"/>-->
              <el-form-item label="描述">
                  <el-input v-model="form.description" autocomplete="off"></el-input>
              </el-form-item>
<!--              <el-form-item label="序號">-->
<!--                  <el-input v-model="form.index" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->

          </el-form>
            <div slot="footer" class="dialog-footer"  >
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
          </el-dialog>


          <el-dialog title="菜單分配" :visible.sync="MenuDialogVis" width="30%">



<!--                      @check-change="handleCheckChange">-->
              <el-tree
                      node-key="id"
                      :data="menuData"
                      show-checkbox
                      accordion>
                      :free-props="{children:'children',hasChildren:'hasChildren'}"
              </el-tree>

            <div slot="footer" class="dialog-footer"  >
                <el-button @click="MenuDialogVis = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
          </el-dialog>

        </el-footer>

    </el-container>
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