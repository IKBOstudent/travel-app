<template>
    <div class="flex flex-row">
        <nav class="w-max h-full bg-slate-300">
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
        </nav>
        <div class="ml-4">
            <manage-table :tableName="selectedTable" />
        </div>
    </div>
</template>

<script>
import ManageTable from '@/components/Admin/ManageTable.vue';

const tableNames = ['flights', 'hotels', 'rooms', 'reservations'];

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
    },
};
</script>

<style></style>
