<template>
    <div class="w-full flex justify-center">
        <form @submit.prevent="handleSubmit" class="container max-w-xs flex flex-col gap-2">
            <input
                :value="username"
                class="border py-2 px-4 rounded-none w-full"
                :class="{ 'border-red-600': invalid }"
                type="text"
                placeholder="username"
                @input="setUsername"
            />

            <input
                :value="password"
                class="border py-2 px-4 rounded-none w-full"
                :class="{ 'border-red-600': invalid }"
                type="password"
                placeholder="password"
                @input="setPassword"
            />
            <div v-if="invalid" class="py-1 rounded-none w-full text-red-600">
                Invalid credentials
            </div>
            <button class="py-2 px-4 bg-gray-300 active:scale-95" type="submit">Login</button>
        </form>
    </div>
</template>
<script>
import axios from 'axios';

export default {
    data() {
        return {
            username: '',
            password: '',
            invalid: false,
        };
    },
    methods: {
        async handleSubmit() {
            try {
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            const axiosInterceptor = axios.interceptors.request.use((config) => {
                                if (config.headers && !config.headers['Authorization']) {
                                    config.headers['Authorization'] =
                                        'Basic ' + window.btoa(this.username + ':' + this.password);
                                }
                                config.headers['Content-Type'];
                                config.headers['Accept'] = 'application/json';
                                return config;
                            });

                            await axios.post('http://localhost:8080/api/admin/login', {});
                            this.$emit('auth');
                            this.$store.commit('setInterceptor', axiosInterceptor);
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                this.invalid = true;
            }
        },

        setUsername(event) {
            this.username = event.target.value;
        },

        setPassword(event) {
            this.password = event.target.value;
        },
    },
};
</script>
<style></style>
