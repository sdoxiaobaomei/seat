<script setup lang="ts">
import ticImgurl from '@/assets/img/tic.jpg';
import ttImgurl from '@/assets/img/TT.jpg';
import { getSeatBook } from '@/api/api';
import { ref } from 'vue';

const today = new Date();
const currentMonth = today.getMonth() + 1;
let dates = [];
let date=new Date(today.getFullYear(),today.getMonth(),1)
while (date.getMonth() + 1 === currentMonth) {
    dates.push(currentMonth + '-' + date.getDate());
    date.setDate(date.getDate() + 1);
}
const tableData = ref([]);
const getData = async () => {
    const res = await getSeatBook();
    tableData.value = res.data;
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

function isWorkday(day) {
    
    return !(day === 5 || day === 6);
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
                <p v-if="!isWorkday(new Date(item).getDay())" style="background-color: brown;">休息</p>
            </el-table-column>
        </el-table>
    
    </el-row>
</template>


