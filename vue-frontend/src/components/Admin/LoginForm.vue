<template>
    <div class="w-full flex justify-center mt-8">
        <form @submit.prevent="handleSubmit" class="container max-w-xs flex flex-col items-center gap-3">
            <h1 class="p-2 font-semibold text-2xl text-neutral-900">Admin</h1>
            <input
                :value="username"
                type="text"
                placeholder="username"
                class="w-full text-lg input"
                :class="{ 'input-invalid': invalid }"
                @input="setUsername"
            />

            <input
                :value="password"
                type="password"
                placeholder="password"
                class="w-full text-lg input"
                :class="{ 'input-invalid': invalid }"
                @input="setPassword"
            />
            <div v-if="invalid" class="inline-flex items-center w-full text-red-600">
                <strong class="inline-flex items-center mr-1"><error-svg class="mr-2 h-5 stroke-2" />Error: </strong>
                Something went wrong.
            </div>
            <button class="w-full h-12 font-semibold text-lg btn-black border-2" :disabled="loading" type="submit">
                Login
            </button>
        </form>
    </div>
</template>
<script>
import axios from "axios";

import ErrorSvg from "../svgs/ErrorSvg.vue";

export default {
    components: {
        ErrorSvg,
    },
    data() {
        return {
            username: "",
            password: "",
            invalid: false,
            loading: false,
        };
    },
    methods: {
        async handleSubmit() {
            try {
                this.loading = true;

                const axiosInterceptor = axios.interceptors.request.use(config => {
                    if (config.headers && !config.headers["Authorization"]) {
                        config.headers["Authorization"] = "Basic " + window.btoa(this.username + ":" + this.password);
                    }
                    config.headers["Content-Type"];
                    config.headers["Accept"] = "application/json";
                    return config;
                });

                await axios.post("/api/admin/login", {});
                this.$emit("auth");
                this.$store.commit("setInterceptor", axiosInterceptor);
            } catch (e) {
                this.invalid = true;
            } finally {
                this.loading = false;
            }
        },

        setUsername(event) {
            this.invalid = false;
            this.username = event.target.value;
        },

        setPassword(event) {
            this.invalid = false;
            this.password = event.target.value;
        },
    },
};
</script>
<style></style>
