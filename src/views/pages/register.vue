<script setup lang="ts">
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { ElMessage } from 'element-plus';
  
  const registerForm = ref({
    email: '',
    password: '',
    confirmPassword: '',
  });
  
  const rules = {
    email: [
      { required: true, message: '请输入邮箱', trigger: 'blur' },
      { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] },
    ],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    confirmPassword: [
      { required: true, message: '请确认密码', trigger: 'blur' },
      {
        validator: (rule, value, callback) => {
          if (value !== registerForm.value.password) {
            callback(new Error('两次密码不一致'));
          } else {
            callback();
          }
        },
        trigger: 'blur',
      },
    ],
  };
  
  const router = useRouter();
  
  const handleRegister = () => {
    const { email, password } = registerForm.value;
    console.log('注册:', email, password);
    ElMessage.success('注册成功！');
  };
  
  const goToLogin = () => {
    router.push('/');
  };
  </script>

<template>
    <div class="register-container">
      <el-card class="register-card">
        <h2 class="register-title">用户注册</h2>
        <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="100px">
   <el-form-item label="邮箱" prop="email">
            <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请确认密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleRegister">注册</el-button>
            <el-button @click="goToLogin">登录</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  
  <style scoped>
  .register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  
  .register-card {
    width: 400px;
  }
  </style>
  