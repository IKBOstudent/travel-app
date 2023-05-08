<template>
    <form @submit.prevent="handleFindFlight" class="flex flex-row gap-2" :class="{ 'flex-col': this.column }">
        <input
            :value="origin"
            class="flex-grow h-12 input"
            type="text"
            placeholder="Откуда"
            @input="setOrigin"
            :class="{ 'w-full': this.column }"
        />

        <input
            :value="destination"
            class="flex-grow h-12 input"
            type="text"
            placeholder="Куда"
            @input="setDestination"
            :class="{ 'w-full': this.column }"
        />

        <vue-date-picker
            v-model="departureDate"
            :min-date="new Date()"
            :enable-time-picker="false"
            placeholder="Туда"
        ></vue-date-picker>

        <vue-date-picker
            v-model="returnDate"
            :enable-time-picker="false"
            :min-date="departureDate"
            placeholder="Обратно"
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
            origin: "",
            destination: "",
            departureDate: null,
            returnDate: null,
        };
    },
    methods: {
        handleFindFlight() {
            if (this.origin === "" || this.destination === "" || this.departureDate === null) {
                alert("Остались пустые поля");
            } else {
                console.log(this.origin, this.destination, this.departureDate, this.returnDate);
                this.$router.push({
                    path: "flights",
                    query: {
                        origin: this.origin,
                        destination: this.destination,
                        departureDate: this.departureDate.toISOString().split("T")[0],
                        returnDate: this.returnDate && this.returnDate.toISOString().split("T")[0],
                    },
                });
                this.$emit("search");
            }
        },
        setOrigin(event) {
            this.origin = event.target.value;
        },
        setDestination(event) {
            this.destination = event.target.value;
        },
    },
    mounted() {
        if (this.$route.query.origin) {
            this.origin = this.$route.query.origin;
            this.destination = this.$route.query.destination;
            this.departureDate = new Date(this.$route.query.departureDate);
            this.returnDate = new Date(this.$route.query.returnDate);
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
