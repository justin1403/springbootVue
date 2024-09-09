<script>
import {defineComponent} from 'vue'

export default defineComponent({
    name: "Course",
    data(){
        return{
            form:{},
            tableData:[],   //
            name:'',
            multipleSelection:[],
            pageNum:1,  // 分頁數
            pageSize:10, // 分頁數量
            total:0,    // 資料總筆數
            dialogFormVisible:false,
            MenuDialogVis:false,
            teachers:[],
            user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{},
        }
    },
    created() {
        // this.$nextTick(() => {
        //     // 1.禁用右键菜单
        //         document.oncontextmenu = new Function("event.returnValue=false");
        //     // 2.禁用鼠标选中
        //         document.onselectstart = new Function("event.returnValue=false");
        //     // 3.禁止键盘F12键
        //         document.addEventListener("keydown", function (e) {
        //             if (e.key == "F12") {
        //                 e.preventDefault();// 如果按下键F12,阻止事件
        //             }
        //         });
        // });


        this.load()
    },
    methods: {
        selectCourse(courseId){
            this.request.post('/course/studentCourse/'+this.user.id + '/' + courseId).then(res=>{
                if(res.code === '200'){
                    this.$message.success("選課成功")
                    // this.dialogFormVisible=false
                }else{
                    this.$message.error("選課失敗")
                }
            })

        },
        load() {
            this.request.get("/course/page", {
                params: {
                    pageNum:this.pageNum
                    , pageSize:this.pageSize
                    , name:this.name
                }
            }).then(res => {

                this.tableData = res.data.records
                this.total = res.data.total

            })

            this.request.get("/user").then(res=>{
                this.role=res.role
            })

            this.request.get("/user/role/ROLE_TEACHER").then(res => {
                this.teachers = res.data

            })

        },
        changeEnable(row){
        this.request.post("/course/",row).then(res=>{
            if(res === '200'){
                this.$message.success("操作成功")
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
            this.request.delete("/course/"+id).then(res=>{
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
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delBatch() {

            let ids = this.multipleSelection.map(v => v.id)  //對像數組轉換 ID 數組 [{},{},{}] => [1,2,3]
            this.request.post("/course/del/batch", ids).then(res => {
                if (res) {
                    this.$message.success("批量刪除成功")
                    this.dialogFormVisible = false
                    this.load()

                } else {
                    this.$message.error("批量刪除失敗")
                }
            })
        },
        reset() {
            this.name = ""
            this.load()
        },

        // 分頁數量
        handleSizeChange(pageSize) {
            console.log(pageSize);
            this.pageSize = pageSize
            this.load()
        },
        // 填寫當前頁數
        handleCurrentChange(pageNum) {
            console.log(pageNum);
            this.pageNum = pageNum
            this.load()
        },

        // handleUploadSuccess() {
        //     this.load()
        // },
        // download(url){
        //     window.open(url)
        // },


        save(){
            this.request.post("/course",this.form).then(res=>{
                if(res.code === '200'){
                    this.$message.success("保存成功")
                    this.dialogFormVisible=false
                    this.load()

                }else{
                    this.$message.error("保存失敗")
                }
            })
        },

    }
})
</script>

<template>
    <el-container>
    <el-header>
            <div >
            <el-input style="width: 200px" placeholder="請輸入名稱" suffix-icon="el-icon-search"  v-model="name"> </el-input>
            <el-button type="primary" icon="el-icon-search" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" icon="el-icon-refresh-right" @click="reset">重置</el-button>
        </div>
<!--            v-if="user.role ==='ROLE_ADMIN'"-->
        <div style="padding: 10px 0">
            <el-button type="primary" @click="handleAdd" v-if="user.role === 'ROLE_ADMIN'">新增<i class="el-icon-circle-plus-outline"></i> </el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='確定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您確定批量删除這些數據嗎？"
                @confirm="delBatch"
            >
            <el-button type="danger" slot="reference" v-if="user.role === 'ROLE_ADMIN'" >批量刪除<i class="el-icon-circle-remove-outline"></i> </el-button>
            </el-popconfirm>
        </div>
    </el-header>

        <el-main>
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange"  >
        <el-table-column type="selection"   align="center"  width="55" ></el-table-column>

        <el-table-column prop="name" align="center" label="課程名稱"></el-table-column>
        <el-table-column prop="score" align="center" label="學分" width="100" ></el-table-column>
        <el-table-column prop="times" align="center" label="上課時數" width="160" ></el-table-column>
        <el-table-column prop="teacher" align="center" label="授課老師" width="160" ></el-table-column>



<!--        <el-table-column label="啟用" align="center"  width="180">-->
<!--            <template slot-scope="scope">-->
<!--                <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>-->
<!--            </template>-->
<!--        </el-table-column>-->
        <el-table-column label="操作"  width="260"  align="center">
            <template slot-scope="scope">
                    <el-button type="primary" @click="selectCourse(scope.row.id)" >選課</el-button>
                    <el-button type="success" @click="handleEdit(scope.row)" v-if="user.role === 'ROLE_ADMIN'" >編輯 <i class="el-icon-edit"></i></el-button>
                <el-popconfirm
                    class="ml-5"
                    confirm-button-text='確定'
                    cancel-button-text='我再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您確定删除嗎？"
                    @confirm="del(scope.row.id)">
                    <el-button type="danger" slot="reference"  v-if="user.role === 'ROLE_ADMIN'" >刪除{{user.role}}<i class="el-icon-remove-outline"></i></el-button>
                </el-popconfirm>
            </template>
        </el-table-column>
    </el-table>
        </el-main>

        <div class="dialog-footer" >
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
    </div>
        <el-dialog title="課程信息" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false"  @keyup.enter.native="save">
            <el-form  label-width="70px" size="small">
                <el-form-item label="名稱">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="學分">
                    <el-input v-model="form.score" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="上課時數">
                    <el-input v-model="form.times" autocomplete="off" ></el-input>
                </el-form-item>

                <el-form-item label="老師">
                    <el-select clearable v-model="form.teacherId" placeholder="請選擇">
                        <el-option v-for="item in teachers" :key="item.id" :label="item.nickname" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>



            </el-form>
            <div slot="footer" class="dialog-footer"  >
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>



    </el-container>
</template>

<style scoped>

</style>
