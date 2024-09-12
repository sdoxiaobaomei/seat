<script setup lang="ts">
    import { ref } from 'vue';
    import calendarVue from '@/components/calendarVue.vue';
    import { getSeats } from '@/api/api';

    const seats = ref([]);
    const getData = async () => {
        const res = await getSeats();
        seats.value = res.data;
    }
    getData();
</script>

<template>
    <el-scrollbar max-height="580px">
        <div class="content">

            <el-tabs type="border-card" class="tab_card">
                <el-tab-pane v-for="seat in seats" :label="seat.name">
                    <calendarVue :seat="seat"/>
                </el-tab-pane>
            </el-tabs>
        </div>
    </el-scrollbar>
</template>

<style scss>
.content{
    position: relative;
    display: flex;
    overflow-x: auto;
    
}
.tab_card{
    width: 1000px;
}

</style>
