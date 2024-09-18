<script setup lang="ts">
import { onMounted } from 'vue';
import { UserFilled } from '@element-plus/icons-vue';
import { useSidebarStore } from '../store/sidebar';
// import { useRouter } from 'vue-router';
// import imgurl from '../assets/img/img.jpg';


const username: string | null = localStorage.getItem('username');
const message: number = 2;

const sidebar = useSidebarStore();
// 侧边栏折叠
const collapseChage = () => {
    sidebar.handleCollapse();
};

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


// 登录方法，跳转到登录页面
const login = () => {
  // 假设登录页面路径为 '/login'
  window.location.href = '/login';
};

// 登出方法，清除本地存储并刷新页面
const logout = () => {
  localStorage.removeItem('username');
  window.location.reload();  // 刷新页面或者导航到登录页
};

// const setFullScreen = () => {
//     if (document.fullscreenElement) {
//         document.exitFullscreen();
//     } else {
//         document.body.requestFullscreen.call(document.body);
//     }
// };
const user=localStorage.getItem('username');
</script>

<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="header-left">
            <!-- <img class="logo" src="../assets/img/logo.svg" alt="" /> -->
            <div class="web-title">订座</div>
            <div class="collapse-btn" @click="collapseChage" ref="ref1">
                
                <el-icon v-if="sidebar.collapse">
                    <span>展开</span>
                    <!-- <Expand /> -->
                </el-icon>
                <el-icon v-else>
                    <span>折叠</span>
                    <!-- <Fold /> -->
                </el-icon>
            </div>
        </div>
        <div class="header-right">
            <div class="header-user-icon">
                <span v-if="user">{{ user+' ' }} </span>
                <el-avatar :size="25">
                    <el-icon color="black"><UserFilled /></el-icon>
                </el-avatar>
                <el-button v-if="!user" size="small" type="primary" @click="login">Login</el-button>
                <el-button v-else size="small" type='warning' @click="logout" class="logout-button" link>Logout</el-button>
            </div>
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
    height: 100%;
    color: #fff;
    background-color: #242f42;
    border-bottom: 1px solid #ddd;
}
.header-left {
    display: flex;
    align-items: center;
    padding-left: 20px;
    height: 100%;
    justify-content: center;
    
}
.web-title {
    margin: 0 40px 0 10px;
    font-size: 22px;
}
.header-right {
    float: right;
    padding-right: 50px;
}
.header-user-icon{
    display: flex;
    align-items: center;
    gap: 10px; /* 使用 gap 为子元素添加10px的间隔 */
}

/* 自定义样式的 logout 按钮 */
.logout-button {
  font-weight: bold;          /* 加粗 */
  text-decoration: underline; /* 下划线 */
  color: white;               /* 按钮字体颜色 */
  padding: 0;
  margin: 0;
}
</style>