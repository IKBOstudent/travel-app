<template>
    <div class="flex flex-col">
        <div class="text-neutral-900 font-semibold text-2xl">Таблица: {{ this.tableName }}_table</div>

        <div>
            <form class="my-8 flex flex-col border rounded border-neutral-200 bg-white" @submit.prevent="handleAddRow">
                <div class="p-6 rounded-t" :class="{ 'bg-neutral-100 text-neutral-400': this.tableStatus !== 1 }">
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
                            class="border py-2 px-3 w-fit rounded leading-4 text-sm focus-visible:border-neutral-700 outline-none"
                            :placeholder="name"
                            v-model="newRowData[name]"
                        />
                    </div>
                </div>
                <div
                    class="px-6 py-3 flex flex-row items-center justify-between w-full border-t border-neutral-200 bg-neutral-100 rounded-b"
                >
                    <p v-if="this.tableStatus === 2" class="text-red-600 flex flex-row items-center">
                        <svg
                            data-testid="geist-icon"
                            fill="none"
                            shape-rendering="geometricPrecision"
                            stroke="currentColor"
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="1.5"
                            viewBox="0 0 24 24"
                            width="24"
                            class="mr-2 h-5"
                        >
                            <circle cx="12" cy="12" r="10" fill="var(--geist-fill)" />
                            <path d="M12 8v4" stroke="var(--geist-stroke)" />
                            <path d="M12 16h.01" stroke="var(--geist-stroke)" />
                        </svg>
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
                    <button
                        type="submit"
                        class="px-3 py-1 bg-neutral-900 border border-neutral-900 rounded text-white font-semibold hover:text-neutral-900 hover:bg-white disabled:text-neutral-400 disabled:bg-neutral-100 disabled:border-neutral-300"
                        :disabled="this.tableStatus !== 1"
                    >
                        Добавить
                    </button>
                </div>
            </form>
            <div v-if="this.tableStatus === 0" class="">Загрузка таблицы...</div>
            <table v-else-if="this.tableStatus === 1" class="w-full border-spacing-0 border-separate">
                <thead>
                    <tr class="h-10 bg-neutral-100">
                        <th
                            v-for="(name, i) in [...tableHead, '']"
                            :key="name"
                            class="px-4 text-xs font-normal text-neutral-600 uppercase border-y border-neutral-200 text-left"
                            :class="{
                                'rounded-tl border-l w-10': i === 0,
                                'rounded-tr border-r w-10': i === tableHead.length,
                            }"
                        >
                            {{ name }}
                        </th>
                    </tr>
                </thead>

                <tbody v-if="table.length > 0" class="">
                    <tr v-for="(row, i) in table" :key="row.id" class="h-12 whitespace-nowrap">
                        <td
                            class="px-4 py-2 text-sm border-neutral-200 text-neutral-700"
                            v-for="(cell, name) in row"
                            :key="name"
                            :class="{ 'border-b': i < table.length - 1 }"
                        >
                            <span>{{ String(cell).length > 0 ? (name === "hotel" ? cell.id : cell) : "–" }}</span>
                        </td>
                        <td
                            class="py-2 px-6 text-sm border-neutral-200 text-red-600 hover:bg-red-50 cursor-pointer"
                            :class="{ 'border-b': i < table.length - 1 }"
                            @click="() => deleteRow(row.id)"
                        >
                            Удалить
                        </td>
                    </tr>
                </tbody>
            </table>
            <div
                v-if="this.tableStatus === 1 && table.length === 0"
                class="w-full py-12 flex justify-center items-center text-sm text-neutral-600"
            >
                Таблица пустая. <strong class="ml-2 font-semibold">Добавьте первую запись.</strong>
            </div>
            <div v-else-if="this.tableStatus === 2" class="flex justify-center w-full">
                <button
                    class="px-4 py-2 font-semibold border border-neutral-200 text-neutral-700 rounded hover:bg-neutral-100"
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

const tableNames = ["flights", "hotels", "rooms", "reservations"];
const tableStatuses = { loading: 0, ok: 1, error: 2 };

export default {
    props: {
        tableName: {
            require: true,
        },
    },
    data() {
        return {
            tableHead: null,
            table: [],
            tableStatus: tableStatuses.loading,
            newRowData: {},
            controller: null,
        };
    },
    methods: {
        async fetchTable(name) {
            this.tableStatus = tableStatuses.loading;
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

                            this.tableStatus = tableStatuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 0);
                });
            } catch (e) {
                if (e.name !== "CanceledError") {
                    console.error("ERROR", e);
                    this.tableStatus = tableStatuses.error;
                } else {
                    this.tableStatus = tableStatuses.ok;
                }
            }
        },

        async handleAddRow() {
            try {
                this.tableStatus = tableStatuses.loading;
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
                            this.tableStatus = tableStatuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 0);
                });
            } catch (e) {
                this.tableStatus = tableStatuses.error;
            }
        },

        async deleteRow(id) {
            try {
                this.tableStatus = tableStatuses.loading;
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            await axios.delete(`http://localhost:8080/api/${this.tableName}/${id}`);

                            this.table = this.table.filter(row => row.id !== id);

                            this.tableStatus = tableStatuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 0);
                });
            } catch (e) {
                this.tableStatus = tableStatuses.error;
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
