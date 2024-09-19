import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
// import { usePermissStore } from '../store/permiss';
import index from '../views/Index.vue';
import login from '../views/pages/login.vue';
import register from '../views/pages/register.vue';
import dashboard from '@/views/dashboard.vue';
import NProgress from 'nprogress';
import home from '@/views_mobile/home.vue'
import 'nprogress/nprogress.css';

// const routes: RouteRecordRaw[] = [
//     {
//         path: '/',
//         redirect: '/dashboard',
//     },
//     {
//         path: '/',
//         name: 'Home',
//         component: index,
//         children: [
//             {
//                 path: '/dashboard',
//                 name: 'dashboard',
//                 meta: {
//                     title: '日历',
//                     noAuth: true,
//                 },
//                 component: () => import(/* webpackChunkName: "dashboard" */ '../views/dashboard.vue'),
//             },
//             {
//                 path: '/login',
//                 name: 'login',
//                 component: login,
//             },
//             {
//                 path: '/register',
//                 name: 'Register',
//                 component: register,
//             },
//         ],
//     },
//     { path: '/:path(.*)', redirect: '/404' }, 
// ];

const routes: RouteRecordRaw[]= [
    {
        path: '/',
        redirect: '/home',
    },
    // {
    //     path: '/',
    //     name: 'index',
    //     component: index,
    //     children: [
    //         {
    //             path:'/dashboard',
    //             name:'dashboard',
    //             meta: {
    //                 title: '系统首页',
    //                 noAuth: false,
    //             },
    //             component: () => import('@/views/dashboard.vue'),
    //         },
    //         {
    //             path:'/calendar',
    //             name:'calendar',
    //             meta: {
    //                 title: '日历',
    //                 noAuth: false,
    //             },
    //             component: () => import('@/components/tabs.vue'),
    //         },
    //         {
    //             path: '/seat/manage',
    //             name: 'seat-manage',
    //             meta: {
    //                 title: '座位管理',
    //                 noAuth: false,
    //             },
    //             component: () => import('@/components/seatManage.vue'),
    //         }
    //     ],
    // },
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
    {
        path: '/home',
        name:'home',
        component: home,
        meta: {
            title: '手机端首页',
            noAuth: true,
        },
    }
];
 
const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
// 
// router.beforeEach((to, from, next) => {
//     // console.log('brefore each')
//     NProgress.start();
//     const role = localStorage.getItem('username');
//     // const permiss = usePermissStore();
    
//     if (!role && to.meta.noAuth !== true) {
//         next('/login');
//     // } else if (typeof to.meta.permiss == 'string' && !permiss.key.includes(to.meta.permiss)) {
//     //     // 如果没有权限，则进入403
//     //     next('/403');
//     } else {
//         next('/home');
//     }
// });

router.afterEach(() => {
    NProgress.done();
});

export default router;
