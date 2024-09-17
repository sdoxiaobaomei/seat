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

    const activeTab = ref('1');
    const handleTabClick = (tab) => {
        console.log("Tab changed:", tab.key);
        activeTab.value = tab.name;
    };
</script>

<template>
    <el-scrollbar max-height="580px">
        <div class="content">
            <el-tabs type="border-card" class="tab_card" v-model="activeTab" @tab-click="handleTabClick" >
                <el-tab-pane v-for="seat in seats" :key="seat.id" :label="seat.name" :name="seat.id.toString()">
                    <calendarVue v-if="(activeTab === seat.id.toString())" :seat="seat"/>
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
