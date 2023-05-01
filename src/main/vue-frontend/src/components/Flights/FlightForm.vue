<template>
    <div>
        <form @submit.prevent="handleFindFlight" class="container max-w-md flex flex-col gap-2">
            <input
                :value="origin"
                class="border py-2 px-4 rounded-none w-full"
                type="text"
                placeholder="Откуда"
                @input="setOrigin"
            />

            <input
                :value="destination"
                class="border py-2 px-4 rounded-none w-full"
                type="text"
                placeholder="Куда"
                @input="setDestination"
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
            <button class="py-2 px-4 bg-slate-800 text-white active:scale-95" type="submit">
                Найти
            </button>
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
            if (this.origin === '' || this.destination === '' || this.departureDate === null) {
                alert('Остались пустые поля');
            } else {
                console.log(this.origin, this.destination, this.departureDate, this.returnDate);
                this.$router.push({
                    path: 'flights',
                    query: {
                        origin: this.origin,
                        destination: this.destination,
                        departureDate: this.departureDate.toISOString().split('T')[0],
                        returnDate: this.returnDate && this.returnDate.toISOString().split('T')[0],
                    },
                });
            }
        },
        setOrigin(event) {
            this.origin = event.target.value;
        },
        setDestination(event) {
            this.destination = event.target.value;
        },
    },
};
</script>
