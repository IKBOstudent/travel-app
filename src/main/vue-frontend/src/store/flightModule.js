export const flightModule = {
    namespaced: true,
    state: {
        info: null,
    },
    getters: {},
    mutations: {
        setShowModal(state, newInfo) {
            console.log("newinfo", newInfo)
            state.info = newInfo;
        },
    },
    actions: {},
};
