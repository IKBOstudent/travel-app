import { createStore } from 'vuex';
import { adminModule } from './adminModule';

export default createStore({
    modules: {
        admin: adminModule,
    },
});
