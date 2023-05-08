<template>
    <flight-modal v-if="$store.state.flight.info !== null" />

    <div>
        <div class="max-w-7xl min-h-screen px-6 mx-auto">
            <aside class="fixed top-0 bottom-0 w-[340px]">
                <router-link to="/" exact class="mt-12 mx-8 btn-gray">Вернуться в меню </router-link>
                <div class="m-8 mt-4 border border-neutral-200 bg-neutral-50 rounded">
                    <h3 class="px-8 pt-4 text-lg font-semibold">Поиск</h3>
                    <flight-form class="pt-4 p-4" :column="true" @search="makeFetches" />
                </div>
            </aside>
            <div class="ml-[340px] py-12 min-h-screen flex flex-col">
                <div class="py-10">
                    <h3 class="text-xl font-semibold">
                        Рейсы {{ $route.query.origin }} - {{ $route.query.destination }}
                    </h3>
                    <div class="mt-2 flex flex-col">
                        <p v-if="forwardStatus === 0" class="text-lg text-semibold text-neutral-500">
                            Ищем подходящие варианты...
                        </p>

                        <p v-if="forwardStatus === 1 && flights.length === 0" class="text-xl text-semibold">
                            Рейсы не найдены :(
                        </p>
                        <p v-if="forwardStatus === 2" class="text-red-600 flex flex-row items-center">
                            <error-svg class="mr-2 h-5" />
                            Произошла ошибка. Попробуйте еще раз...
                        </p>

                        <div v-if="forwardStatus === 1 && flights.length > 0" class="flex flex-col">
                            <div class="mt-4 flex flex-col gap-4">
                                <flight-card v-for="flight in flights" :key="flight.id" :flight="flight" />
                            </div>
                        </div>
                    </div>
                </div>
                <div v-if="$route.query.returnDate" class="py-4">
                    <h3 class="text-xl font-semibold">
                        Рейсы {{ $route.query.destination }} - {{ $route.query.origin }}
                    </h3>
                    <div class="mt-2 flex flex-col">
                        <p v-if="backwardStatus === 0" class="text-lg text-semibold text-neutral-500">
                            Ищем подходящие варианты...
                        </p>

                        <p v-if="backwardStatus === 1 && returnFlights.length === 0" class="text-xl text-semibold">
                            Рейсы не найдены :(
                        </p>
                        <p v-if="backwardStatus === 2" class="text-red-600 flex flex-row items-center">
                            <error-svg class="mr-2 h-5" />
                            Произошла ошибка. Попробуйте еще раз...
                        </p>

                        <div v-if="backwardStatus === 1 && returnFlights.length > 0" class="flex flex-col">
                            <div class="mt-4 flex flex-col gap-4">
                                <flight-card v-for="flight in returnFlights" :key="flight.id" :flight="flight" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import FlightForm from "@/components/Flights/FlightForm.vue";
import FlightModal from "@/components/Flights/FlightModal.vue";
import ErrorSvg from "@/components/svgs/ErrorSvg.vue";
import FlightCard from "@/components/Flights/FlightCard.vue";

const statuses = { loading: 0, ok: 1, error: 2 };

export default {
    components: {
        FlightCard,
        FlightForm,
        ErrorSvg,
        FlightModal,
    },
    data() {
        return {
            flights: [],
            returnFlights: [],
            forwardStatus: statuses.loading,
            backwardStatus: statuses.loading,
        };
    },
    methods: {
        makeFetches() {
            const query = this.$route.query;
            if (!query || !query.origin || !query.destination || !query.departureDate) {
                this.$router.push("/");
            }

            this.fetchFlights(true);
            if (query.returnDate) {
                this.fetchFlights(false);
            }
        },
        async fetchFlights(forward) {
            try {
                if (forward) {
                    this.forwardStatus = statuses.loading;
                } else {
                    this.backwardStatus = statuses.loading;
                }

                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            const query = this.$route.query;

                            let url = `/api/flights?`;
                            if (forward) {
                                url += `origin=${query.origin}`;
                                url += `&destination=${query.destination}`;
                                url += `&date=${query.departureDate}`;
                            } else {
                                url += `origin=${query.destination}`;
                                url += `&destination=${query.origin}`;
                                url += `&date=${query.returnDate}`;
                            }

                            const { data } = await axios.get(url);
                            console.log(data);
                            data.forEach(flight => {
                                flight.arrivalTime = flight.arrivalTime.slice(0, 5);
                                flight.departureTime = flight.departureTime.slice(0, 5);
                                flight.price = new Intl.NumberFormat("ru-RU", {
                                    style: "currency",
                                    currency: "RUB",
                                }).format(flight.price);
                                flight.date = new Intl.DateTimeFormat("ru-RU", {
                                    weekday: "long",
                                    year: "numeric",
                                    month: "long",
                                    day: "numeric",
                                }).format(new Date(flight.date));
                            });

                            if (forward) {
                                this.flights = data;
                                this.forwardStatus = statuses.ok;
                            } else {
                                this.returnFlights = data;
                                this.backwardStatus = statuses.ok;
                            }

                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                console.log(e);
                if (forward) {
                    this.forwardStatus = statuses.error;
                } else {
                    this.backwardStatus = statuses.error;
                }
            }
        },
    },
    mounted() {
        this.makeFetches();
    },
};
</script>
