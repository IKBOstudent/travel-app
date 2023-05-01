<template>
    <div v-if="!loading">
        <flight-card
            v-for="flight in flights"
            :key="flight.id"
            :flight="flight"
            v-model:active="activeFlight"
        />
    </div>
    <div v-else>Loading...</div>

    <flight-form />
</template>

<script>
import FlightForm from './components/FlightForm.vue';
import FlightCard from './components/FlightCard.vue';
import axios from 'axios';

// axios.defaults.baseURL = '/';

export default {
    components: {
        FlightForm,
        FlightCard,
    },

    data() {
        return {
            flights: [],
            loading: true,
            activeFlight: 1,
        };
    },
    methods: {
        async fetchFlights() {
            try {
                await new Promise((res) => {
                    setTimeout(async () => {
                        const { data } = await axios.get('http://localhost:8080/api/flights', {
                            proxy: {
                                protocol: 'http',
                                host: 'localhost',
                                port: 8080,
                            },
                        });
                        console.log(data);
                        this.flights = data;
                        res();
                    }, 1000);
                });
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

<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
</style>
