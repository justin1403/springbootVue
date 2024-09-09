<script>
import {defineComponent} from 'vue'

export default defineComponent({
    name: "FrontHome",
    data(){
        return{
            imgs: [
                'https://www.momoshop.com.tw/edm/cmmedm.jsp?lpn=O7BnQ9T3Ke5&n=1&mdiv=1099900000-bt_0_247_01-bt_0_247_01_P1_8_e1&ctype=B',
                'https://www.momoshop.com.tw/edm/cmmedm.jsp?lpn=O2nf3RJxMQ9&n=1&mdiv=1099900000-bt_0_247_01-bt_0_247_01_P1_5_e1&ctype=B',

            ]
        }
    },
    created (){
        this.request.get("/echarts/file/front/all").then(res=> {
          console.log(res.data)
        })
    },
    methods:{

    }
})
</script>

<template>
<div>
  <div style="margin: 10px 0">
      <el-carousel height="450px" :interval="10000">
          <el-carousel-item v-for="item in imgs" :key="item">
            <img :src="item" alt="" style="width:100%">
          </el-carousel-item>
      </el-carousel>
  </div>

  <div style="margin: 10px 0">
      <el-row :gutter="10">
          <el-col :span="6">

          </el-col>
          <el-col :span="6">

          </el-col>

          <el-col :span="6">

          </el-col>
          <el-col :span="6">

          </el-col>
      </el-row>
  </div>
</div>



</template>

<style scoped>

</style>