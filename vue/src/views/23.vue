<script>
import {defineComponent} from 'vue'

export default defineComponent({
    name: "23",
    data() {
        return {
            data: [{
                id: 1,
                label: '一级 1',
                children: [{
                    id: 4,
                    label: '二级 1-1',
                    children: [{

                    }]
                }]
            }, {
                id: 2,
                label: '一级 2',
                children: [{
                    id: 5,
                    label: '二级 2-1'
                }, {
                    id: 6,
                    label: '二级 2-2'
                }]
            }, {
                id: 3,
                label: '一级 3',
                children: [{
                    id: 7,
                    label: '二级 3-1'
                }, {
                    id: 8,
                    label: '二级 3-2'
                }]
            }],

        };
    }
})
</script>

<template>
    <div>
        <el-tree
            :data="data"
            show-checkbox
            node-key="id"

        >
        </el-tree>

    </div>

</template>

<style scoped>

</style>