<template>
    <div>
        <flight-form />
        <div v-if="!loading && flights.length > 0" class="py-2 flex flex-col gap-2">
            <flight-card v-for="flight in flights" :key="flight.id" :flight="flight" />
        </div>
        <div v-else-if="!loading && flights.length === 0">Рейсов не найдено</div>
        <div v-else>Loading...</div>
    </div>
</template>

<script>
// import axios from 'axios';

import FlightCard from './FlightCard.vue';
import FlightForm from './FlightForm.vue';

export default {
    components: {
        FlightForm,
        FlightCard,
    },
    data() {
        return {
            flights: [
                {
                    id: 352,
                    origin: 'Moscow',
                    destination: 'Paris',
                    date: '2023-01-14',
                    time: '09:35:00',
                    price: 6000,
                },
                {
                    id: 353,
                    origin: 'Moscow',
                    destination: 'Paris',
                    date: '2023-01-14',
                    time: '18:40:00',
                    price: 3060,
                },
            ],
            loading: true,
        };
    },
    methods: {
        async fetchFlights() {
            try {
                // await new Promise((res) => {
                //     setTimeout(async () => {
                //         const url = `http://localhost:8080/api/flights?origin=${
                //             this.origin
                //         }&destination=${this.destination}&date=${new Date(this.date).format(
                //             'YYYY-MM-DD',
                //         )}`;
                //         console.log(url);
                //         const response = await axios.get('http://localhost:8080/api/flights');
                //         console.log(response);
                //         const { data } = response;
                //         console.log(data);
                //         this.flights = data;
                //         res();
                //     }, 1000);
                // });
                console.log(this.flights);
            } catch (e) {
                alert('Error', e);
            } finally {
                this.loading = false;
            }
        },
    },
    mounted() {
        this.fetchFlights();
    },
};
</script>
