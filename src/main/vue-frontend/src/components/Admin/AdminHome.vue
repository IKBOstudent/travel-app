<template>
    <div class="max-w-7xl min-h-screen px-6 mx-auto">
        <aside class="fixed top-0 bottom-0 w-[300px]">
            <div class="flex flex-col p-8 h-full">
                <div class="flex justify-between items-center pt-4 px-4">
                    <h1 class="text-xl font-semibold">Admin</h1>
                    <button
                        class="inline-flex items-center py-1 px-3 bg-neutral-100 border border-neutral-200 text-neutral-500 rounded hover:text-neutral-900"
                        @click="handleLogout"
                    >
                        Выйти
                        <svg
                            class="ml-1 h-5"
                            data-testid="geist-icon"
                            fill="none"
                            height="24"
                            shape-rendering="geometricPrecision"
                            stroke="currentColor"
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="1.5"
                            viewBox="0 0 24 24"
                            width="24"
                        >
                            <path d="M9 21H5a2 2 0 01-2-2V5a2 2 0 012-2h4" />
                            <path d="M16 17l5-5-5-5" />
                            <path d="M21 12H9" />
                        </svg>
                    </button>
                </div>
                <ul class="pt-8 pr-8 flex flex-col list-none text-md font-semibold">
                    <p class="p-4 text-lg">Таблицы</p>
                    <li
                        v-for="name in tableNames"
                        :key="name"
                        class="px-4 py-2 h-10 cursor-pointer rounded hover:bg-neutral-100 mt-1 font-normal"
                        :class="{ 'bg-neutral-200': name === selectedTable }"
                        @click="() => setSelected(name)"
                    >
                        {{ name }}
                    </li>
                </ul>
            </div>
        </aside>

        <div class="ml-[300px] pt-12 min-h-screen">
            <manage-table :tableName="selectedTable" />
        </div>
    </div>
</template>

<script>
import ManageTable from "@/components/Admin/ManageTable.vue";
import axios from "axios";

const tableNames = ["flights", "hotels", "rooms", "reservations"];

export default {
    components: {
        ManageTable,
    },
    data() {
        return {
            tableNames,
            selectedTable: tableNames[0],
        };
    },
    methods: {
        setSelected(name) {
            this.selectedTable = name;
        },

        async handleLogout() {
            try {
                this.$router.push("/");
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            this.$store.commit("setInterceptor", null);
                            await axios.post("http://localhost:8080/api/admin/logout");
                            console.log("logout success");
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                console.warn("server error");
            }
        },
    },
};
</script>

<style></style>
