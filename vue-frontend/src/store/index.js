import { createStore } from 'vuex';
import { adminModule } from './adminModule';
import { hotelModule } from './hotelModule';
import { flightModule } from './flightModule';

export default createStore({
    modules: {
        admin: adminModule,
        hotel: hotelModule,
        flight: flightModule
    },

});
