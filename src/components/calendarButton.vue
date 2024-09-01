<script setup lang="ts">
import calendar from '../store/lunarDay';
import { ref, defineProps, computed }from 'vue';
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
function bookButtonClick(data) {
    
    // console.log(username);
    buttonType.value = 'info';
    buttonTitle.value = useUserStore().userInfo;

}
</script>


<template>
    <div v-if="!isHoliday(data) && !isWeekend(data) && (data.type === 'current-month')" class="button-area">
        <div>
            <el-button size="small" :type="buttonType" round @click="bookButtonClick(data)">{{buttonTitle}}</el-button>
        </div>
    </div>
</template>