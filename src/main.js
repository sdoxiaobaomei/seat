import { createApp } from 'vue';
import { createPinia } from 'pinia';
// import './style.css';
import App from './App.vue';
import ElementPlus from 'element-plus';
import router from './router';
import 'element-plus/dist/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';


const app = createApp(App);
app.use(createPinia())
app.use(router);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(ElementPlus).mount('#app');