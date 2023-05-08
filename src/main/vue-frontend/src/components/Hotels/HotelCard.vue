<template>
    <div class="p-4 flex flex-col border border-neutral-200 bg-neutral-50 rounded">
        <div class="flex flex-row">
            <div class="m-1 w-[160px] h-[160px] bg-neutral-200 rounded"></div>
            <div class="flex-grow ml-4 flex flex-col">
                <div class="text-lg font-semibold">Отель "{{ hotel.name }}"</div>
                <div class="flex flex-row items-center gap-1 text-xs text-neutral-600 p-px">
                    Рейтинг<star-svg
                        v-for="star in [...Array(hotel.stars)]"
                        :key="star"
                        class="h-[14px] fill-yellow-400 text-yellow-400"
                    />
                </div>
                <div
                    class="w-full mt-6 flex flex-row justify-between border-l-2 border-neutral-300"
                    v-for="(room, i) in show ? hotel.rooms : [hotel.rooms[0]]"
                    :key="room.id"
                >
                    <div class="px-4 flex flex-col">
                        <h3 class="font-semibold">Номер на {{ room.beds }} человек</h3>
                        <p class="mt-2 text-sm text-green-700 font-medium">Бесплатная отмена бронирования</p>
                        <p class="text-sm text-green-700 font-medium">Предоплата не взимается</p>
                    </div>
                    <div class="flex flex-col justify-end">
                        <span class="text-xs text-neutral-700"
                            >{{ room.nights }} ночи, {{ $route.query.guests }} гостя</span
                        >
                        <h1 class="text-xl font-semibold">
                            {{ room.price }}
                        </h1>
                        <button class="mt-1 btn-black" @click="() => handleSelect(i)">Выбрать</button>
                    </div>
                </div>
                <div v-if="hotel.rooms.length > 1" class="mt-4">
                    <button class="btn-gray" @click="handleShowMore">{{ show ? "Скрыть" : "Показать еще" }}</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import StarSvg from "../svgs/StarSvg.vue";

export default {
    components: {
        StarSvg,
    },
    props: {
        hotel: {
            require: true,
        },
    },
    data() {
        return {
            show: false,
        };
    },

    methods: {
        handleShowMore() {
            this.show = !this.show;
        },

        handleSelect(i) {
            this.$store.commit("hotel/setShowModal", {
                id: this.hotel.rooms[i].id,
                name: this.hotel.name,
                beds: this.hotel.rooms[i].beds,
                checkIn: this.$route.query.checkInDate,
                checkOut: this.$route.query.checkOutDate,
                price: this.hotel.rooms[i].price,
            });
        },
    },
};
</script>
