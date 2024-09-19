<script setup lang="ts">
import ticImgurl from '@/assets/img/tic.jpg';
import ttImgurl from '@/assets/img/TT.jpg';
import { deleteSeatBook, getSeatBook, insertSeatBook } from '@/api/api';
import { computed, nextTick, onMounted, ref } from 'vue';
import { ButtonInstance, ElMessage } from 'element-plus';
import { InfoFilled } from '@element-plus/icons-vue';
import calendar from '../store/lunarDay';

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
    tableData.value = [];
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

//抽屉部分
const drawer = ref(false);
const drawer2 = ref(false);
const handleClose = (done: () => void) => {
    done();
}

//日期选择器部分
const picker1 = ref(''); 

function isHoliday({dayjs}) {
    // console.log("cell: ",dayjs.format('YYYY-MM-DD'))
    const date = dayjs.format('YYYY-MM-DD');
    
    let solarDayArr = date.split('-');
    let lunarDay:any = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2]);
    if (lunarDay.holidays) console.log(date,"是假期")
    return lunarDay.holidays != null;
}

function isWeekend({dayjs}) {
    // console.log("cell: ",dayjs.format('YYYY-MM-DD'))
    const date = dayjs.format('YYYY-MM-DD');
    
    let solarDayArr = date.split('-');
    let lunarDay:any = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2]);
    return lunarDay.nWeek === 6 || lunarDay.nWeek === 7;
}

//事件部分
// 订座按钮点击事件
const bookButtonClick = async (row: any,item:any) => {
    //后台验证订座是否成功，然后设置已订状态
    // console.log("Inserting seat book:", today, "当前行：", row);
    // console.log("什么是item: ", item)
    let username = localStorage.getItem('username');

    // 遍历表格数据，检查该用户当天是否已经预定了其他座位
    for (let i = 0; i < tableData.value.length; i++) {
        const currentRow = tableData.value[i];
        
        // 如果某个座位的当前日期已经被用户预定
        if (currentRow[item] === username) {
            ElMessage({
                message: `您已在 ${item} 预订了${currentRow.id}，请先取消再预订！`,
                type: 'warning',
            });
            return; // 终止预定流程
        }
    }

    console.log("insert Seat Book result: ", insertSeatBook(row.id,username,item));
    
    // 调用 API 插入预订记录
    const insertResult = await insertSeatBook(row.id, username, item);
    
    if (!insertResult) {return}
    ElMessage({
        message: `您已成功在 ${item} 预订了${row.id}`,
        type: 'success',
    });
    // 重新获取最新的预订状态
    row[item] = username;
    // tableData.value = [...tableData.value];
    // await getData();
}

//取消预订事件
const confirmCancelEvent = async (row: any, day: any) => {
    //从后台查询当前座位和日期是谁订的，和当前登录用户进行对比
    // const today = data.day;
    // const id = `${seat.name}-${seat.group}`;
    const cancelResult = await deleteSeatBook(row.id, day);
    console.log("delete response: ", cancelResult)

    // 手动更新按钮状态
    if (!cancelResult) return;
    // 取消预定后更新当前行的订座状态
    row[day] = null;
    // 触发表格刷新
    tableData.value = [...tableData.value];
}
</script>
<template>
    <el-row>
        <el-col :span="6" >
            <el-button type="primary" @click="open = true" size="small" >引导</el-button>
        </el-col>
        <el-col :span="6">
            <el-button type="primary" @click="drawer = true" size="small" >座位图</el-button>
            <el-drawer
                v-model="drawer"
                title="座位图"
                direction="ttb"
                :before-close="handleClose"
            >
                <el-image :src="ticImgurl"/>
                <el-image :src="ttImgurl"/>
                
            </el-drawer>
        </el-col>
        <el-col :span="6">
            <el-button type="primary" @click="drawer2 = true" size="small" >按日期查询座位</el-button>
            <el-drawer
                v-model="drawer2"
                title="日历"
                direction="rtl"
                size="80%"
            >
                <el-date-picker
                    v-model="picker1"
                    type="date"
                    placeholder="Pick a day"
                    size="small"
                    format="YYYY/MM/DD"
                    value-format="YYYY-MM-DD"
                >
                    <template #default="cell">
                        <div class="cell" :class="{ current: cell.isCurrent }">
                        <span class="text">{{ cell.text }}</span>
                        <span v-if="isHoliday(cell) || isWeekend(cell)" class="holiday" ></span>
                        </div>
                    </template>
                </el-date-picker>
                
            </el-drawer>
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
        <el-table :data="tableData" height="430" :border="true" :fit="false" >
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
                class="dashboard-column"
                >
                <template #default="{ row }" >
                    <div class="dashboard-cell">
                        <p v-if="!isWorkday(new Date(item))" style="background-color: gray;margin:0;width: 100%;">休息</p>
                        <span v-else-if="isHighlightSelf && (row[item] === username)" style="background: yellow;font-size: large;">{{ row[item] }}</span>
                        <span v-else-if="!row[item]"><el-button type="success" size="small" circle @click="bookButtonClick(row, item)">订</el-button></span>
                        <el-popconfirm
                            width="220"
                            :icon="InfoFilled"
                            icon-color="#626AEF"
                            title="Are you sure to delete this?"
                            @confirm="confirmCancelEvent(row, item)"
                            v-else
                        >
                            <template #reference>
                                <span>{{ row[item] }}</span>
                            </template>
                        </el-popconfirm>

                    </div>
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


<style >
.el-table .el-table__cell {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    padding: 4px 0 !important;
    table-layout: auto;
    text-align: center !important;
}

.el-table .el-table__row {
	line-height: 0.5; /* 调整为你需要的行高值 */
}

.dashboard-cell{
    display: flex;
    align-items: center;
    justify-content: center;

}
.cell .holiday {
  position: absolute;
  width: 6px;
  height: 6px;
  background: var(--el-color-danger);
  border-radius: 50%;
  bottom: 0px;
  left: 50%;
  transform: translateX(-50%);
}
</style>