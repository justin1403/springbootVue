<script>
import * as echarts from 'echarts';
import {defineComponent} from 'vue'

export default defineComponent({
    name: "Home",
    data() {
        return {}
    },
    mounted() {

        var option = {
            title: {
              text:'各季度會員數量統計',
                subtext: '趨勢圖',
                left:'center'

            },
            xAxis: {
                type: 'category',
                data: ["第一季度","第二季度","第三季度","第四季度"]
            },
            yAxis: {
                type: 'value'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name:"圖拉斯",
                    // name:"5678",
                    data: [
                    ],
                    type: 'bar'
                },
                {
                    name:"圖拉斯",
                    data: [  ],
                    type: 'line'
                },
                {
                    name:"星巴克",
                    data: [
     ],
                    type: 'bar',
                },
                {
                    name:"星巴克",
                    data: [
],
                    type: 'line'
                },
            ]
        };
        var chartDom = document.getElementById('main');
        var myChart = echarts.init(chartDom);
        this.request.get("/echarts/members").then(res=>{
            option.series[0].data=res.data
                option.series[3].data=[5,6,7,4]
                option.series[1].data=res.data
                option.series[2].data=[5,6,7,4]
            option && myChart.setOption(option)
        })

        var peioption = {
            title: {
                text:'各季度會員數量統計',
                subtext: '趨勢圖',
                left:'center',
            },
            tooltip: {
                trigger: 'item',
                formatter:'{a} <br/> {b}:{c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: '星巴克',
                    type: 'pie',
                    radius: '70%',
                    center:['30%','60%'],
                    roseType: 'radius',
                    label:{
                            show:true,
                            position:'inner',
                                fontWeight:300,
                                fontSize:14,
                                color:"#fff",

                        formatter:'{d}%'                            },

                    data: [

                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 20,
                            shadowOffsetX: 2,
                            shadowColor: 'rgba(1, 0, 0, 0.5)'
                        }
                    }
                }, {
                    name: '圖拉斯',
                    type: 'pie',
                    radius: '60%',
                    center:['75%','50%'],
                    label:{
                        // normal:{
                        show:true,
                        position:'inner',
                        // textStyle:{
                        fontWeight:300,
                        fontSize:14,
                        color:"#fff",

                        formatter:'{c}  ({d}%)'
                    },

                    data: [
                        {name:"第一季度",value:2},
                        {name:"第二季度",value:3},
                        {name:"第三季度",value:4},
                        {name:"第四季度",value:5}
                    ],
                }
            ]
        };

        var peiDom = document.getElementById('pei');
        var peiChart = echarts.init(peiDom);
        this.request.get("/echarts/members").then(res=> {
            peioption.series[0].data=[
                {name:"第一季度",value:res.data[0]},
                {name:"第二季度",value:res.data[1]},
                {name:"第三季度",value:res.data[2]},
                {name:"第四季度",value:res.data[3]}
            ]
            peioption && peiChart.setOption(peioption);
        })

    }
})
</script>

<template>
<div style="padding-top: 20px">
    <el-row :gutter="20" style="margin-bottom:60px ">
        <el-col :span="6">
            <el-card style="color:#409EFF">
                <div ><i class=" el-icon-user-solid"/> 用戶總數</div>
                <div style="padding: 10px 0;text-align: center;font-weight: bold">
                    100
                </div>
            </el-card>
        </el-col>

        <el-col :span="6">
            <el-card style="color:#67C23A">
                <div><i class="el-icon-money"/> 銷售總量</div>
                <div style="padding: 10px 0;text-align: center;font-weight: bold">
                    ￥ 1,000,000
                </div>
            </el-card>
        </el-col>

        <el-col :span="6">
            <el-card  style="color:#E6A23C">
                <div><i class="el-icon-bank-card"/> 收益總額</div>
                <div style="padding: 10px 0;text-align: center;font-weight: bold">
                    ￥ 100,000
                </div>
            </el-card>
        </el-col>

        <el-col :span="6">
            <el-card  style="color:#F56C6C">
                <div><i class="el-icon-s-shop"/> 門店總數</div>
                <div style="padding: 10px 0;text-align: center;font-weight: bold">
                    20
                </div>
            </el-card>
        </el-col>

    </el-row>

    <el-row>
    <el-col :span="12">
    <div id="main" style="width:500px;height:400px  "></div>
    </el-col>

    <el-col :span="12">
    <div id="pei" style="width:500px;height:400px  "></div>
    </el-col>

    </el-row>
</div>
</template>

<style scoped>

</style>