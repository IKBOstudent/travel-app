<template>
    <hotel-modal v-if="$store.state.hotel.info !== null" />
    <div>
        <div class="max-w-7xl min-h-screen px-6 mx-auto">
            <aside class="fixed top-0 bottom-0 w-[340px]">
                <router-link to="/" exact class="mt-12 mx-8 btn-gray">Вернуться в меню</router-link>
                <div class="m-8 mt-4 border border-neutral-200 bg-neutral-50 rounded">
                    <h3 class="px-8 pt-4 text-lg font-semibold">Поиск</h3>
                    <hotel-form class="pt-4 p-4" :column="true" @search="fetchHotels" />
                </div>
            </aside>
            <div class="ml-[340px] py-12 min-h-screen">
                <div class="flex flex-col py-10">
                    <p v-if="resultStatus === 0" class="text-lg text-semibold text-neutral-500">
                        Ищем подходящие варианты...
                    </p>

                    <p v-if="resultStatus === 1 && hotels.length === 0" class="text-xl text-semibold">
                        Отели не найдены :(
                    </p>
                    <p v-if="resultStatus === 2" class="text-red-600 flex flex-row items-center">
                        <error-svg class="mr-2 h-5" />
                        Произошла ошибка. Попробуйте еще раз...
                    </p>

                    <div v-if="resultStatus === 1 && hotels.length > 0" class="flex flex-col">
                        <h3 class="text-xl font-semibold">
                            Отели в городе {{ $route.query.city }}: найдено {{ hotels.length }} вариантов
                        </h3>

                        <div class="mt-4 flex flex-col gap-4">
                            <hotel-card v-for="hotel in hotels" :key="hotel.id" :hotel="hotel" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// import axios from 'axios';
import HotelForm from "@/components/Hotels/HotelForm.vue";
import HotelCard from "@/components/Hotels/HotelCard.vue";
import ErrorSvg from "@/components/svgs/ErrorSvg.vue";
import HotelModal from "@/components/Hotels/HotelModal.vue";
import axios from "axios";

const statuses = { loading: 0, ok: 1, error: 2 };

export default {
    components: {
        HotelCard,
        HotelForm,
        ErrorSvg,
        HotelModal,
    },
    data() {
        return {
            hotels: [],
            resultStatus: statuses.loading,
        };
    },
    methods: {
        async fetchHotels() {
            try {
                this.resultStatus = statuses.loading;

                const query = this.$route.query;

                let url = `/api/rooms?`;
                url += `city=${query.city}`;
                url += `&guests=${query.guests}`;
                url += `&check_in_date=${query.checkInDate}`;
                url += `&check_out_date=${query.checkOutDate}`;

                const { data } = await axios.get(url);

                const nights = (new Date(query.checkOutDate) - new Date(query.checkInDate)) / (1000 * 60 * 60 * 24);

                let hotels = [];
                if (data.length > 0) {
                    let currentId = data[0];
                    data.forEach(room => {
                        const formRoom = {
                            id: room.id,
                            beds: room.beds,
                            price: new Intl.NumberFormat("ru-RU", {
                                style: "currency",
                                currency: "RUB",
                            }).format(room.nightPrice * nights),
                            nights,
                        };
                        if (room.hotel.id !== currentId) {
                            currentId = room.hotel.id;

                            hotels.push({
                                ...room.hotel,
                                rooms: [formRoom],
                            });
                        } else {
                            hotels[hotels.length - 1].rooms.push(formRoom);
                        }
                    });
                }
                console.log(hotels);
                this.hotels = hotels;

                this.resultStatus = statuses.ok;
            } catch (e) {
                this.resultStatus = statuses.error;
            }
        },
    },
    mounted() {
        this.fetchHotels();
    },
};
</script>
