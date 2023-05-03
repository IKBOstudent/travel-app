<template>
    <div class="flex flex-row">
        <nav class="">
            <div class="w-max bg-slate-300">
                <ul class="flex flex-col list-none text-md font-semibold uppercase">
                    <li
                        v-for="name in tableNames"
                        :key="name"
                        class="px-6 py-4 cursor-pointer"
                        :class="{ 'bg-slate-400': name === selectedTable }"
                        @click="() => setSelected(name)"
                    >
                        {{ name }}
                    </li>
                </ul>
            </div>
            <div>
                <button
                    class="mt-4 py-2 px-4 bg-slate-800 text-white active:scale-95"
                    @click="handleLogout"
                >
                    Logout
                </button>
            </div>
        </nav>

        <div class="ml-4">
            <manage-table :tableName="selectedTable" />
        </div>
    </div>
</template>

<script>
import ManageTable from '@/components/Admin/ManageTable.vue';
import axios from 'axios';

const tableNames = ['flights', 'hotels', 'rooms', 'reservations'];

export default {
    components: {
        ManageTable,
    },
    data() {
        return {
            tableNames,
            selectedTable: tableNames[0],
            isAuthenticated: false,
        };
    },
    methods: {
        setSelected(name) {
            this.selectedTable = name;
        },
        handleAuth() {
            this.isAuthenticated = true;
        },

        async handleLogout() {
            try {
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            await axios.post('http://localhost:8080/api/admin/logout');
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                alert('Error', e);
            } finally {
                console.log('logout success');
            }
        },
    },
};
</script>

<style></style>
