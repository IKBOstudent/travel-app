<template>
    <div class="container mx-auto px-4">
        <div class="flex flex-row">
            <div
                class="py-2 px-4 cursor-pointer"
                :class="{ 'bg-slate-300': active === 1 }"
                @click="() => setActive(1)"
            >
                Авиабилеты
            </div>
            <div
                class="py-2 px-4 cursor-pointer"
                :class="{ 'bg-slate-300': active === 2 }"
                @click="() => setActive(2)"
            >
                Отели
            </div>
        </div>
        <div class="p-4 pt-4 bg-slate-300">
            <flight-form v-if="active === 1" />
            <hotel-form v-else />
        </div>
    </div>
</template>

<script>
import FlightForm from '@/components/Flights/FlightForm.vue';
import HotelForm from '@/components/Hotels/HotelForm.vue';

export default {
    components: {
        FlightForm,
        HotelForm,
    },
    data() {
        return {
            active: 1,
        };
    },
    methods: {
        setActive(num) {
            this.active = num;
        },
    },
};
</script>

<style></style>
