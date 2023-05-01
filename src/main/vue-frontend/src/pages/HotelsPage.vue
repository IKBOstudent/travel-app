<template>
    <div class="container mx-auto px-4 flex flex-col gap-4">
        <div>Результаты поиска: отели в городе {{ $route.query.city }}</div>
        <div v-if="!loading && hotels.length > 0" class="py-2 flex flex-col gap-2">
            <hotel-card
                v-for="hotel in hotels"
                :key="hotel.id"
                :hotel="hotel"
                :selected="selected && selected === hotel.id"
                @select="setSelected"
            />
        </div>
        <div v-else-if="!loading && hotels.length === 0">Отелей не найдено</div>
        <div v-else>Загрузка...</div>
    </div>
</template>

<script>
// import axios from 'axios';

import HotelCard from '@/components/Hotels/HotelCard.vue';

export default {
    components: {
        HotelCard,
    },
    data() {
        return {
            hotels: [
                {
                    id: 352,
                    city: 'Paris',
                    name: 'First',
                    stars: 3,
                    checkInDate: '2023-01-14',
                    checkOutDate: '2023-01-17',
                    roomBeds: 2,
                    price: 66000,
                },
                {
                    id: 353,
                    city: 'Paris',
                    name: 'Second',
                    stars: 4,
                    checkInDate: '2023-01-14',
                    checkOutDate: '2023-01-17',
                    roomBeds: 2,
                    price: 86000,
                },
            ],
            loading: true,
            selected: null,
        };
    },
    methods: {
        async fetchHotels() {
            try {
                console.log(this.hotels);
            } catch (e) {
                alert('Error', e);
            } finally {
                this.loading = false;
            }
        },

        setSelected(id) {
            this.selected = id;
        },
    },
    mounted() {
        this.fetchHotels();
    },
};
</script>
