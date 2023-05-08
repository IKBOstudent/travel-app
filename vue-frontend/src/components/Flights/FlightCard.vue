<template>
    <div class="p-4 flex flex-col border border-neutral-200 bg-neutral-50 rounded">
        <div class="flex flex-row">
            <div class="flex-grow ml-4 flex flex-col">
                <div class="text-lg font-semibold">{{ flight.origin }} - {{ flight.destination }}</div>
                <div class="flex flex-row items-center gap-1 text-xs text-neutral-600 p-px">Без пересадок</div>

                <div class="w-full mt-4 flex flex-row justify-between">
                    <div class="px-2 flex flex-col">
                        <div class="flex flex-row gap-1 text-2xl font-bold">
                            <span>{{ flight.departureTime }}</span>
                            <div class="inline-flex flex-row">
                                <svg
                                    v-for="v in [...Array(4)]"
                                    :key="v"
                                    class="mt-px w-6"
                                    fill="none"
                                    shape-rendering="geometricPrecision"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    viewBox="0 0 24 24"
                                >
                                    <path d="M5 12h14" />
                                </svg>
                            </div>

                            <span> {{ flight.arrivalTime }}</span>
                        </div>

                        <h3 class="mt-1 font-semibold">{{ flight.date }}</h3>
                    </div>
                    <div class="flex flex-col justify-end">
                        <span class="text-xs text-neutral-700">Билет невозвратный</span>
                        <h1 class="text-xl font-semibold">
                            {{ flight.price }}
                        </h1>
                        <button class="mt-1 btn-black" @click="handleSelect">Выбрать</button>
                    </div>
                </div>
            </div>
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

    methods: {
        handleSelect() {
            this.$store.commit("flight/setShowModal", this.flight);
        },
    },
};
</script>
