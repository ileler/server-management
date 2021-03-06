<template>
    <v-data-table
            loading-text="Loading... Please wait"
            :items-per-page="5"
            :loading="loading"
            :headers="headers"
            :items="list"
            :search="search"
            sort-by="calories"
            class="elevation-1"
    >
        <template v-slot:top>
            <v-toolbar flat color="white">
                <v-toolbar-title>{{ title }}</v-toolbar-title>
                <v-divider
                        class="mx-4"
                        inset
                        vertical
                ></v-divider>
                <v-text-field
                        v-model="search"
                        label="Search"
                        single-line
                        hide-details
                        clearable
                ></v-text-field>
                <v-spacer></v-spacer>
                <v-dialog v-model="dialog" max-width="500px">
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                                color="primary"
                                dark
                                class="mb-2"
                                v-bind="attrs"
                                v-on="on"
                        >New Item
                        </v-btn>
                    </template>
                    <v-card>
                        <v-card-title>
                            <span class="headline">{{ formTitle }}</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-form ref="form">
                                        <v-col cols="12" md="12" v-for="field in fields" v-bind:key="field.name"
                                               v-bind:field="field">
                                            <v-select v-if="field.items"
                                                      :disabled="editedIndex > -1 && field.noEditingAllowed"
                                                      v-model="editedItem[field.name]"
                                                      :items="itemsKeys[field.itemsKey]" :label="field.label" clearable
                                                      :rules="field.rules">
                                                <template v-slot:prepend-item>
                                                    <v-text-field
                                                            append-icon="mdi-magnify"
                                                            label="Search"
                                                            single-line
                                                            hide-details
                                                            clearable
                                                            @input="val => filter(field.items, val, field.itemsKey)"
                                                    ></v-text-field>
                                                    <v-divider class="mt-2"></v-divider>
                                                </template>
                                            </v-select>
                                            <v-text-field v-if="!field.items"
                                                          :disabled="editedIndex > -1 && field.noEditingAllowed"
                                                          v-model="editedItem[field.name]" :label="field.label"
                                                          :rules="field.rules"></v-text-field>
                                        </v-col>
                                    </v-form>
                                </v-row>
                            </v-container>
                        </v-card-text>

                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                            <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <v-dialog v-model="delConfirm" persistent max-width="800">
                    <v-card>
                        <v-card-title class="headline">Delete confirmation</v-card-title>
                        <v-card-text>Are you sure you want to delete [{{ currentDelItem.name }}] from [{{ title }}]?
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="green darken-1" text @click="delConfirm = false">Cancel</v-btn>
                            <v-btn color="green darken-1" text @click="confirmDeleteItem()">Approve</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-toolbar>
        </template>
        <template v-slot:item.actions="{ item }">
            <v-icon
                    small
                    class="mr-2"
                    @click="editItem(item)"
            >
                mdi-pencil
            </v-icon>
            <v-icon
                    small
                    @click="deleteItem(item)"
            >
                mdi-delete
            </v-icon>
        </template>
        <template v-slot:no-data>
            <v-btn color="primary" @click="load">Reload</v-btn>
        </template>
    </v-data-table>
</template>

<script>
    export default {
        props: ['title', 'fields', 'headers', 'list', 'loading'],

        data: () => {
            return {
                search: '',
                dialog: false,
                editing: false,
                delConfirm: false,
                currentDelItem: {},
                editedIndex: -1,
                itemsKeys: {},
                editedItem: {},
                defaultItem: {},
            };
        },

        computed: {
            formTitle() {
                return this.editedIndex === -1 ? 'New Item' : 'Edit Item';
            }
        },

        watch: {
            dialog(val) {
                val || this.close();
            },
            fields: {
                handler: function (fields) {
                    let defaultItem = {};
                    let itemsKeys = {};
                    (fields || []).forEach((field) => {
                        defaultItem[field.name] = field.default;
                        if (field.items) {
                            field.itemsKey = field.name + 'Items';
                            itemsKeys[field.itemsKey] = field.items;
                        }
                    });
                    this.itemsKeys = itemsKeys;
                    this.editedItem = defaultItem;
                    this.defaultItem = defaultItem;
                },
                deep: true
            }
        },

        /*created() {
            this.load();
        },*/

        methods: {
            save() {
                if (this.editing) return;
                this.$refs.form.validate() && (this.editing = true) && this.$emit(this.editedIndex > -1 ? 'mod' : 'add', Object.assign({}, this.editedItem));
            },

            saveCallback(close) {
                this.editing = false;
                close && this.close();
            },

            deleteItem(item) {
                this.delConfirm = true;
                this.currentDelItem = item;
            },

            confirmDeleteItem() {
                this.delConfirm = false;
                this.$emit('del', this.currentDelItem);
            },

            editItem(item) {
                this.editedIndex = this.list.indexOf(item);
                this.editedItem = Object.assign({}, item);
                this.dialog = true;
            },

            load() {
                this.$emit('get');
            },

            filter(items, query, itemsKey) {
                this.itemsKeys[itemsKey] = query ? items.filter(item => item.indexOf(query) !== -1) : items;
            },

            close() {
                this.dialog = false;
                this.editing = false;
                this.$nextTick(() => {
                    this.editedItem = Object.assign({}, this.defaultItem);
                    this.editedIndex = -1;
                    this.$refs.form.reset();
                })
            },
        },
    }
</script>