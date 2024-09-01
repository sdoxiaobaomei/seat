import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
// import { usePermissStore } from '../store/permiss';
import index from '../views/Index.vue';
import login from '../views/login.vue';
import register from '../views/register.vue';
// import NProgress from 'nprogress';
// import 'nprogress/nprogress.css';

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

const routes= [
    {
        path: '/',
        name: 'Login',
        component: login,
    },
    {
        path: '/register',
        name: 'Register',
        component: register,
    },
    {
        path: '/index',
        name: 'Index',
        component: index,
    },
];
 
const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

// router.beforeEach((to, from, next) => {
//     NProgress.start();
//     const role = localStorage.getItem('vuems_name');
//     const permiss = usePermissStore();

//     if (!role && to.meta.noAuth !== true) {
//         next('/login');
//     } else if (typeof to.meta.permiss == 'string' && !permiss.key.includes(to.meta.permiss)) {
//         // 如果没有权限，则进入403
//         next('/403');
//     } else {
//         next();
//     }
// });

// router.afterEach(() => {
//     NProgress.done();
// });

export default router;
