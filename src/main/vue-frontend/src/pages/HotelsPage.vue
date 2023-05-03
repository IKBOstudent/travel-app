<template>
    <div class="container mx-auto px-4 flex flex-col gap-4">
        <div>Результаты поиска: отели в городе {{ $route.query.city }}</div>
        <div v-if="!loading && rooms.length > 0" class="py-2 flex flex-col gap-2">
            <hotel-card
                v-for="room in rooms"
                :key="room.id"
                :room="room"
                :selected="selected && selected === room.id"
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
            rooms: [],
            loading: true,
            selected: null,
        };
    },
    methods: {
        async fetchRooms() {
            try {
                console.log(this.rooms);
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
        this.fetchRooms();
    },
};
</script>
