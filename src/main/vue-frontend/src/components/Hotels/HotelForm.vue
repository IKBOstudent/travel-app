<template>
    <div>
        <form @submit.prevent="handleFindHotels" class="container max-w-md flex flex-col gap-2">
            <input
                :value="city"
                class="border py-2 px-4 rounded-none w-full"
                type="text"
                placeholder="Город"
                @input="setCity"
            />

            <input
                :value="guests"
                class="border py-2 px-4 rounded-none w-full"
                type="number"
                placeholder="Число гостей"
                @input="setGuests"
            />

            <vue-date-picker
                v-model="checkInDate"
                :enable-time-picker="false"
                input-class-name="border py-2 pl-8 rounded-none"
                placeholder="Дата заезда"
            ></vue-date-picker>

            <vue-date-picker
                v-model="checkOutDate"
                :enable-time-picker="false"
                :min-date="checkInDate"
                input-class-name="border py-2 pl-8 rounded-none"
                placeholder="Дата выезда"
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
            city: '',
            guests: null,
            checkInDate: null,
            checkOutDate: null,
        };
    },
    methods: {
        handleFindHotels() {
            if (
                this.city === '' ||
                this.guests === null ||
                this.checkInDate === null ||
                this.checkOutDate === null
            ) {
                alert('Остались пустые поля');
            } else {
                console.log(this.city, this.guests, this.checkInDate, this.checkOutDate);
                this.$router.push({
                    path: 'hotels',
                    query: {
                        city: this.city,
                        guests: this.guests,
                        checkInDate: this.checkInDate.toISOString().split('T')[0],
                        checkOutDate: this.checkOutDate.toISOString().split('T')[0],
                    },
                });
            }
        },
        setCity(event) {
            this.city = event.target.value;
        },
        setGuests(event) {
            this.guests = event.target.value;
        },
    },
};
</script>
