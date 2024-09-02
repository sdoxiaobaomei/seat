<script setup lang="ts">
import calendar from '../store/lunarDay';
import { ref, computed }from 'vue';
import { useUserStore } from '@/store/user';


const username=computed(() => useUserStore().userInfo);

defineProps({
    data: Object
});


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

const buttonType=ref('success');
const buttonTitle=ref('book');
const isBooked=ref(false);
function bookButtonClick(data) {
    //后台验证订座是否成功，然后设置已订状态
    isBooked.value = true;
    // console.log(username);
    buttonType.value = isBooked.value ? 'info' : 'success';
    // buttonTitle.value = useUserStore().userInfo;
    buttonTitle.value = isBooked.value ? localStorage.getItem('username') + " booked" : "book";

}

function cancelButtonClick(data) {
    //cancel按钮点击事件
}

const confirmCancelEvent = () => {
    //从后台查询当前座位和日期是谁订的，和当前登录用户进行对比


    isBooked.value = false;
    // console.log(username);
    buttonType.value = isBooked.value ? 'info' : 'success';
    // buttonTitle.value = useUserStore().userInfo;
    buttonTitle.value = isBooked.value ? localStorage.getItem('username') + " booked" : "book";
}
</script>


<template>
    <div v-if="!isHoliday(data) && !isWeekend(data) && (data.type === 'current-month')" class="button-area">
        <div>
            <el-button size="small" :type="buttonType" round @click="bookButtonClick(data)" :disabled="isBooked">{{buttonTitle}}</el-button>

            <el-popconfirm title="你确定取消预定吗?" @confirm="confirmCancelEvent">
                <template #reference>
                    <el-button v-if="isBooked" size="small" type="danger" round @click="cancelButtonClick(data)">Cancel</el-button>
                </template>
            </el-popconfirm>
        </div>
    </div>
</template>