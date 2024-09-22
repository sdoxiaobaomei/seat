<template>
<el-row>
    <el-col :span="18">

        <el-date-picker
            v-model="picker1"
            type="date"
            placeholder="Pick a day"
            size="small"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
            @change="handleDateConfirm"
            @clear="handleDateClear"
        >
            <template #default="cell">
                <div class="cell" :class="{ current: cell.isCurrent }">
                    <span  class="text">{{ cell.text }}</span>
                    <span v-if="isHoliday(cell) || isWeekend(cell)" class="holiday"></span>
                </div>
            </template>
        </el-date-picker>
    </el-col>
    <!-- <el-col :span="6">

        <el-button type="primary" size="small" :icon="Search"></el-button>
    </el-col> -->
</el-row>
<el-row>
    <el-col :span="24">
        <el-card v-for="item in seatBookByDates" :key="item.id" class="record-card" shadow="always">
            <div class="group-title">{{ item.id }}</div>
            <div class="card-header">
                <span class="seat-info">座位: {{ item.seat }}</span>
            </div>
            <div class="card-content">
                <p v-show="item.bookedUser !== ''">订阅人: {{ item.bookedUser }}</p>
                <!-- <p>状态: <strong>{{ record.status }}</strong></p> -->
            </div>
            <div class="card-actions">
                <el-button v-if="item.bookedUser === ''" type="success" size="small" @click="bookSeat(item)">订</el-button>
                 <el-button v-if="item.bookedUser === username" type="danger" size="small" @click="cancelBook(item)">取消</el-button> 
            </div>
        </el-card>
    </el-col>
</el-row>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import calendar from '../store/lunarDay';
import { Search } from '@element-plus/icons-vue';
import { getSeatBookByDate, insertSeatBook } from '@/api/api';

const username = localStorage.getItem('username');

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
const seatBookByDates=ref([])
const handleDateConfirm = async (date: string) => {
    console.log("param: ", date)
    seatBookByDates.value = await getSeatBookByDate(date);
    // console.log("after response seat book by date: ", seatBookByDate)
}
const handleDateClear = async () => {
    seatBookByDates.value=[];
    console.log("after clear list: ", seatBookByDates)
}
const bookSeat = async (item) => {
    const date = item.id;
    const seat = item.seat;
    const insertResult = await insertSeatBook(seat, username, date );
    console.log("insert result: ", insertResult)
}

const cancelBook = async (item) => {
    console.log("Cancel: ", item)
}
</script>

<style scoped>
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
.booking-records {
  padding: 16px;
}
.group-section {
  margin-bottom: 16px;
}
.group-title {
  font-weight: bold;
  font-size: 1.2em;
  margin-bottom: 8px;
}
.record-card {
  margin-bottom: 12px;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 10px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  font-size: 1em;
  margin-bottom: 6px;
}
.card-content p {
  margin: 4px 0;
}
.card-actions {
  display: flex;
  justify-content: flex-end;
}
.status {
  padding: 2px 8px;
  border-radius: 4px;
  font-weight: bold;
}
.status.已订阅 {
  background-color: #67c23a;
  color: white;
}
.status.已取消 {
  background-color: #f56c6c;
  color: white;
}
</style>