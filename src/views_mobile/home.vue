<template>
    <el-container class="mainpage">
        <el-header>
            <Header ></Header>
        </el-header>
        
        <el-main class="content-box">
            <!-- <router-view v-slot="{ Component }">
                <transition name="move" mode="out-in">
                    <component :is="Component" />
                </transition>
            </router-view> -->
            <!-- <Dashboard /> -->
            <router-view/>
        </el-main>

        <el-footer>
            <Footer ></Footer>
        </el-footer>
    </el-container>
    
    
</template>
<script setup lang="ts">
import Header from '@/components/header.vue';
import Dashboard from '@/views/dashboard.vue';
import Footer from "@/components/footer.vue";


</script>

<style scoped>
.mainpage {
    display: flex;
    flex-direction: column;
    height: 100vh;
    padding: 0;  /* 确保没有内部的边距 */
    margin: 0;   /* 确保没有外部的边距 */
}

.el-header, .el-main, .el-footer {
    padding: 0;  /* 确保没有内部的边距 */
    margin: 0;   /* 确保没有外部的边距 */
}
.el-main {
    padding-top: 10px;
}
.custom-footer {
  width: 100%;         /* 确保footer占满屏幕宽度 */
  margin: 0;           /* 去除任何外边距 */
  padding: 0;          /* 去除内边距 */
  box-sizing: border-box; /* 确保padding不会影响布局 */
}
</style>