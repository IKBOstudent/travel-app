import { createApp } from 'vue';
import App from './App';
import './index.css';
import router from './router/router';
import store from './store';

createApp(App).use(router).use(store).mount('#app');
