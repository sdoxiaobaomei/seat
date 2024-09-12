<script setup lang="ts">
import calendar from '../store/lunarDay';
import calendarButton from './calendarButton.vue';
import { ref } from 'vue';

const value = ref(new Date());
const isToday = (date:Date) =>{
    return date.toDateString() === new Date().toDateString();
}

defineProps ({
    seat:{
        type: Object
    }
})

function whichHoliday(soltData) {
    let solarDayArr = soltData.day.split('-');
    let lunarDay:any = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2])
    let festAndTerm:any = [];
    
    festAndTerm.push(lunarDay.holidays == null ? '' : ' ' + lunarDay.holidays);
    // festAndTerm.push(lunarDay.lunarFestival == null ? '' : '' + lunarDay.lunarFestival);
    // festAndTerm.push(lunarDay.Term == null ? '' : '' + lunarDay.Term);
    festAndTerm = festAndTerm.join('');
    return festAndTerm;
    // return festAndTerm != '';
}
</script>

<template>
    <div class="container">
        
        <el-calendar v-model="value" class="custom-calendar">
            <template #date-cell="{data}">

                    <div class="custom-content">
                        <div class="date-area">
                            <div v-if="isToday(data.date)">
                                <p>{{ whichHoliday(data)}}</p>
                                <p>{{ '今天' }}</p>
                            </div>
                            <div v-else>
                                <p>{{ whichHoliday(data)}}</p>
                                <p>{{ (data.day.split('-').slice(2).join('')) }}</p>
                            </div>
                        </div>
                        <calendar-button :data="data" :seat="seat"/>
                        <!-- <div v-if="!isHoliday(data) && !isWeekend(data) && (data.type === 'current-month')" class="button-area">
                            <div>
                                <el-button :id="data.day" size="small" :type="buttonType" round @click="bookButtonClick(data)">book</el-button>
                            </div>
                        </div> -->
    
                    </div>
                </template>            
            </el-calendar>
    </div>
</template>

<style>

.custom-calendar .el-calendar-day {
  position: relative;
  display: flex;
  overflow-x: auto;
  
}

 .custom-content {
  position: relative;
  top: 0;
  /* left: 0; */
  
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  /* justify-content: center; */
  /* border: 1px solid #409eff; */
  box-sizing: border-box;
  overflow: hidden;
}
.date-area{
    align-items: flex-end;
}
.button-area{
    left: 200;
    align-items: stretch;
}
</style>