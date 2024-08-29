<script setup lang="ts">
// import { useSidebarStore } from '@/store/sidebar';
// import { useTabsStore } from '@/store/tabs';
import { CalendarInstance } from 'element-plus';
import vHeader from '../components/header.vue';
// import vSidebar from '@/components/sidebar.vue';
import vTabs from '../components/tabs.vue';
import { useTabsStore } from '../store/tabs';
import {ref,onMounted,onBeforeUnmount }from 'vue';
import calendar from '../store/lunarDay';
import { ElMessageBox } from 'element-plus';

const tabs = useTabsStore();
const datePicked = ref('');
const value = ref(new Date());
const disabledDate = ref(new Date(2024,8,10));
const Holidays = ref([
    '2023-01-01'
])
const seats = ref([
    //EE
    { label: 'A46' },
    { label: 'B46' },
    //Room2
    { label: 'C46' },
    { label: 'A47' },
    { label: 'B47' },
    { label: 'C47' },
    { label: 'D47' },
    //CM
    { label: 'A48' },
    // Room1
    { label: 'B48' },
    { label: 'C48' },
    { label: 'D48' },
    { label: 'C49' },
    { label: 'D49' },
    //Flex
    { label: 'A49' },
    { label: 'B49' },
])

const isToday = (date:Date) =>{
    return date.toDateString() === new Date().toDateString();
}

function isHoliday(soltData) {
    let solarDayArr = soltData.day.split('-');
    let lunarDay:any = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2])
    let festAndTerm:any = [];
    festAndTerm.push(lunarDay.festival == null ? '' : ' ' + lunarDay.festival);
    festAndTerm.push(lunarDay.lunarFestival == null ? '' : '' + lunarDay.lunarFestival);
    festAndTerm.push(lunarDay.Term == null ? '' : '' + lunarDay.Term);
    festAndTerm = festAndTerm.join('');
    return festAndTerm;
    // return festAndTerm != '';
}

var isHovered=ref(false);

const calendarItems=[];


</script>

<template>
    <div class="wrapper">
        <v-header />
        <div class="content-box" >
            <!-- <v-tabs></v-tabs> -->
            <!-- <h2>content box</h2> -->
            <el-tabs type="border-card">
                <el-tab-pane v-for="seat in seats" :label="seat.label">
                    <div class="content">
                        <el-calendar v-model="value">
                            <template #date-cell="{data}">
                                <div v-if="isToday(data.date)">
                                    {{ '今天'  }}
                                </div>
                                <div v-else v-for="item in calendarItems" @mouseenter="isHovered=true" @mouseleave="isHovered=false" >
                                    <p>{{ isHoliday(data)  }}</p>
                                    <p>{{ (data.day.split('-').slice(2).join('')) }}</p>
                                    <div :id="data.day" v-show="isHovered&&(data.day===item.beginTime)" >
                                        <el-button type="info" >Info</el-button>
                                    </div>
                                   
                                </div>
                            </template>
                              
                        </el-calendar>
                    </div>
                </el-tab-pane>
            </el-tabs>
            
        </div>
    </div>

</template>

<style scoped>
.layout{
    height: 100%;
}
.custom-date-class {
  /* cursor: pointer; */
  /* 其他你需要的样式 */
}
</style>
