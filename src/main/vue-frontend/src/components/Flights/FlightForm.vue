<template>
    <div>
        <form @submit.prevent="handleFindFlight" class="container w-80 flex flex-col gap-2">
            <h4>Выбор перелета</h4>
            <input
                :value="origin"
                class="border py-2 px-4 rounded-none w-full"
                type="text"
                placeholder="Откуда"
                @input="setFrom"
            />

            <input
                :value="destination"
                class="border py-2 px-4 rounded-none w-full"
                type="text"
                placeholder="Куда"
                @input="setTo"
            />

            <vue-date-picker
                v-model="departureDate"
                :enable-time-picker="false"
                input-class-name="border py-2 pl-8 rounded-none"
                placeholder="Туда"
            ></vue-date-picker>

            <vue-date-picker
                v-model="returnDate"
                :enable-time-picker="false"
                :min-date="departureDate"
                input-class-name="border py-2 pl-8 rounded-none"
                placeholder="Обратно"
            ></vue-date-picker>
            <button class="py-2 px-4 bg-slate-300 active:scale-95" type="submit">Найти</button>
        </form>
    </div>
</template>

<script>
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

export default {
    components: { VueDatePicker },
    data() {
        return {
            origin: '',
            destination: '',
            departureDate: null,
            returnDate: null,
        };
    },
    methods: {
        handleFindFlight() {
            console.log(this.origin, this.destination, this.departureDate, this.returnDate);
            this.origin = '';
            this.destination = '';
            this.departureDate = null;
            this.returnDate = null;
        },
        setFrom(event) {
            this.from = event.target.value;
        },
        setTo(event) {
            this.to = event.target.value;
        },
    },
};
</script>

<style></style>
