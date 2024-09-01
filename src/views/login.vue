<script setup lang="ts">
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { ElMessage } from 'element-plus';
  
  import {useUserStore} from '../store/user';
  

  const loginForm = ref({
    email: '',
    password: '',
  });
  
  const rules = {
    email: [
      { required: true, message: '请输入邮箱', trigger: 'blur' },
      { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] },
    ],
    password: [{ required: true, message: "请输入密码 trigger:" }],
  }
  
  const router = useRouter();
  
  const handleLogin = () => {
    const { email, password } = loginForm.value;
    console.log('登录:', email, password);
    ElMessage.success('登录成功！');
    router.push('/index');
    useUserStore().setUserInfo(email)
  }
  
  const goToRegister = () => {
    router.push('/register');
  }
  </script>
  
<template>
    <div class="login-container">
      <el-card class="login-card">
        <h2 class="login-title">用户登录</h2>
        <el-form :model="loginForm" ref="loginForm" :rules="rules" label-width="100px">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="loginForm.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleLogin">登录</el-button>
            <el-button @click="goToRegister">注册</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  
  <style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  
  .login-card {
    width: 400px;
  }
  </style>
  