<script>
    import {defineComponent} from 'vue'

    export default defineComponent({
        name: "Role",
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
                ],
                props: {
                    label:'name',
                },
                expends:[],
                checks:[],
                roleId:0,
                roleFlag:'',

            }
        },
        created() {
            // 請求分頁查詢數據
            this.load()
        },
        methods:{
            load(){
                this.request.get("/role/page", {
                    params:{
                        pageNum: this.pageNum
                        ,pageSize: this.pageSize
                        ,name: this.name

                    }
                }).then(res =>{
                    console.log(res)
                    this.tableData=res.data.records
                    this.total=res.data.total
                })
                this.request.get("/menu/ids").then(r=>{
                    this.ids=r.data
                })
            },
            save(){
                this.request.post("/role",this.form).then(res=>{
                    if(res.code === '200'){
                        this.$message.success("保存成功")
                        this.dialogFormVisible=false
                        this.load()

                    }else{
                        this.$message.error("保存失敗")
                    }
                })
            },
            saveRoleMenu(){
                this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res=>{

                    if(res.code === '200'){
                        this.$message.success("綁定成功")
                        this.MenuDialogVis = false

                        // 操作管理員角色後需要重新登錄
                        if (this.roleFlag === 'ROLE_ADMIN'){
                          this.$store.commit("logout")
                        }
                    }else{
                        this.$message.error(res.msg)
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
                this.dialogFormVisible=true
            },
            // 刪除
            del(id){
                this.request.delete("/role/"+id).then(res=>{
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
                let ids = this.multipleSelection.map(v => v.id)
                this.request.post("/role/del/batch",ids ).then(res=>{
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
            selectMenu(role){
                this.MenuDialogVis = true
                this.roleId = role.id
                this.roleFlag = role.flag
                this.request.get("/menu").then(res =>{
                    this.menuData=res.data
                    this.expends = this.menuData.map(v => v.id)
                })
                this.request.get("/role/roleMenu/" + this.roleId).then(res =>{
                    this.checks=res.data
                    this.ids.forEach(id=>{
                            if(!this.checks.includes(id)){
                                this.$nextTick(()=>{
                                  this.$refs.tree.setChecked(id,false)
                                })
                            }

                        })
                    this.MenuDialogVis = true
                    })
            },
        }
    })
</script>

<template>
    <el-container>
        <el-header>
            <div>
                <el-input style="width: 200px" placeholder="請輸入名稱" suffix-icon="el-icon-search" @keyup.enter.native="load"  v-model="name"> </el-input>
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
            </div>
        </el-header>



        <el-main>
           <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"   @selection-change="handleSelectionChange"  >
          <el-table-column type="selection" width="55" align="center"></el-table-column>
          <el-table-column prop="name" label="名稱" width="100" align="center" ></el-table-column>
          <el-table-column prop="flag" label="唯一標識" width="200" align="center" ></el-table-column>
          <el-table-column prop="description" label="描述"   align="center"></el-table-column>

          <el-table-column label="操作" fixed="right" width="300" align="center">
              <template slot-scope="scope">
                  <el-button type="info" @click="selectMenu(scope.row)" style="margin-right:5px ">分配菜單<i class="el-icon-menu"></i> </el-button>
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
          <div class="dialog-footer2" >
          <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :currentPage="pageNum"
                  :page-sizes="[2, 5, 10 ,20]"
                  :page-Size="pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total">
          </el-pagination>
      </div>

          <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%" @keyup.enter.native="save" :close-on-click-modal="false">
          <el-form  label-width="70px" size="small">
              <el-form-item label="名稱">
                  <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="唯一標識">
                  <el-input v-model="form.flag" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="描述">
                  <el-input v-model="form.description" autocomplete="off"></el-input>
              </el-form-item>

          </el-form>
          <div slot="footer" class="dialog-footer"  >
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
          </div>
      </el-dialog>


      <el-dialog title="菜單分配" :visible.sync="MenuDialogVis" width="30%" @keyup.enter.native="saveRoleMenu" :close-on-click-modal="false">

          <el-tree
                  :props="props"
                  :data="menuData"
                  show-checkbox
                  node-key="id"
                  ref="tree"
                  accordion
                  default-expand-all
                  :default-expanded-keys="expends"
                  :default-checked-keys="checks"
          >
              <span class="custom-tree-node" slot-scope="{ node, data }">
                  <span><i :class="data.icon"></i> {{data.name}}</span>
              </span>
          </el-tree>
          <div slot="footer" class="dialog-footer"  >
              <el-button @click="MenuDialogVis = false">取 消</el-button>
              <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
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

