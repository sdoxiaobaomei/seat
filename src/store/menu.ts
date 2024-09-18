import { Menus } from '../types/menu';

export const menuData: Menus[] = [
    {
        id: '0',
        title: '系统首页',
        index: '/dashboard',
        class: 'menu_dashboard',
        icon: 'Odometer',
    },
    {
        id: '1',
        title: '订阅日历',
        index: '/calendar',
        class:'menu_calendar',
        icon: 'HomeFilled',
        // children: [
        //     {
        //         id: '11',
        //         pid: '1',
        //         index: '/tt',
        //         title: 'TT - 天堂',
        //     },
        //     {
        //         id: '12',
        //         pid: '2',
        //         index: '/',
        //         title: 'TIC - 滨江',
        //     }
        // ],
    },
    {
        id: '2',
        title: '座位管理',
        index: '/seat/manage',
        class:'menu_seat_manage',
        icon: 'HomeFilled'
    }
];