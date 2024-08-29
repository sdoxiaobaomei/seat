import { createApp } from 'vue';
import { createPinia } from 'pinia';
// import './style.css';
import App from './App.vue';
import ElementPlus from 'element-plus';
import router from './router';
import 'element-plus/dist/index.css'

const app = createApp(App);
app.use(createPinia())
app.use(router);
app.use(ElementPlus).mount('#app');