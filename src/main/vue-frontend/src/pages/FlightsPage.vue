<template>
    <div class="container mx-auto px-4">
        <div>Результаты поиска:</div>
        <div v-if="!loading" class="py-2 flex flex-col gap-4">
            <div>
                Рейсы {{ $route.query.origin }} - {{ $route.query.destination }}
                <br />
                на {{ $route.query.departureDate }}
                <div v-if="flights.length > 0" class="flex flex-col gap-2">
                    <flight-card v-for="flight in flights" :key="flight.id" :flight="flight" />
                </div>
                <div v-else>Рейсов не найдено</div>
            </div>

            <div v-if="$route.query.returnDate">
                Рейсы {{ $route.query.destination }} - {{ $route.query.origin }}
                <br />
                на {{ $route.query.returnDate }}
                <div v-if="returnFlights.length > 0" class="flex flex-col gap-2">
                    <flight-card
                        v-for="flight in returnFlights"
                        :key="flight.id"
                        :flight="flight"
                    />
                </div>
                <div v-else>Рейсов не найдено</div>
            </div>
        </div>
        <div v-else>Загрузка...</div>
    </div>
</template>

<script>
import axios from 'axios';

import FlightCard from '@/components/Flights/FlightCard.vue';

export default {
    components: {
        FlightCard,
    },
    data() {
        return {
            flights: [],
            returnFlights: [],
            loading: true,
        };
    },
    methods: {
        async fetchFlights(oneDir) {
            try {
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            const query = this.$route.query;

                            let url = `http://localhost:8080/api/flights?`;
                            if (oneDir) {
                                url += `origin=${query.origin}`;
                                url += `&destination=${query.destination}`;
                                url += `&date=${query.departureDate}`;
                            } else {
                                url += `origin=${query.destination}`;
                                url += `&destination=${query.origin}`;
                                url += `&date=${query.returnDate}`;
                            }

                            // console.log(url);
                            const { data } = await axios.get(url);
                            console.log(data);
                            this.flights = data;
                            res();
                        } catch (e) {
                            rej(e);
                        }
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
        const query = this.$route.query;
        if (!query || !query.origin || !query.destination || !query.departureDate) {
            this.$router.push('/');
        }
        this.fetchFlights(true);
        if (query.returnDate) {
            this.fetchFlights(false);
        }
    },
};
</script>
