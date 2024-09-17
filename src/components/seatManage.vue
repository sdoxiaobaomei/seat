<script setup lang="ts">
    import { addSeat, getSeats } from '@/api/api';
    import { reactive, ref } from 'vue';

    // const tableData = ref([]);
    const tableData = ref([]);
    const getData = async () => {
        const res = await getSeats();
        console.log('seats: ', res.data)
        tableData.value = res.data;
        // return res.data;
    }
    getData();

    const handleClick =  () => {
        console.log()
    
        // tableData.value = getData();
    }

    const dialogFormVisible = ref(false);
    const dialogAddSeatVisible = ref(false);
    const formLabelWidth = '140px'
    const handleEdit = () => {
        dialogFormVisible.value = true;
        console.log('edit')
    }

    const handleAddBtnClick = () => {
        dialogAddSeatVisible.value = true;
        console.log("add a seat")
    }

    const handleAddSeatConfirmClick = (form:{name:string,group:string}) => {
        dialogAddSeatVisible.value = false;
        console.log("add a seat: ", form)
        const res = addSeat(form);
        console.log("handleAddSeatConfirmClick: ", res)
    }


    const form = reactive({
        name: '',
        group: '',
    })
</script>

<template>
    <el-button type="primary" text @click="handleAddBtnClick">
        + 添加
    </el-button>
    <el-dialog v-model="dialogAddSeatVisible" title="Seat Detail" width="500">
        <el-form :model="form">
            <el-form-item label="Seat name" :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="Group" :label-width="formLabelWidth">
                <el-input v-model="form.group" placeholder="Please input a group"/>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogAddSeatVisible = false">Cancel</el-button>
                <el-button type="primary" @click="handleAddSeatConfirmClick(form)">
                    Confirm
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-table :data="tableData" style="width: 100%">
        <el-table-column fixed prop="name" label="Seat" width="150" />
        <el-table-column prop="group" label="Group" width="120" />
        <el-table-column prop="area" label="Area" width="120" />
        <el-table-column fixed="right" label="Operations" min-width="120">
            <template #default>
                <el-button link type="primary" size="small" @click="handleClick" disabled>
                    Detail
                </el-button>
                <el-button link type="primary" size="small" @click="handleEdit" disabled>Edit</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-dialog v-model="dialogFormVisible" title="Seat Detail" width="500">
        <el-form :model="form">
            <el-form-item label="Seat name" :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="Group" :label-width="formLabelWidth">
                <el-input v-model="form.group" placeholder="Please input a group"/>
                
                
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