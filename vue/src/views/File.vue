<script>
import {defineComponent} from 'vue'

export default defineComponent({
    name: "File",
    data(){
        return{
            tableData:[],   //
            total:0,    // 資料總筆數
            pageNum:1,  // 分頁數
            pageSize:10, // 分頁數量
            name:'',
            multipleSelection:[],
        }
    },
    created() {
    this.load()
    },
    methods: {
        load() {
            this.request.get("/file/page", {
                params: {
                    pageNum: this.pageNum
                    , pageSize: this.pageSize
                    , name: this.name
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })

        },
        changeEnable(row){
        this.request.post("/file/update",row).then(res=>{
            if(res === '200'){
                this.$message.success("操作成功")
            }
        })
        },
        // 刪除
        del(id) {
            this.request.delete("/file/" + id).then(res => {
                if (res.data) {
                    this.$message.success("刪除成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
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
            this.request.post("/file/del/batch", ids).then(res => {
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
        handleFileUploadSuccess() {
            this.load()
        },
        download(url){
            window.open(url)
        }


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
        <div style="padding: 10px  0">
            <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" :on-success="handleFileUploadSuccess" style="display: inline-block">
                <el-button type="primary">上傳文件<i class="el-icon-top"></i> </el-button>
            </el-upload>
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
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange"  >
        <el-table-column type="selection"   align="center"  width="55" ></el-table-column>
        <el-table-column  label="圖片" align="center" width="100">
            <template slot-scope="scope"  >
                <img :src="scope.row.url"   width="80" height="80"   />
            </template>
         </el-table-column>
        <el-table-column prop="name" align="center" label="文件名稱"   ></el-table-column>
        <el-table-column prop="type" align="center" label="文件類型" width="80" ></el-table-column>
        <el-table-column prop="size" align="right" label="文件大小（KB）" width="160" ></el-table-column>
        <el-table-column  label="下載" align="center" width="100" >
            <template slot-scope="scope">
                <el-button type="primary" @click="download(scope.row.url)">下載</el-button>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
            <template slot-scope="scope">
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
    </el-container>
</template>

<style scoped>

</style>
