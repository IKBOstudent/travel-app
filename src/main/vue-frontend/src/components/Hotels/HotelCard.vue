<template>
    <div class="p-4 flex flex-col border gap-4">
        <div class="flex flex-row justify-between">
            <div class="flex flex-col">
                <div>Отель "{{ hotel.name }}", {{ hotel.stars }} звезды</div>
                <div>Номер на {{ hotel.roomBeds }} человек</div>
            </div>
            <div class="flex flex-col">
                <div>От {{ hotel.price }} ₽</div>
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
                    Дата заезда: <strong>{{ hotel.checkInDate }}</strong>
                </div>
                <div>
                    Дата выезда: <strong>{{ hotel.checkOutDate }}</strong>
                </div>
            </div>

            <div class="self-end">
                <button class="py-2 px-4 bg-slate-300 active:scale-95">
                    Подтвердить бронирование
                </button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        hotel: {
            require: true,
        },
        selected: {
            required: true,
        },
    },

    methods: {
        handleSelect() {
            console.log(this.hotel.id);
            this.$emit('select', this.hotel.id);
        },
    },
};
</script>
