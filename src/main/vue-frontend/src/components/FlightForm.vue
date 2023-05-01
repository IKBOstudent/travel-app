<template>
    <div>
        <form @submit.prevent="handleFindFlight">
            <h4>Выбор перелета</h4>
            <input class="" type="text" placeholder="from" :value="from" @input="setFrom" />
            <input class="" type="text" placeholder="to" :value="to" @input="setTo" />
            <button type="submit">Найти</button>
        </form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            to: '',
            from: '',
        };
    },
    methods: {
        handleFindFlight() {
            console.log(this.from, this.to);
            this.from = '';
            this.to = '';
        },
        setFrom(event) {
            this.from = event.target.value;
        },
        setTo(event) {
            this.to = event.target.value;
        },
    },
};
</script>

<style></style>
