<template>
    <div>
        <Table ref="table" :title="title" :fields="fields" :headers="headers" :list="list"
               :loading="loading"
               v-on:get="get" v-on:add="add" v-on:mod="mod" v-on:del="del"/>
        <v-dialog v-model="delConfirm" persistent max-width="800">
            <v-card>
                <v-card-title class="headline">Whether to force deletion?</v-card-title>
                <v-card-text>Forced deletion will delete the included subset (Group>Server>Service).</v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="green darken-1" text @click="delConfirm = false">Cancel</v-btn>
                    <v-btn color="green darken-1" text @click="forcedDel()">Approve</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
    import {FormRules} from '../../utils/Const';
    import HTTP from '../../utils/Http';
    import Table from './_Table';

    export default {
        props: ['groupList'],

        components: {
            Table,
        },

        data: () => {
            return {
                title: 'Servers',
                list: [],
                delConfirm: false,
                currentDelItem: null,
                loading: false,
                fields: [
                    {
                        name: 'name',
                        label: 'Name(ID)',
                        noEditingAllowed: true,
                        rules: [FormRules.required, FormRules.counter({max: 50})]
                    },
                    {name: 'group', label: 'Group', items: [], rules: [FormRules.required]},
                    {name: 'ip', label: 'IP', rules: [FormRules.required, FormRules.ip]},
                    {name: 'port', label: 'Port', default: 22, rules: [FormRules.required, FormRules.port]},
                    {name: 'desc', label: 'Desc'},
                    {name: 'username', label: 'Username', rules: [FormRules.required]},
                    {name: 'password', label: 'Password', rules: [FormRules.required]},
                ],
                headers: [
                    {text: 'Name(ID)', value: 'name'},
                    {text: 'Group', value: 'group'},
                    {text: 'IP', value: 'ip'},
                    {text: 'Port', value: 'port'},
                    {text: 'Username', value: 'username'},
                    {text: 'Password', value: 'password'},
                    {text: 'Desc', value: 'desc'},
                    {text: 'Actions', value: 'actions', sortable: false},
                ],
            };
        },

        watch: {
            groupList: {
                handler: function (groupList) {
                    this.fields[1].items = groupList;
                },
                deep: true
            }
        },

        methods: {
            add: function (item) {
                HTTP.add('/server', item, (result) => {
                    this.$refs.table.saveCallback(result === true);
                    if (result === true) {
                        this.get();
                    } else {
                        alert('Data already exists or the system is abnormal.');
                    }
                });
            },
            del: function (item, forced) {
                this.currentDelItem = item;
                HTTP.del('/server?name=' + item.name + '&forced=' + (forced === true), (result) => {
                    if (result === true) {
                        this.get();
                        this.delConfirm = false;
                    } else {
                        if (forced !== true) {
                            this.delConfirm = true;
                        } else {
                            alert('Data unexists or the system is abnormal.');
                        }
                    }
                });
            },
            forcedDel: function () {
                this.del(this.currentDelItem, true);
            },
            mod: function (item) {
                HTTP.mod('/server', item, (result) => {
                    this.$refs.table.saveCallback(result === true);
                    if (result === true) {
                        this.get();
                    } else {
                        alert('Data unexists or the system is abnormal.');
                    }
                });
            },
            get: function () {
                this.loading = true;
                HTTP.get('/servers', (result) => {
                    this.loading = false;
                    if (result instanceof Array) {
                        this.list = result;
                        this.$emit('list', this.list);
                    } else {
                        alert('System is abnormal.');
                    }
                });
            },
        }
    };
</script>