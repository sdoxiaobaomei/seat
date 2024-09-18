<script setup lang="ts">
import { useSidebarStore } from '@/store/sidebar';
// import { useTabsStore } from '@/store/tabs';
import { CalendarInstance } from 'element-plus';
import vHeader from '../components/header.vue';
import vSidebar from '@/components/sidebar.vue';
import vTabs from '../components/tabs.vue';
import { useTabsStore } from '../store/tabs';
import {ref,onMounted,onBeforeUnmount }from 'vue';
import calendar from '../store/lunarDay';
import { ElMessageBox } from 'element-plus';
import calendarVue from '../components/calendarVue.vue';


const tabs = useTabsStore();
const datePicked = ref('');
const value = ref(new Date());
const disabledDate = ref(new Date(2024,8,10));
const Holidays = ref([
    '2023-01-01'
])

const sidebar = useSidebarStore();
// const Component = ref();

</script>

<template>
    <div class="wrapper">
        <el-container>
            <el-header>
                <v-header />

            </el-header>
            <el-container>
                <el-aside>
                    <v-sidebar />
                </el-aside>
                <el-main class="content-box" :class="{ 'content-collapse': sidebar.collapse }">
                    <!-- <transition name="move" mode="out-in"> -->

                        <router-view />
                    <!-- </transition> -->
                    <!-- <router-view v-slot="{ Component }">
                        <transition name="move" mode="out-in">
                            <component :is="Component" />
                        </transition>
                    </router-view> -->
                    <!-- <div class="content" > -->
                    <!-- </div> -->
                </el-main>
            </el-container>
        </el-container>
        <!-- <div class="content-box"> -->
            <!-- <v-tabs></v-tabs> -->
            <!-- <h2>content box</h2> -->
            
        <!-- </div> -->
    </div>

</template>

<style scoped>
.layout{
    height: 100%;
}
/* .custom-date-class {
   cursor: pointer; 
   其他你需要的样式 
} */

.content-box {
    position: absolute;
    left: 250px;
    right: 0;
    top: 70px;
    bottom: 0;
    padding-bottom: 30px;
    -webkit-transition: left 0.3s ease-in-out;
    transition: left 0.3s ease-in-out;
    background: #eef0fc;
    overflow: hidden;
    overflow-x: auto; /* 启用横向滚动 */
    overflow-y: auto; /* 启用纵向滚动 */
}
.content-collapse {
    left: 65px;
}
.move-enter-active,
.move-leave-active {
	transition: opacity 0.5s ease;
}

.move-enter-from,
.move-leave-to {
	opacity: 0;
}
</style>
