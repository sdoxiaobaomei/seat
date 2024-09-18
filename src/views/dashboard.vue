<script setup lang="ts">
import ticImgurl from '@/assets/img/tic.jpg';
import ttImgurl from '@/assets/img/TT.jpg';
import { getSeatBook } from '@/api/api';
import { computed, nextTick, onMounted, ref } from 'vue';
import { ButtonInstance } from 'element-plus';

const username = localStorage.getItem('username');
const isHighlightSelf = ref(false);
const today = new Date();
const currentMonth = today.getMonth() + 1;
const currentYear = today.getFullYear();
const open = ref(false)

const ref2 = ref<ButtonInstance>()
const ref3 = ref<ButtonInstance>()

let dates = [];
let date=new Date(today.getFullYear(),today.getMonth(),1)
while (date.getMonth() + 1 === currentMonth) {
    
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');  // getMonth() 从0开始计数，需加1
    const day = String(date.getDate()).padStart(2, '0');
  
    let formattedDate = `${year}-${month}-${day}`;
    dates.push(formattedDate);
    date.setDate(date.getDate() + 1);
}
const tableData = ref([]);
const getData = async () => {
    const res = await getSeatBook();
    let seatBookList = res.data;
    // console.log("seatBookList: ", seatBookList)
    seatBookList.forEach(element => {
        let newEntry = {};
        newEntry["id"] = element.id;
        element.dates.forEach(item => {
            // console.log(item)
            let key = item.date;
            let value = item.username;
            newEntry[key] = value;
        });
        
        // console.log("new entry: ", newEntry);
        tableData.value.push(newEntry);
    });
    console.log("table data: ", tableData.value)
}
onMounted(async () => {
  await getData();
 
});

function isWorkday(date) {
    let dayInWeek = date.getDay();
    return !(dayInWeek === 0 || dayInWeek === 6);
}
function splitToMonthDay(item: string) {
    const today = new Date();
    const tableDate = new Date(item);
    if (today.getDate() === tableDate.getDate() 
        && today.getMonth() === tableDate.getMonth() 
        && today.getFullYear() === tableDate.getFullYear()) {
        return "今天"
    }
    const dateArray: string[] = item.split('-');
    return `${dateArray[1]}-${dateArray[2]}`;
}


</script>
<template>
    <el-row>
        <el-col :span="6" >
            <el-button type="primary" @click="open = true" size="small" >引导</el-button>
        </el-col>
        <el-col :span="6">
            <el-image :src="ticImgurl"/>
        </el-col>
        <el-col :span="6">
            <el-image :src="ttImgurl"/>
        </el-col>
        <el-col :span="6" />
    </el-row>
    <el-row>
        <el-switch
            v-model="isHighlightSelf"
            inline-prompt
            active-text="高亮自己"
            inactive-text="不高亮"
            class="highlight-switch"
        ></el-switch>
    </el-row>
    <el-row class="table_row">
        <el-table :data="tableData" height="330" :border="true" fit >
            <el-table-column 
                label="座位"
                prop="id"
                fixed
                :min-width="100"
                :show-overflow-tooltip="true"
                 >
                
            </el-table-column>
            <el-table-column 
                v-for="(item, index) in dates" 
                :key="index"
                :prop="item"
                :label="splitToMonthDay(item)"
                :show-overflow-tooltip="true"
                >
                <template #default="{ row }">
                    <p v-if="!isWorkday(new Date(item))" style="background-color: gray;margin:0;width: 100%;">休息</p>
                    <span v-else-if="isHighlightSelf && (row[item] === username)" style="background: yellow;">{{ row[item] }}</span>
                    <span v-else>{{ row[item] }}</span>
                </template>
            </el-table-column>
        </el-table>
    
    </el-row>

    <el-tour v-model="open">
    <el-tour-step target=".collapse-btn" title="展开/折叠">
      
      <div>点击这里折叠/展开侧边菜单</div>
    </el-tour-step>
    <el-tour-step
      target=".highlight-switch"
      title=""
      description="切换显示是否高亮你自己的名字"
    />
    <el-tour-step
      target=".menu_calendar"
      title=""
      description="从此进入座位订阅界面"
    />
  </el-tour>
</template>


<style scoped>
.el-table .el-table__cell {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    padding: 1px;
    table-layout: auto;
}

.el-table .el-table__row {
	line-height: 0.5; /* 调整为你需要的行高值 */
}


</style>