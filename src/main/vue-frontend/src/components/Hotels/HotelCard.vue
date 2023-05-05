<template>
    <div class="p-4 flex flex-col border gap-4">
        <div class="flex flex-row justify-between">
            <div class="flex flex-col">
                <div>Отель "{{ room.hotel.name }}", {{ room.hotel.stars }} звезды</div>
                <div>Номер на {{ room.beds }} человек</div>
            </div>
            <div class="flex flex-col">
                <div>От {{ room.nightPrice }} ₽</div>
                <button
                    v-if="!selected"
                    class="py-1 px-4 bg-slate-300 active:scale-95"
                    @click="handleSelect"
                >
                    Выбрать
                </button>
            </div>
        </div>
        <div v-if="selected" class="flex flex-row justify-between">
            <div>
                <div>Подтверждение бронирования:</div>
                <div>
                    Дата заезда: <strong>{{ $route.query.checkInDate }}</strong>
                </div>
                <div>
                    Дата выезда: <strong>{{ $route.query.checkOutDate }}</strong>
                </div>
            </div>

            <div class="self-end">
                <button
                    class="py-2 px-4 bg-slate-300 active:scale-95"
                    @click="handleSubmitReservation"
                >
                    Подтвердить бронирование
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    props: {
        room: {
            require: true,
        },
        selected: {
            required: true,
        },
    },

    methods: {
        handleSelect() {
            console.log(this.room.id);
            this.$emit('select', this.room.id);
        },
        async handleSubmitReservation() {
            try {
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            let url = `http://localhost:8080/api/reservations?`;
                            url += `room_id=${this.room.id}`;
                            const body = {
                                checkInDate: this.$route.query.checkInDate,
                                checkOutDate: this.$route.query.checkOutDate,
                            };
                            const { data } = await axios.post(url, body);
                            console.log(data);
                            alert('Success');
                            res();
                        } catch (e) {
                            console.log(e);
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                alert('Error', e);
            }
        },
    },
};
</script>
