<script setup lang="ts">
import ticImgurl from '@/assets/img/tic.jpg';
import ttImgurl from '@/assets/img/TT.jpg';
import { getSeatBook } from '@/api/api';
import { computed, ref } from 'vue';

const username = localStorage.getItem('username');
const isHighlightSelf = ref(false);
const today = new Date();
const currentMonth = today.getMonth() + 1;
const currentYear = today.getFullYear();
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
        
        console.log("new entry: ", newEntry);
        tableData.value.push(newEntry);
    });
    console.log("table data: ", tableData.value)
}
getData();

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
        <el-col :span="6" />
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
        ></el-switch>
    </el-row>
    <el-row>
        <el-table :data="tableData" height="400">
            <el-table-column 
                label="座位"
                prop="id"
                fixed >
                
            </el-table-column>
            <el-table-column 
                v-for="(item, index) in dates" 
                :key="index"
                :prop="item"
                :label="splitToMonthDay(item)">
                <template #default="{ row }">
                    <p v-if="!isWorkday(new Date(item))" style="background-color: brown;">休息</p>
                    <span v-else-if="isHighlightSelf && (row[item] === username)" style="background: yellow;">{{ row[item] }}</span>
                    <span v-else>{{ row[item] }}</span>
                </template>
            </el-table-column>
        </el-table>
    
    </el-row>
</template>


