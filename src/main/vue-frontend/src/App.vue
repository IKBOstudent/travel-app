<template>
    <div class="contianer p-4">
        <flights-page />
    </div>
</template>

<script>
import FlightsPage from './components/Flights/FlightsPage.vue';

export default {
    components: {
        FlightsPage,
    },
    data() {},
};
</script>

<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
</style>
