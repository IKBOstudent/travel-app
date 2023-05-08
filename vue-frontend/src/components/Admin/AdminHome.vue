<template>
    <div class="max-w-7xl min-h-screen px-6 mx-auto">
        <aside class="fixed top-0 bottom-0 w-[300px]">
            <div class="flex flex-col p-8 h-full">
                <div class="flex justify-between items-center pt-4 px-4">
                    <h1 class="text-xl font-semibold">Admin</h1>
                    <button class="btn-gray" @click="handleLogout">
                        Выйти
                        <logout-svg class="ml-1 h-5" />
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
import LogoutSvg from "../svgs/LogoutSvg.vue";
import axios from "axios";

const tableNames = ["flights", "hotels", "rooms", "reservations"];

export default {
    components: {
        ManageTable,
        LogoutSvg,
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
                    }, 0);
                });
            } catch (e) {
                console.warn("server error");
            }
        },
    },
};
</script>

<style></style>
