import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
// import { usePermissStore } from '../store/permiss';
import index from '../views/Index.vue';
import login from '../views/pages/login.vue';
import register from '../views/pages/register.vue';
import dashboard from '@/views/dashboard.vue';
import NProgress from 'nprogress';
import home from '@/views_mobile/home.vue'
import 'nprogress/nprogress.css';


const routes: RouteRecordRaw[]= [
    {
        path: '/',
        redirect: '/dashboard',
    },
    {
        path: '/',
        name: 'home',
        component: home,
        children: [
            {
                path:'/dashboard',
                name:'dashboard',
                meta: {
                    title: '详情页',
                    noAuth: false,
                },
                component: () => import('@/views/dashboard.vue'),
            },
            {
                path: '/profile',
                name: 'profile',
                meta: {
                    title: '个人信息',
                    noAuth: false,
                },
                component: () => import('@/views/profile.vue'),
            },
            {
                path: '/orders',
                name: 'orders',
                meta: {
                    title: '订单',
                    noAuth: false,
                },
                component: () => import('@/views/orders.vue'),
            }
        ],
    },
    {
        path: '/register',
        name: 'Register',
        component: register,
    },
    {
        path: '/login',
        name: 'login',
        meta: {
            title: '系统首页',
            noAuth: true,
        },
        component: login,
    },
];
 
const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
// 
router.beforeEach((to, from, next) => {
    // console.log('brefore each')
    NProgress.start();
    const role = localStorage.getItem('username');
    // const permiss = usePermissStore();
    
    if (!role && to.meta.noAuth !== true) {
        next('/login');
    // } else if (typeof to.meta.permiss == 'string' && !permiss.key.includes(to.meta.permiss)) {
    //     // 如果没有权限，则进入403
    //     next('/403');
    } else {
        next();
    }
});

router.afterEach(() => {
    NProgress.done();
});

export default router;
