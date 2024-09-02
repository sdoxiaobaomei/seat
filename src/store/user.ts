import { defineStore } from 'pinia';
// import store from 'pinia-plugin-persistedstate';

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        userInfo: '',
    }),
    actions:{
        setUserInfo(userInfo) {
            this.userInfo = userInfo;
        }
    },
    // persist: {
    //     enabled: true,
    // }
});

// export const pinia = new Pinia().use(store);