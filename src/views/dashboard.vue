<script setup lang="ts">
import ticImgurl from '@/assets/img/tic.jpg';
import ttImgurl from '@/assets/img/TT.jpg';
import { deleteSeatBook, getSeatBook, insertSeatBook } from '@/api/api';
import { computed, nextTick, onMounted, ref } from 'vue';
import { ButtonInstance, ElMessage, ElTable } from 'element-plus';
import { InfoFilled,ArrowLeft, ArrowRight} from '@element-plus/icons-vue';
import CalendarMobileVue from '@/components/calendarMobileVue.vue';
import calendar from '../store/lunarDay';

const username = localStorage.getItem('username');
const isHighlightSelf = ref(false);
const today = new Date();
const currentMonth = ref(today.getMonth() + 1);//default to the month of today
const currentYear = today.getFullYear();
const open = ref(false)

const ref2 = ref<ButtonInstance>()
const ref3 = ref<ButtonInstance>()

const dates = ref([]);
//得到dates数值，根据传入的月份更新
const getDatesByMonth = ( changedMonth: number) => {
    if ((changedMonth === currentMonth.value) && (dates.value.length > 0)) return;
    dates.value = [];//initial dates
    // console.log("get dates for month: ", changedMonth)
    let date=new Date(currentYear,changedMonth - 1, 1)
    while (date.getMonth() + 1 === changedMonth) {
        
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');  // getMonth() 从0开始计数，需加1
        const day = String(date.getDate()).padStart(2, '0');
    
        let formattedDate = `${year}-${month}-${day}`;
        dates.value.push(formattedDate);
        date.setDate(date.getDate() + 1);
    }
    currentMonth.value=changedMonth;
    // console.log("dates: ", dates)
}
getDatesByMonth(currentMonth.value);
const tableData = ref([]);

const getData = async () => {
    // getDatesByMonth(currentMonth.value);
    tableData.value = [];
    const resData = await getSeatBook(dates);
    console.log("table data: ", resData)
    tableData.value = resData;
    // console.log("seatBookList: ", seatBookList)
    // seatBookList.forEach(element => {
    //     let newEntry = {};
    //     newEntry["id"] = element.id;
    //     element.dates.forEach(item => {
    //         // console.log(item)
    //         let key = item.date;
    //         let value = item.username;
    //         newEntry[key] = value;
    //     });
        
    //     // console.log("new entry: ", newEntry);
    //     tableData.value.push(newEntry);
    // });
    // console.log("table data: ", tableData.value)
}
onMounted(async () => {
  await getData();
});

function isWorkday(date: Date) {
    let isWorkday = true;
    let lunarDay:any = calendar.solar2lunar(date.getFullYear(), String(date.getMonth()+1).padStart(2, '0'), String(date.getDate()).padStart(2, '0'));
    
    isWorkday = (lunarDay.holidays == null);//节假日
    isWorkday = lunarDay.nWeek <= 5; //周末
    return isWorkday;
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


//事件部分
// 订座按钮点击事件
const bookButtonClick = async (row: any,item:any) => {
    //后台验证订座是否成功，然后设置已订状态
     console.log("Inserting seat book:", today, "当前行：", row.subscriptions, "item是：",item);
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

    // console.log("insert Seat Book result: ", insertSeatBook(row.id,username,item));
    
    // 调用 API 插入预订记录
    const insertResult = await insertSeatBook(row.seatId, username, item);
    
    if (!insertResult) {return}
    ElMessage({
        message: `您已成功在 ${item} 预订了${row.seatName}`,
        type: 'success',
    });
    // 重新获取最新的预订状态
    row[item] = username;
    // tableData.value = [...tableData.value];
    getData();
}

//取消预订事件
const confirmCancelEvent = async (row: any, day: any) => {
    //从后台查询当前座位和日期是谁订的，和当前登录用户进行对比
    // const today = data.day;
    // const id = `${seat.name}-${seat.group}`;
    // console.log("cancel which row: ", row[day])
    const cancelUser = row[day];
    if (cancelUser !== username) {
        ElMessage({
            message: `您无法取消${cancelUser}的预定`,
            type: 'error',
        });
        return;
    }
    const cancelResult = await deleteSeatBook(row.id, day);
    console.log("delete response: ", cancelResult)

    // 手动更新按钮状态
    if (!cancelResult) return;
    // 取消预定后更新当前行的订座状态
    row[day] = null;
    // 触发表格刷新
    tableData.value = [...tableData.value];
}

const myTable = ref<InstanceType<typeof ElTable> | null>(null);
//跳转到今天
const showTodayData = () => {
    console.log("show today Data, ")
    // const year = today.getFullYear();
    // const month = String(today.getMonth() + 1).padStart(2, '0');  // getMonth() 从0开始计数，需加1
    // const day = String(today.getDate()).padStart(2, '0');
    
    // let formattedDate = `${month}-${day}`;

    const table = myTable.value?.$el;
    if (!table) return;

    const columnsElements = table.querySelectorAll('.el-table__header th');

    for (let i = 0; i < columnsElements.length; i++) {
    const column = columnsElements[i];
    console.log("column.innerText: ",column.innerText)
    if (column.innerText === "今天") {
        const columnOffset = column.getBoundingClientRect().left;
        const tableRect = table.getBoundingClientRect();
        const scrollLeft = columnOffset - tableRect.left + table.scrollLeft;

        table.scrollTo({ left: scrollLeft, behavior: 'smooth' });
        break;
    }
    }

}

//展示下个月数据
const nextMonthData = () => {
    getDatesByMonth(currentMonth.value + 1);
    
    getData();
}

//展示上个月数据
const lastMonthData = () => {
    getDatesByMonth(currentMonth.value - 1);
    getData();
}

</script>
<template>
    <el-row>
        <el-col :span="6" >
            <el-button type="primary" @click="open = true" size="small" >引导</el-button>
        </el-col>
        <el-col :span="6">
            <el-button type="primary" @click="drawer = true" size="small" class="button-seatPic">座位图</el-button>
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
            <el-button disabled type="primary" @click="drawer2 = true" size="small" >按日期查询座位</el-button>
            <el-drawer
                v-model="drawer2"
                title="日历"
                direction="rtl"
                size="80%"
            >
                <CalendarMobileVue />
            </el-drawer>
        </el-col>
        <el-col :span="6" />
    </el-row>
    <el-row>
        <el-col :span="6">

            <el-switch
                v-model="isHighlightSelf"
                inline-prompt
                active-text="高亮自己"
                inactive-text="不高亮"
                class="highlight-switch"
            ></el-switch>
        </el-col>
        <el-col :span="18" style="display: flex;align-items: center;">

            <el-button-group size="small">
                <el-button type="primary" :icon="ArrowLeft" @click="lastMonthData">上个月</el-button>
                <el-button type="primary" @click="showTodayData">今天</el-button>
                <el-button type="primary" @click="nextMonthData">
                    下个月<el-icon class="el-icon--right"><ArrowRight /></el-icon>
                </el-button>
            </el-button-group>
        </el-col>
    </el-row>
    <el-row class="table_row">
        <el-table :data="tableData" ref="myTable" height="430"  :border="true" :fit="false" >
            <el-table-column 
                label="座位"
                prop="seatName"
                fixed
                :min-width="100"
                :show-overflow-tooltip="true"
                filters
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
                        <span v-else-if="isHighlightSelf && (row[item] === username)" style="background: yellow;font-size: large;">{{ row.subscriptions[item] }}</span>
                        <span v-else-if="!row.subscriptions[item]"><el-button type="success" size="small" circle @click="bookButtonClick(row, item)">订</el-button></span>
                        <el-popconfirm
                            width="220"
                            :icon="InfoFilled"
                            icon-color="#626AEF"
                            title="Are you sure to cancel?"
                            @confirm="confirmCancelEvent(row, item)"
                            v-else
                        >
                            <template #reference>
                                <span>{{ row.subscriptions[item] }}</span>
                            </template>
                        </el-popconfirm>

                    </div>
                </template>
            </el-table-column>
        </el-table>
    </el-row>

    <el-tour v-model="open">
        <el-tour-step 
            
            title="预订/取消">
        
            <div>点击绿色按钮订座,点击表格中名字取消预订</div>
        </el-tour-step>
        <el-tour-step
            target=".highlight-switch"
            title=""
            description="切换显示是否高亮你自己的名字"
        />
        <el-tour-step
            target=".button-seatPic"
            title=""
            description="点此查看座位图"
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

</style>