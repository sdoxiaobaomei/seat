import { Menus } from '../types/menu';

export const menuData: Menus[] = [
    {
        id: '0',
        title: '系统首页',
        index: '/index',
        icon: 'Odometer',
    },
    {
        id: '1',
        title: '订座',
        index: '1',
        icon: 'HomeFilled',
        children: [
            {
                id: '11',
                pid: '1',
                index: '/system-user',
                title: '按座位',
            },
            {
                id: '12',
                pid: '1',
                index: '/system-role',
                title: '按日期',
            }
        ],
    }
];