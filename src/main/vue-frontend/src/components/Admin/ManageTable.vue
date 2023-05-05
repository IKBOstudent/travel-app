<template>
    <div class="p-4">
        <div class="py-4">Таблица: {{ this.tableName }}_table</div>
        <div v-if="this.tableStatus === 0">Загрузка таблицы...</div>
        <div v-else-if="this.tableStatus === 2">
            Ошибка загрузки данных :(
            <button class="p-2 w-full bg-slate-300" @click="() => fetchTable(this.tableName)">
                Обновить страницу
            </button>
        </div>

        <div v-else>
            <form class="p-6 flex flex-col w-60 gap-1" @submit.prevent="handleAddRow">
                <div>Добавление новой сущности</div>
                <div v-for="name in tableHead" :key="name">
                    <input
                        v-if="name !== 'id' && !name.toLocaleLowerCase().includes('list')"
                        type="text"
                        class="border py-1 px-4 rounded-none w-full"
                        :placeholder="name"
                        v-model="newRowData[name]"
                    />
                </div>

                <button class="p-2 w-full bg-slate-300">Добавить</button>
            </form>

            <div v-if="table.length === 0">Таблица пустая</div>
            <div v-else class="">
                <table class="">
                    <thead>
                        <tr class="border-y">
                            <th
                                v-for="(_, name) in table[0]"
                                :key="name"
                                class="px-4 py-2 align-middle text-xs uppercase"
                            >
                                {{ name }}
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="row in table" :key="row.id">
                            <td
                                class="px-4 py-4 align-middle text-xs whitespace-nowrap"
                                v-for="(cell, name) in row"
                                :key="name"
                            >
                                {{ cell }}
                            </td>
                            <td>
                                <button
                                    class="p-2 w-full bg-slate-300"
                                    @click="() => deleteRow(row.id)"
                                >
                                    Удалить
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

const tableNames = ['flights', 'hotels', 'rooms', 'reservations'];
const tableStatuses = { loading: 0, ok: 1, error: 2 };
console.log(tableStatuses);

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
                            let response = await axios.get(
                                `http://localhost:8080/api/${name}/metadata`,
                                {
                                    signal: this.controller.signal,
                                },
                            );
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
                    }, 1000);
                });
            } catch (e) {
                if (e.name !== 'CanceledError') {
                    console.error('ERROR', e);
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
                                url += `?hotel_id=${rowData['hotel']}`;
                                delete rowData['hotel'];
                            } else if (this.tableName === tableNames[3]) {
                                url += `?room_id=${rowData['room']}`;
                                delete rowData['room'];
                            }
                            const { data } = await axios.post(url, rowData);
                            this.table.push(data);
                            this.tableStatus = tableStatuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
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

                            this.table = this.table.filter((row) => row.id !== id);

                            this.tableStatus = tableStatuses.ok;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
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
