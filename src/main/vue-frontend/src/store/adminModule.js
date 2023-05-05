import axios from 'axios';

export const adminModule = {
    state: {
        authInterceptor: null,
    },
    getters: {},
    mutations: {
        setInterceptor(state, newInterceptor) {
            if (newInterceptor === null) {
                axios.interceptors.request.eject(this.axiosInterceptor);
            }
            state.authInterceptor = newInterceptor;
        },
    },
    actions: {},
};
