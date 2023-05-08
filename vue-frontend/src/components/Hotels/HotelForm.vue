<template>
    <form @submit.prevent="handleFindHotels" class="flex flex-row gap-2" :class="{ 'flex-col': this.column }">
        <input
            :value="city"
            type="text"
            placeholder="Город"
            @input="setCity"
            class="flex-grow h-12 input"
            :class="{ 'w-full': this.column }"
        />

        <input
            :value="guests"
            class="flex-grow h-12 input"
            type="number"
            min="1"
            placeholder="Число гостей"
            :class="{ 'w-full': this.column }"
            @input="setGuests"
        />

        <vue-date-picker
            v-model="checkInDate"
            :min-date="new Date()"
            :enable-time-picker="false"
            placeholder="Дата заезда"
        ></vue-date-picker>

        <vue-date-picker
            v-model="checkOutDate"
            :enable-time-picker="false"
            :min-date="checkInDate && new Date(checkInDate.getTime() + 86400000)"
            placeholder="Дата выезда"
        ></vue-date-picker>
        <button class="h-[46px] border-2 btn-black w-fit" type="submit" :class="{ 'w-full': column }">
            <div v-if="column">Найти</div>
            <search-svg v-else class="w-6" />
        </button>
    </form>
</template>

<script>
import VueDatePicker from "@vuepic/vue-datepicker";
import SearchSvg from "@/components/svgs/SearchSvg.vue";
import "@vuepic/vue-datepicker/dist/main.css";

export default {
    props: {
        column: {
            require: true,
        },
    },
    components: { VueDatePicker, SearchSvg },
    data() {
        return {
            city: "",
            guests: null,
            checkInDate: null,
            checkOutDate: null,
        };
    },
    methods: {
        handleFindHotels() {
            if (this.city === "" || this.guests === null || this.checkInDate === null || this.checkOutDate === null) {
                alert("Остались пустые поля");
            } else {
                this.$router.push({
                    path: "hotels",
                    query: {
                        city: this.city,
                        guests: this.guests,
                        checkInDate: this.checkInDate.toISOString().split("T")[0],
                        checkOutDate: this.checkOutDate.toISOString().split("T")[0],
                    },
                });
                this.$emit("search");
            }
        },
        setCity(event) {
            this.city = event.target.value;
        },
        setGuests(event) {
            this.guests = event.target.value;
        },
    },

    mounted() {
        if (this.$route.query.city) {
            this.city = this.$route.query.city;
            this.guests = this.$route.query.guests;
            this.checkInDate = new Date(this.$route.query.checkInDate);
            this.checkOutDate = new Date(this.$route.query.checkOutDate);
        }
    },
};
</script>

<style>
.dp__main {
    @apply min-w-0;
}

.dp__input {
    @apply input pl-10 h-12;
    width: inherit;
}

.dp__action_cancel {
    @apply btn-gray;
}

.dp__action_select {
    @apply btn-black ml-2;
}

.dp__active_date {
    @apply bg-blue-600;
}

.dp__today {
    @apply border-2 border-neutral-200;
}

.dp__overlay_cell_active {
    @apply bg-blue-600;
}
</style>
