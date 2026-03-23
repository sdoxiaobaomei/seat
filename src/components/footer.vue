<template>

        <!-- <el-row justify="center">
           
            <el-col :span="6" >1</el-col>
            <el-col :span="6" >2</el-col>
            <el-col :span="6" >3</el-col>
            <el-col :span="6" >
                <el-button :icon="User" circle />
            </el-col>
        </el-row> -->
        <div class="mobile-footer">
            <el-row justify="space-around" :align="`middle`" class="footer-row">
                <el-col :span="4" class="footer-item" @click="goToHome">
                    <el-button type="text"  circle>
                    <el-icon><HomeFilled /></el-icon>
                    </el-button>
                    <p>Home</p>
                </el-col>
                <el-col :span="4" class="footer-item">
                    <el-button type="text" @click="goToOrders" circle disabled>
                    <el-icon><Calendar /></el-icon>
                    </el-button>
                    <p>Orders</p>
                </el-col>
                <el-col :span="4" class="footer-item" @click="goToProfile">
                    <el-button type="text"  circle>
                    <el-icon><User /></el-icon>
                    </el-button>
                    <p>Profile</p>
                </el-col>
            </el-row>
        </div>
</template>

<script setup lang="ts">
import { User, HomeFilled , ChatDotRound, Calendar } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';

const router = useRouter();
const goToHome = () => {
    router.push('/dashboard')
}

const goToOrders = () => {
    router.push('/orders')
}

const goToProfile = () => {
    router.push('/profile');
}
</script>

<style scoped>
.mobile-footer {
  /* position: fixed; */
  /* bottom: 0; */
  width: 100%;
  background-color: #fff;
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
  border-top: 1px solid #ddd;
  padding: 0;  /* 确保没有内部的边距 */
  margin: 0;   /* 确保没有外部的边距 */
}

.footer-row {
  padding: 0;  /* 去除行的内边距 */
  width: 100%;
  margin: 0;
}

.footer-item {
  text-align: center;
  color: #606266;
}

.footer-item p {
  margin: 0;
  font-size: 12px;
}

.el-button {
  color: #606266;
  font-size: 20px;
}

.el-button:hover {
  color: #409EFF;
}

.el-button:active {
  color: #0066CC;
}

</style>