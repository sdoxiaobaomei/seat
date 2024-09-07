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
    

    // const seats2 = ref ([
    //     //EE
    //     { name: 'A46' },
    //     { name: 'B46' },
    //     //Room2
    //     { name: 'C46' },
    //     { name: 'A47' },
    //     { name: 'B47' },
    //     { name: 'C47' },
    //     { name: 'D47' },
    //     //CM
    //     { name: 'A48' },
    //     // Room1
    //     { name: 'B48' },
    //     { name: 'C48' },
    //     { name: 'D48' },
    //     { name: 'C49' },
    //     { name: 'D49' },
    //     //Flex
    //     { name: 'A49' },
    //     { name: 'B49' },
    // ])
    // console.log(seats2)
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
