<script setup lang="ts">
import calendar from '../store/lunarDay';
import { ref, onMounted, watch }from 'vue';
import { deleteSeatBook, insertSeatBook, isSeatBookToday } from '@/api/api';

const username = localStorage.getItem('username');
const {data, seat} = defineProps({
    data: Object,
    seat: Object
});

const isBooked = ref(false);
const isLoading = ref(true);
const buttonType=ref('success');
const buttonTitle=ref('book');
const isCurrentUserBooked = ref(false);
const fetchSeatBooking = async () => {
    const res = await isSeatBookToday(seat.name+'-'+seat.group,username,data.day);
    if (res.status === 404) {return}
    if (data.type !== "current-month") {
        return;
    }
    if (res === undefined) {
        console.log("res not found of \"is seat book today\": ", res);
        return;
    }
    const seatBook = res.data;
    if (seatBook.length === 0 ) {
        return;
    }
    if (seatBook[0].dates === undefined) return;
    
    // console.log("seatBook is : ", seatBook[0])
    const findResult = seatBook[0].dates.find(rec => {
        if (rec.date === data.day) {
            // console.log("find book: ", rec)
            buttonTitle.value = rec.username + ' booked';
            isCurrentUserBooked.value = rec.username === username;
            // console.log("is current user book: ", isCurrentUserBooked.value)
        }
        return (rec.date === data.day);
    });
    isBooked.value = !!findResult;

    buttonType.value = isBooked.value ? 'info' : 'success';
    isLoading.value = false;
}
onMounted(() => {
    // isBokking.value = await isCurrentUserBooked();
    fetchSeatBooking();
})



function isHoliday(soltData) {
    let solarDayArr = soltData.day.split('-');
    let lunarDay:any = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2]);
    return lunarDay.holidays != null;
}

function isWeekend(soltData) {
    // console.log(soltData);
    let solarDayArr = soltData.day.split('-');
    let lunarDay:any = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2]);
    return lunarDay.nWeek === 6 || lunarDay.nWeek === 7;
}


// 订座按钮点击事件
const bookButtonClick = async (data: any) => {
    //后台验证订座是否成功，然后设置已订状态
    const today = data.day;//YYYY-MM-dd
    const id = `${seat.name}-${seat.group}`;

    console.log("Inserting seat book:", seat, today);
    // let username = localStorage.getItem('username');
    // let date = today;
    // console.log("insert Seat Book result: ",insertSeatBook(id,username,date));
    
    // 调用 API 插入预订记录
    const insertResult = await insertSeatBook(id, username!, today);
    
    if (!insertResult) {return}
      // 手动更新按钮状态
    buttonType.value = 'info';
    buttonTitle.value = `${username} booked`;
    isBooked.value = true;
    isCurrentUserBooked.value = true;

    // 重新获取最新的预订状态
    // await fetchSeatBooking();

}

function cancelButtonClick(data) {
    //cancel按钮点击事件
}

const confirmCancelEvent = async () => {
    //从后台查询当前座位和日期是谁订的，和当前登录用户进行对比
    const today = data.day;
    const id = `${seat.name}-${seat.group}`;
    const res = await deleteSeatBook(id, today);
    

    console.log("delete response: ", res)

    // 手动更新按钮状态
    buttonType.value = 'success';
    buttonTitle.value = 'book';
    isBooked.value = false;
    isCurrentUserBooked.value = false;
}

watch(
    ()=> data,
    async (newValue, oldValue) => {
    console.log("Month changed. Resetting button states.",oldValue,newValue);
    buttonType.value = 'success';
    buttonTitle.value = 'book';
    isBooked.value = false;
    isCurrentUserBooked.value = false;

    // 在月份切换后重新获取座位预订状态
    await fetchSeatBooking();
  },
  { deep: true } // 深度监听对象变化
)
</script>


<template>
    <div v-if="!isHoliday(data) && !isWeekend(data) && (data.type === 'current-month')" class="button-area" v-loading="isLoading">
        <div>
            <el-button 
                size="small" 
                :type="buttonType" 
                round 
                @click="bookButtonClick(data)" 
                :disabled="isBooked">
                {{buttonTitle}}
            </el-button>

            <el-popconfirm title="你确定取消预定吗?" @confirm="confirmCancelEvent">
                <template #reference>
                    <el-button 
                        v-if="isCurrentUserBooked" 
                        size="small" 
                        type="danger" 
                        round 
                        @click="cancelButtonClick(data)">
                        Cancel
                    </el-button>
                    <div v-else></div>
                </template>
            </el-popconfirm>
        </div>
    </div>
    <div v-else>

    </div>
</template>