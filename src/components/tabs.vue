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
        console.log("Tab changed:", tab);
        activeTab.value = tab.name;
    };
</script>

<template>
    <div class="tabs-container">

        <el-scrollbar max-height="780px">
            <div class="content">
                <el-tabs type="border-card" class="tab_card" v-model="activeTab" @tab-click="handleTabClick" >
                    <el-tab-pane v-for="seat in seats" :key="seat.id" :label="seat.name" :name="seat.id.toString()">
                        <template v-if="(activeTab === seat.id.toString())" >
                            <el-scrollbar max-height="430px">
                                <calendarVue  class="calendar" :key="activeTab" :seat="seat"/>
                            </el-scrollbar>
                        </template>
                        
                        <template v-else>
                            <div>Loading...</div> <!-- 占位元素，确保 el-tab-pane 有内容 -->
                        </template>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </el-scrollbar>
    </div>
</template>

<style scss>
.content{
    position: relative;
    display: flex;
    overflow-x: auto;
    
}
.tab_card{
    width: 1000px;
    overflow-y: scroll;
}
.calendar {
    width: 900px;
}
/* .tabs-container {
    max-height: 300px;
    overflow-y: auto;
} */
</style>
