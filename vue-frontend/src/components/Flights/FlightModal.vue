<template>
    <div>
        <div class="fixed t-0 l-0 b-0 w-full h-full bg-black/40 z-40 pointer-events-none"></div>

        <div class="fixed t-0 l-0 flex flex-col items-center justify-center h-screen w-screen z-50 overflow-auto">
            <div class="drop-shadow-2xl w-[30rem] flex flex-col border rounded border-neutral-200 bg-white">
                <div class="px-8 py-4 flex flex-col">
                    <h1 class="mt-2 text-2xl font-semibold">Подтверждение бронирования</h1>
                    <span class="mt-4 text-lg font-semibold"
                        >Рейс {{ $store.state.flight.info.origin }} - {{ $store.state.flight.info.destination }}</span
                    >
                    <span>Дата: {{ $store.state.flight.info.date }}</span>
                    <span>Вылет: {{ $store.state.flight.info.departureTime }}</span>
                    <span>Прилет: {{ $store.state.flight.info.arrivalTime }}</span>

                    <span class="mx-2 mt-2 text-xl font-semibold text-right"
                        >Сумма: {{ $store.state.flight.info.price }}</span
                    >
                    <span v-if="status === 2" class="mx-2 mt-2 text-red-600 text-md text-right"
                        >Ошибка бронирования :(</span
                    >
                    <span v-else-if="status === 1" class="mx-2 mt-2 text-green-700 text-md text-right"
                        >Оплачено. Номер брони: {{ reservationId }}</span
                    >
                </div>

                <div
                    class="px-6 py-3 flex flex-row items-center justify-between w-full border-t border-neutral-200 bg-neutral-50 rounded-b"
                >
                    <button class="bg-white btn-gray" @click="handleDiscard" :disabled="status === 0">
                        {{ status > 0 ? "Закрыть" : "Отмена" }}
                    </button>

                    <button
                        v-if="status !== 1"
                        class="btn-black"
                        @click="handleSubmitReservation"
                        :class="{
                            'border-red-600 text-red-600 bg-white hover:text-red-800': status === 2,
                        }"
                        :disabled="status === 0"
                    >
                        <span v-if="status === -1">Подтвердить бронирование</span>
                        <span v-if="status === 0">Обработка запроса...</span>

                        <span v-if="status === 2">Повторить</span>
                    </button>
                    <button
                        v-if="status === 1"
                        class="btn-black"
                        @click="handleDiscard"
                        :class="{
                            'border-green-700 text-green-700 bg-white hover:text-green-800': status === 1,
                        }"
                    >
                        <span>Готово</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";

const statuses = { notSent: -1, loading: 0, ok: 1, error: 2 };

export default {
    emits: ["discard"],
    data() {
        return {
            status: statuses.notSent,
            reservationId: null,
        };
    },
    methods: {
        handleDiscard() {
            this.$store.commit("flight/setShowModal", null);
        },

        async handleSubmitReservation() {
            try {
                this.status = statuses.loading;
                const body = {
                    checkInDate: this.$route.query.checkInDate,
                    checkOutDate: this.$route.query.checkOutDate,
                };

                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            let url = `http://localhost:8080/api/flights/book?`;
                            url += `flight_id=${this.$store.state.flight.info.id}`;

                            const { data } = await axios.post(url, body);
                            this.reservationId = data.id;
                            this.status = statuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                this.status = statuses.error;
            }
        },
    },
};
</script>
