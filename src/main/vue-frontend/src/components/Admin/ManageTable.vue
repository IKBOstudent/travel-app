<template>
    <div class="flex flex-col">
        <div class="text-neutral-900 font-semibold text-2xl">Таблица: {{ this.tableName }}_table</div>

        <div>
            <form class="my-8 flex flex-col border rounded border-neutral-200 bg-white" @submit.prevent="handleAddRow">
                <div class="p-6 rounded-t" :class="{ 'bg-neutral-50 text-neutral-400': this.tableStatus !== 1 }">
                    <span class="text-md">Добавление новой сущности</span>
                    <div v-if="this.tableStatus === 0" class="mt-2">Загрузка данных...</div>
                    <div
                        v-else-if="this.tableStatus === 1"
                        v-for="name in tableHead"
                        :key="name"
                        class="mt-2 flex flex-col gap-2"
                    >
                        <input
                            v-if="
                                name !== 'id' &&
                                !name.toLocaleLowerCase().includes('list') &&
                                !name.toLocaleLowerCase().includes('nights')
                            "
                            type="text"
                            autocapitalize="none"
                            autocomplete="off"
                            autocorrect="off"
                            class="text-sm input"
                            :placeholder="name"
                            v-model="newRowData[name]"
                        />
                    </div>
                </div>
                <div
                    class="px-6 py-3 flex flex-row items-center justify-between w-full border-t border-neutral-200 bg-neutral-50 rounded-b"
                >
                    <p v-if="this.tableStatus === 2" class="text-red-600 flex flex-row items-center">
                        <error-svg class="mr-2 h-5" />
                        Произошла ошибка :(
                    </p>
                    <p
                        v-else
                        :class="{
                            'text-neutral-400': this.tableStatus !== 1,
                            'text-neutral-700': this.tableStatus === 1,
                        }"
                        class=""
                    >
                        Добавить элемент в таблицу
                    </p>
                    <button type="submit" class="btn-black" :disabled="this.tableStatus !== 1">Добавить</button>
                </div>
            </form>
            <div v-if="this.tableStatus === 0" class="px-4">Загрузка таблицы...</div>
            <table v-else-if="this.tableStatus === 1" class="pb-8 w-full border-spacing-0 border-separate">
                <thead>
                    <tr class="h-10 bg-neutral-50">
                        <th
                            v-for="(name, i) in [...tableHead, '']"
                            :key="name"
                            class="px-4 text-xs font-normal text-neutral-600 uppercase border-y border-neutral-200 text-left border-l"
                            :class="{
                                'rounded-tl border-l w-10': i === 0,
                                'rounded-tr border-r w-10 ': i === tableHead.length,
                            }"
                        >
                            {{ name }}
                        </th>
                    </tr>
                </thead>

                <tbody v-if="table.length > 0" class="">
                    <tr v-for="row in table" :key="row.id" class="h-12 whitespace-nowrap">
                        <td
                            class="border-l border-b px-4 py-2 text-sm border-neutral-200 text-neutral-700"
                            v-for="(cell, name) in row"
                            :key="name"
                        >
                            <span>{{ String(cell).length > 0 ? (name === "hotel" ? cell.id : cell) : "–" }}</span>
                        </td>
                        <td
                            class="border-x border-b py-2 px-6 text-sm border-neutral-200 text-red-600 hover:bg-red-50 cursor-pointer"
                            @click="() => deleteRow(row.id)"
                        >
                            Удалить
                        </td>
                    </tr>
                </tbody>
            </table>
            <div
                v-if="this.tableStatus === 1 && table.length === 0"
                class="pb-8 w-full py-12 flex justify-center items-center text-sm text-neutral-600"
            >
                Таблица пустая. <strong class="ml-2 font-semibold">Добавьте первую запись.</strong>
            </div>
            <div v-else-if="this.tableStatus === 2" class="pb-8 flex justify-center w-full">
                <button
                    class="px-4 py-2 font-semibold border border-neutral-200 text-neutral-700 rounded hover:bg-neutral-50"
                    @click="() => fetchTable(this.tableName)"
                >
                    Обновить таблицу
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import ErrorSvg from "../svgs/ErrorSvg.vue";

const tableNames = ["flights", "hotels", "rooms", "reservations"];
const statuses = { loading: 0, ok: 1, error: 2 };

export default {
    components: {
        ErrorSvg,
    },
    props: {
        tableName: {
            require: true,
        },
    },
    data() {
        return {
            tableHead: null,
            table: [],
            tableStatus: statuses.loading,
            newRowData: {},
            controller: null,
        };
    },
    methods: {
        async fetchTable(name) {
            this.tableStatus = statuses.loading;
            if (this.controller) {
                this.controller.abort();
            }
            try {
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            this.controller = new AbortController();
                            let response = await axios.get(`http://localhost:8080/api/${name}/metadata`, {
                                signal: this.controller.signal,
                            });
                            this.tableHead = response.data;

                            response = await axios.get(`http://localhost:8080/api/${name}/all`, {
                                signal: this.controller.signal,
                            });
                            this.controller = null;
                            this.table = response.data;

                            this.tableStatus = statuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 0);
                });
            } catch (e) {
                if (e.name !== "CanceledError") {
                    console.error("ERROR", e);
                    this.tableStatus = statuses.error;
                } else {
                    this.tableStatus = statuses.ok;
                }
            }
        },

        async handleAddRow() {
            try {
                this.tableStatus = statuses.loading;
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            const rowData = { ...this.newRowData };
                            let url = `http://localhost:8080/api/${this.tableName}`;
                            if (this.tableName === tableNames[2]) {
                                url += `?hotel_id=${rowData["hotel"]}`;
                                delete rowData["hotel"];
                            } else if (this.tableName === tableNames[3]) {
                                url += `?room_id=${rowData["room"]}`;
                                delete rowData["room"];
                            }
                            const { data } = await axios.post(url, rowData);

                            let response = await axios.get(`http://localhost:8080/api/${this.tableName}/metadata`);
                            this.tableHead = response.data;

                            this.table.push(data);
                            this.tableStatus = statuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 0);
                });
            } catch (e) {
                this.tableStatus = statuses.error;
            }
        },

        async deleteRow(id) {
            try {
                this.tableStatus = statuses.loading;
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            await axios.delete(`http://localhost:8080/api/${this.tableName}/${id}`);

                            this.table = this.table.filter(row => row.id !== id);

                            this.tableStatus = statuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 0);
                });
            } catch (e) {
                this.tableStatus = statuses.error;
            }
        },
    },

    watch: {
        $props: {
            handler() {
                this.fetchTable(this.tableName);
            },
            deep: true,
            immediate: true,
        },
    },
};
</script>

<style></style>
