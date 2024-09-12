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
            console.log(item)
            let key = item.date;
            let value = item.username;
            newEntry[key] = value;
        });
        
        console.log("new entry: ", newEntry);
        tableData.value.push(newEntry);
    });
    console.log("table data: ", tableData)
}
getData();

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
                prop="id"
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


