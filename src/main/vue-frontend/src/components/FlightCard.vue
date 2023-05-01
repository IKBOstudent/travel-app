<template>
    <div
        :class="[{ active: active === flight.id }, 'flight']"
        @click="$emit('update:active', this.flight.id)"
    >
        <div>{{ flight.id }}</div>
        <div>Origin: {{ flight.origin }}</div>
        <div>Destination: {{ flight.destination }}</div>
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
