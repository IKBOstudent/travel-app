<template>
    <div class="max-w-5xl min-h-screen px-6 mx-auto">
        <nav class="pt-24">
            <h1 class="text-3xl font-bold text-center">Путешествуйте по всему миру</h1>
            <p class="m-3 text-lg text-center">Огромный выбор стран и городов</p>
            <div class="mt-8 flex flex-row border border-neutral-200 w-fit rounded">
                <div
                    v-for="(val, i) in ['Авиабилеты', 'Отели']"
                    :key="val"
                    class="flex justify-center m-1 py-2 cursor-pointer w-[120px] rounded"
                    :class="{ 'bg-neutral-200': active === i }"
                    @click="() => setActive(i)"
                >
                    {{ val }}
                </div>
            </div>
            <div class="mt-2 rounded w-full bg-neutral-50 border border-neutral-200 py-2 px-2 box-border">
                <flight-form v-if="active === 0" :column="false" />
                <hotel-form v-else :column="false" />
            </div>
        </nav>
    </div>
</template>

<script>
import FlightForm from "@/components/Flights/FlightForm.vue";
import HotelForm from "@/components/Hotels/HotelForm.vue";

export default {
    components: {
        FlightForm,
        HotelForm,
    },
    data() {
        return {
            active: 0,
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
