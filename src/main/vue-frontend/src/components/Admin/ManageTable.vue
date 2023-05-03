<template>
    <div>
        <div class="p-4">Таблица: {{ this.tableName }}_table</div>
        <div v-if="isTableLoading">Загрузка таблицы...</div>
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
            isTableLoading: true,
            newRowData: {},
        };
    },
    methods: {
        async fetchTableHead(name) {
            const { data } = await axios.get(`http://localhost:8080/api/${name}/metadata`);
            this.tableHead = data;
        },

        async fetchTable(name) {
            try {
                this.isTableLoading = true;
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            const { data } = await axios.get(
                                `http://localhost:8080/api/${name}/all`,
                            );
                            console.log(data);
                            this.table = data;
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                alert('Error', e);
            } finally {
                this.isTableLoading = false;
            }
        },

        async handleAddRow() {
            try {
                this.isTableLoading = true;
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
                            console.log(url, rowData);
                            const { data } = await axios.post(url, rowData);
                            console.log(data);
                            this.table.push(data);
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                alert('Error', e);
            } finally {
                this.isTableLoading = false;
                console.log('update');
            }
        },

        async deleteRow(id) {
            try {
                this.isTableLoading = true;
                await new Promise((res, rej) => {
                    setTimeout(async () => {
                        try {
                            const { data } = await axios.delete(
                                `http://localhost:8080/api/${this.tableName}/${id}`,
                            );
                            console.log(data);
                            this.table = this.table.filter((row) => row.id !== id);
                            res();
                        } catch (e) {
                            rej(e);
                        }
                    }, 1000);
                });
            } catch (e) {
                alert('Error', e);
            } finally {
                this.isTableLoading = false;
                console.log('update');
            }
        },
    },

    watch: {
        $props: {
            handler() {
                this.fetchTableHead(this.tableName);
                this.fetchTable(this.tableName);
            },
            deep: true,
            immediate: true,
        },
    },
};
</script>

<style></style>
