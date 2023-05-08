import FlightsPage from '@/pages/FlightsPage.vue';
import HotelsPage from '@/pages/HotelsPage.vue';
import HomePage from '@/pages/HomePage.vue';
import AdminPage from '@/pages/AdminPage.vue';

import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        component: HomePage,
    },
    {
        path: '/flights',
        component: FlightsPage,
    },
    {
        path: '/hotels',
        component: HotelsPage,
    },
    {
        path: '/admin',
        component: AdminPage,
    },
];

const router = createRouter({
    routes,
    history: createWebHistory(),
});

export default router;
