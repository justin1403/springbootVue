<template>
        <el-container style="min-height: 100vh" >
            <el-aside width="sideWidth +'px'" style="background-color: rgb(238, 241, 246)">
                <el-menu :default-openeds="['1','3','1-4']" style="min-height: 100%;overflow: hidden"
                         background-color="rgb(48,65,86)" text-color="#fff"
                         active-text-color="#ffd04b" :collapse-transition="false" :collapse="isCollapse"
                >
                    <div style="heigh:60px ;line-height:60px;text-align:center">
                        <img src="../assets/logo.png" alt="" style="width:16px;position:relative;top: 2px ">
                             <b style="color:white" v-show="logoTextShow"> 後檯管理系統</b>
                    </div>
                    <el-submenu index="1">
                        <template slot="title"><i class="el-icon-message"></i>
                            <span>導航一</span>
                        </template>
                        <el-menu-item-group >
                            <template slot="title">分組一</template>
                            <el-menu-item index="1-1">選項1</el-menu-item>
                            <el-menu-item index="1-2">選項2</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group title="分組2">
                            <el-menu-item index="1-3">選項3</el-menu-item>
                        </el-menu-item-group>
                        <el-submenu index="1-4">
                            <template slot="title">選項4</template>
                            <el-menu-item index="1-4-1">選項4-1</el-menu-item>
                        </el-submenu>
                    </el-submenu>
                    <el-submenu index="2">
                        <template slot="title"><i class="el-icon-menu"></i>
                            <span>導航二</span>
                        </template>
                        <el-menu-item-group>
                            <template slot="title">分組一</template>
                            <el-menu-item index="2-1">選項1</el-menu-item>
                            <el-menu-item index="2-2">選項2</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group title="分組2">
                            <el-menu-item index="2-3">選項3</el-menu-item>
                        </el-menu-item-group>
                        <el-submenu index="2-4">
                            <template slot="title">選項4</template>
                            <el-menu-item index="2-4-1">選項4-1</el-menu-item>
                        </el-submenu>
                    </el-submenu>
                    <el-submenu index="3">
                        <template slot="title"><i class="el-icon-setting"></i>
                            <span>導航三</span>
                        </template>
                        <el-menu-item-group>
                            <template slot="title">分組一</template>
                            <el-menu-item index="3-1">選項1</el-menu-item>
                            <el-menu-item index="3-2">選項2</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group title="分組2">
                            <el-menu-item index="3-3">選項3</el-menu-item>
                        </el-menu-item-group>
                        <el-submenu index="3-4">
                            <template slot="title">選項4</template>
                            <el-menu-item index="3-4-1">選項4-1</el-menu-item>
                        </el-submenu>
                    </el-submenu>
                </el-menu>
            </el-aside>

            <el-container>
                <el-header style="font-size: 12px;border-bottom:1px solid #ccc;line-height: 60px;display: flex">
                    <div style="flex:1;font-size: 20px">
                        <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
                    </div>
                    <el-dropdown style="width: 70px;cursor:pointer">
                        <span>王小虎</span>
                        <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>個人信息</el-dropdown-item>
                            <el-dropdown-item>退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-header>

                <el-main>
                    <div style="margin-bottom: 30px">
                        <el-breadcrumb separator="/">
                            <el-breadcrumb-item :to="{path:'/'}">首頁</el-breadcrumb-item>
                            <el-breadcrumb-item >用戶管理</el-breadcrumb-item>
                        </el-breadcrumb>
                    </div>

                    <div style="padding: 10px 0">
                        <el-input style="width: 200px" placeholder="請輸入名稱" suffix-icon="el-icon-search"  v-model="username"> </el-input>
                        <el-input style="width: 200px" placeholder="請輸入郵箱" suffix-icon="el-icon-message" class="ml-5" v-model="email"> </el-input>
                        <el-input style="width: 200px" placeholder="請輸入地址" suffix-icon="el-icon-position" class="ml-5" v-model="address"> </el-input>
                        <el-button type="primary" icon="el-icon-search" class="ml-5" @click="load">搜索</el-button>
                        <el-button type="warning" icon="el-icon-refresh-right"  @click="reset">重置</el-button>
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

                        <el-button type="primary" class="ml-5">導入<i class="el-icon-bottom "></i> </el-button>
                        <el-button type="primary" >導出<i class="el-icon-top"></i> </el-button>

                    </div>

                    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">

                        <el-table-column type="selection" width="55"></el-table-column>
                        <el-table-column prop="id" label="ID" width="80"></el-table-column>
                        <el-table-column prop="username" label="用戶名" width="140"></el-table-column>
                        <el-table-column prop="nickname" label="暱稱" width="120"></el-table-column>
                        <el-table-column prop="address" label="地址" width="120"></el-table-column>
                        <el-table-column prop="email" label="郵箱"></el-table-column>
                        <el-table-column prop="phone" label="電話"></el-table-column>
                        <el-table-column prop="address" label="地址"></el-table-column>
                        <el-table-column label="操作" width="200" align="center">
                            <template slot-scope="scope">
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
                   <div style="padding: 10px  0" >
                     <!-- :hide-on-single-page="value" 當只有一頁，分頁隱藏-->

                     <el-pagination
                               @size-change="handleSizeChange"
                               @current-change="handleCurrentChange"
                               :currentPage="pageNum"

                               :page-sizes="[2, 5, 10, 20]"
                               :page-Size="pageSize"
                               layout="total, sizes, prev, pager, next, jumper"
                               :total="total">
                       </el-pagination>
                   </div>

                    <el-dialog title="用戶信息" :visible.sync="dialogFormVisible" width="30%">
                        <el-form  label-width="70px" size="small">
                            <el-form-item label="用戶名">
                                <el-input v-model="form.username" autocomplete="off"></el-input>
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
                                <el-input v-model="form.address" autocomplete="off"></el-input>
                            </el-form-item>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="dialogFormVisible = false">取 消</el-button>
                            <el-button type="primary" @click="save">确 定</el-button>
                        </div>
                    </el-dialog>


                </el-main>
            </el-container>
        </el-container>

</template>

<script>

    export default {
      name:'HomeView',
        data() {
            return {
                tableData:[],   //
                total:0,    // 資料總筆數
                pageNum:1,  // 分頁數
                pageSize:2, // 分頁數量
                username:"",
                email:"",
                address:"",
                form:{},
                dialogFormVisible:false,
                collapseBtnClass:'el-icon-s-fold',
                isCollapse:false,
                sideWidth:200,
                logoTextShow:true ,  // false 只顯示 logo
                headerBg:'headerBg'
            }  },
        created(){
            // 請求分頁查詢數據
        this.load()
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

                    this.tableData=res.records
                    this.total=res.total



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
                 this.request.post("/user",this.form).then(res=>{
                    if(res){
                        this.$message.success("保存成功")
                        this.dialogFormVisible=false
                        this.load()

                    }else{
                    this.$message.error("保存失敗")
                    }
                })
          },
            handleAdd(){
                this.dialogFormVisible=true
                this.form={}


          },
            handleEdit(row){
                this.form=Object.assign({},row)
                // this.form=row        // 按取消也會儲存，但不會顯示儲存成功
                this.dialogFormVisible=true
          },
            del(id){
                this.request.delete("/user/"+id).then(res=>{
                    if(res){
                        this.$message.success("刪除成功")
                        this.dialogFormVisible=false
                        this.load()

                    }else{
                        this.$message.error("刪除失敗")
                    }
                })
          },
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
                    if(res){
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
          handleSizeChange(pageSize) {
            console.log(pageSize);
            this.pageSize=pageSize
            this.load()
            },
          handleCurrentChange(pageNum) {
            console.log(pageNum);
            this.pageNum=pageNum
            this.load()
          }
        }
    }
</script>

<style>
.headerBg{
  background-color: #42b983;
}
</style>