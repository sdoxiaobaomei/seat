<script setup>
import IndexVue from './views/Index.vue';
import { ElConfigProvider } from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import Home from './views_mobile/home.vue';


const goBack = () => {
  console.log('go back')
}
</script>

<template>
  <el-config-provider :locale="zhCn">
    <!-- <router-view/> -->
    <!-- <IndexVue /> -->
    <Home/>
    <!-- <router-view v-slot="{ Component }">
        <transition name="move" mode="out-in">
            <component :is="Component" />
        </transition>
    </router-view> -->

  </el-config-provider>
</template>

<style scoped>
@import './assets/css/main.css';
</style>
