import { Menus } from '../types/menu';

export const menuData: Menus[] = [
    {
        id: '0',
        title: '系统首页',
        index: '/dashboard',
        icon: 'Odometer',
    },
    {
        id: '1',
        title: '工区',
        index: '1',
        icon: 'HomeFilled',
        children: [
            {
                id: '11',
                pid: '1',
                index: '/tic',
                title: 'TT - 天堂',
            },
            {
                id: '12',
                pid: '1',
                index: '/',
                title: 'TIC - 滨江',
            }
        ],
    },
    {
        id: '2',
        title: '座位管理',
        index: '/seat/manage',
        icon: 'HomeFilled'
    }
];