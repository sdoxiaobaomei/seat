<script setup lang="ts">
import { onMounted } from 'vue';
// import { useSidebarStore } from '../store/sidebar';
// import { useRouter } from 'vue-router';
// import imgurl from '../assets/img/img.jpg';

const username: string | null = localStorage.getItem('vuems_name');
const message: number = 2;

// const sidebar = useSidebarStore();
// // 侧边栏折叠
// const collapseChage = () => {
//     sidebar.handleCollapse();
// };

onMounted(() => {
    // if (document.body.clientWidth < 1500) {
    //     collapseChage();
    // }
});

// 用户名下拉菜单选择事件
// const router = useRouter();
// const handleCommand = (command: string) => {
//     if (command == 'loginout') {
//         localStorage.removeItem('vuems_name');
//         router.push('/login');
//     } else if (command == 'user') {
//         router.push('/ucenter');
//     }
// };

const setFullScreen = () => {
    if (document.fullscreenElement) {
        document.exitFullscreen();
    } else {
        document.body.requestFullscreen.call(document.body);
    }
};
</script>

<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="header-left">
            <!-- <img class="logo" src="../assets/img/logo.svg" alt="" /> -->
            <div class="web-title">订座</div>
            <!-- <div class="collapse-btn" @click="collapseChage">
                <el-icon v-if="sidebar.collapse">
                    <Expand />
                </el-icon>
                <el-icon v-else>
                    <Fold />
                </el-icon>
            </div> -->
        </div>
        <div class="header-right">
            <!-- <div class="header-user-icon"> -->
                <!-- 用户头像 -->
                <!-- <el-avatar class="user-avator" :size="30" :src="imgurl" /> -->
            <!-- </div> -->
        </div>
    </div>
</template>

<style scoped>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    color: #fff;
    background-color: #242f42;
    border-bottom: 1px solid #ddd;
}
.header-left {
    display: flex;
    align-items: center;
    padding-left: 20px;
    height: 100%;
}
.web-title {
    margin: 0 40px 0 10px;
    font-size: 22px;
}
.header-right {
    float: right;
    padding-right: 50px;
}
</style>