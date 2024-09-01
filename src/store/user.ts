import { defineStore } from 'pinia';

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        userInfo: '',
    }),
    actions:{
        setUserInfo(userInfo) {
            this.userInfo = userInfo;
        }
    }
});