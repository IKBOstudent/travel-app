<template>
    <div>
        <div v-if="!this.isAuthenticated">
            <login-form @auth="setAuthenticated" />
        </div>
        <div v-else>
            <admin-home />
        </div>
    </div>
</template>

<script>
import AdminHome from '@/components/Admin/AdminHome.vue';
import LoginForm from '@/components/Admin/LoginForm.vue';

export default {
    components: {
        LoginForm,
        AdminHome,
    },
    data() {
        return {
            isAuthenticated: false,
        };
    },
    methods: {
        setAuthenticated() {
            this.isAuthenticated = true;
        },
    },
};
</script>
<style></style>
