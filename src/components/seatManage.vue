<script setup lang="ts">
import { reactive, ref } from 'vue';

const tableData = [
  {
    seat: 'B38',
    group: 'EE',
    area: 'TT',
    tag: 'Home',
  },
  {
    seat: 'C38',
    group: 'EE',
    area: 'TT',
    tag: 'Office',
  },
  {
    seat: 'A39',
    group: 'Rocket',
    area: 'TT',
    tag: 'Home',
  }
]

const handleClick = () => {
    console.log('click')
}

const dialogFormVisible = ref(false);
const formLabelWidth = '140px'
const handleEdit = () => {
    dialogFormVisible.value = true;
    console.log('edit')
}

const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})
</script>

<template>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column fixed prop="seat" label="Seat" width="150" />
        <el-table-column prop="group" label="Group" width="120" />
        <el-table-column prop="area" label="Area" width="120" />
        <el-table-column fixed="right" label="Operations" min-width="120">
            <template #default>
                <el-button link type="primary" size="small" @click="handleClick">
                    Detail
                </el-button>
                <el-button link type="primary" size="small" @click="handleEdit">Edit</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-dialog v-model="dialogFormVisible" title="Seat Detail" width="500">
        <el-form :model="form">
            <el-form-item label="Seat name" :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="Group" :label-width="formLabelWidth">
                <el-input v-model="form.region" placeholder="Please input a group"/>
                
                
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Cancel</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">
                Confirm
                </el-button>
            </div>
        </template>
    </el-dialog>
  </template>