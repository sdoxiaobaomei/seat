<script setup>
import IndexVue from './views/Index.vue';
import { ElConfigProvider } from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';


const goBack = () => {
  console.log('go back')
}
</script>

<template>
  <el-config-provider :locale="zhCn">
    <!-- <router-view/> -->
    <IndexVue />

  </el-config-provider>
</template>

<style scoped>
@import './assets/css/main.css';
</style>
