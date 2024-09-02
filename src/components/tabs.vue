<script setup lang="ts">
    import { ref } from 'vue';
    import calendarVue from '@/components/calendarVue.vue';


    const seats = ref ([
        //EE
        { label: 'A46' },
        { label: 'B46' },
        //Room2
        { label: 'C46' },
        { label: 'A47' },
        { label: 'B47' },
        { label: 'C47' },
        { label: 'D47' },
        //CM
        { label: 'A48' },
        // Room1
        { label: 'B48' },
        { label: 'C48' },
        { label: 'D48' },
        { label: 'C49' },
        { label: 'D49' },
        //Flex
        { label: 'A49' },
        { label: 'B49' },
    ])
</script>

<template>
    <el-tabs type="border-card">
        <el-tab-pane v-for="seat in seats" :label="seat.label">
            <calendarVue/>
        </el-tab-pane>
    </el-tabs>
</template>

<style scss>

</style>
