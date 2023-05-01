<template>
    <div class="p-4 cursor-pointer flex flex-row border gap-4 justify-between">
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
    },

    methods: {},
};
</script>
