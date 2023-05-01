<template>
    <div
        :class="[
            { active: active === flight.id },
            'p-4 cursor-pointer flex flex-row border gap-4 justify-between',
        ]"
        @click="$emit('update:active', this.flight.id)"
    >
        <div class="flex flex-col">
            <div>Дата: {{ flight.date }} Время: {{ flight.time }}</div>

            <div>{{ flight.origin }} - {{ flight.destination }}</div>
        </div>
        <div class="flex flex-col">
            <div>От {{ flight.price }} ₽</div>
            <button class="py-1 px-4 bg-slate-300 active:scale-95">Выбрать</button>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        flight: {
            require: true,
        },
        active: {
            require: true,
        },
    },

    methods: {},
};
</script>

<style>
.flight {
    cursor: pointer;
}

.active {
    background-color: darkgrey;
}
</style>
