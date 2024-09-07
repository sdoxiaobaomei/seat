<script setup lang="ts">
import ticImgurl from '@/assets/img/tic.jpg';
import ttImgurl from '@/assets/img/TT.jpg';
import { getSeatBook } from '@/api/api';
import { computed, ref } from 'vue';

const today = new Date();
const currentMonth = today.getMonth() + 1;
const currentYear = today.getFullYear();
let dates = [];
let date=new Date(today.getFullYear(),today.getMonth(),1)
while (date.getMonth() + 1 === currentMonth) {
    dates.push(currentYear+'-'+currentMonth + '-' + date.getDate());
    date.setDate(date.getDate() + 1);
}
const tableData = ref();
const getData = async () => {
    const res = await getSeatBook();
    let seatBookList = res.data;
    console.log(seatBookList)
    seatBookList.forEach(element => {
        let map = new Map([]);
        map.set("seat", element.seat);
        // tableData.value.push({seat: element.seat})
        element.dates.forEach(item => {
            console.log(item)
            let key = item.date;
            let value = item.username;
            map.set(key, value);
        });
        // tableData.value = computed(()=>(map)).value;
        console.log(computed(()=>(map)).value);
    });
    console.log(tableData)
}
getData();
// const tableData = [
//     {
//         seat: 'A46-EE',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'B46-EE',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'C46-R2',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'A47-R2',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'B47-R2',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'C47-R2',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'D47-R2',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'A48-CM',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'B48',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'C48',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'D48',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'A49',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'B49',
//         "9-2": 'booked',
//         "9-3": 'booked',
//     },
//     {
//         seat: 'C49',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
//     {
//         seat: 'D49',
//         "9-3": 'booked',
//         "9-9": 'booked',
//     },
// ]

function isWorkday(date) {
    let dayInWeek = date.getDay();
    return !(dayInWeek === 0 || dayInWeek === 6);
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
        <el-table :data="tableData" height="400">
            <el-table-column 
                label="座位"
                prop="seat"
                fixed >

            </el-table-column>
            <el-table-column 
                v-for="(item, index) in dates" 
                :key="index"
                :prop="item"
                :label="item">
                <p v-if="!isWorkday(new Date(item))" style="background-color: brown;">休息</p>
            </el-table-column>
        </el-table>
    
    </el-row>
</template>


