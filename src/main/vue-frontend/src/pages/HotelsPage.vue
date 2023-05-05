<template>
    <div class="container mx-auto px-4 flex flex-col gap-4">
        <div>Результаты поиска:</div>
        <div v-if="!loading && rooms.length > 0" class="py-2 flex flex-col gap-2">
            Отели в городе {{ $route.query.city }}
            <br />
            на {{ $route.query.checkInDate }} - {{ $route.query.checkOutDate }}

            <hotel-card
                v-for="room in rooms"
                :key="room.id"
                :room="room"
                :selected="selected && selected === room.id"
                @select="setSelected"
            />
        </div>
        <div v-else-if="!loading && rooms.length === 0">Отелей не найдено</div>
        <div v-else>Загрузка...</div>
    </div>
</template>

<script>
// import axios from 'axios';

import HotelCard from '@/components/Hotels/HotelCard.vue';
import axios from 'axios';

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
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            const query = this.$route.query;

                            let url = `http://localhost:8080/api/rooms?`;
                            url += `city=${query.city}`;
                            url += `&guests=${query.guests}`;
                            url += `&check_in_date=${query.checkInDate}`;
                            url += `&check_out_date=${query.checkOutDate}`;

                            const { data } = await axios.get(url);
                            console.log(data);
                            this.rooms = data;
                            res();
                        } catch (e) {
                            console.log(e);
                            rej(e);
                        }
                    }, 1000);
                });
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
