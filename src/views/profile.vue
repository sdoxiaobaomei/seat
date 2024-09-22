<template>
     <el-form label-position="left" :model="form" label-width="auto" style="max-width: 600px">
        <el-form-item label="昵称">
            <el-input disabled v-model="form.displayname" />
        </el-form-item>
        <el-form-item label="组">
            <el-select disabled v-model="form.group" placeholder="please select your group">
                <el-option label="EE" value="EE" />
                <el-option label="Galaxy" value="Galaxy" />
                <el-option label="Rocket" value="Rocket" />
                <el-option label="CM" value="CM" />
            </el-select>
        </el-form-item>
        


     </el-form>
</template>

<script setup lang="ts">
import { getUserByUsername } from '@/api/api';
import { reactive } from 'vue';

const username = localStorage.getItem('username');
const form = reactive({
    displayname: '',
    group: '',
    date1: '',
    date2: '',
    delivery: false,
    type: [],
    resource: '',
    desc: '',
})

const getUserInfo = async () =>{

    const userInfo = await getUserByUsername(username);
    console.log('userinfo: ',userInfo.data[0])
    form.displayname = userInfo.data[0].displayname;
    form.group = userInfo.data[0].group;
}
getUserInfo();


const onSubmit = () => {
  console.log('submit!')
}
</script>

<styple scoped>

</styple>