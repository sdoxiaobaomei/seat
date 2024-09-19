<template>
    <el-container class="mainpage">
        <el-header>
            <Header ></Header>
        </el-header>
        
        <el-main class="content-box">
            <Dashboard />

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
}
.el-footer {
    background-color: yellowgreen;
}
</style>