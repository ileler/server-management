<template>
    <div>
        <Table ref="table" :title="title" :fields="fields" :headers="headers" :list="list"
               :loading="loading"
               v-on:get="get" v-on:add="add" v-on:mod="mod" v-on:del="del"/>
    </div>
</template>

<script>
    import {FormRules} from '../../utils/Const';
    import HTTP from '../../utils/Http';
    import Table from './_Table';

    export default {
        props: ['serverList'],

        components: {
            Table,
        },

        data: () => {
            return {
                title: 'Services',
                list: [],
                loading: false,
                fields: [
                    {
                        name: 'name',
                        label: 'Name(ID)',
                        noEditingAllowed: true,
                        rules: [FormRules.required, FormRules.counter({max: 50})]
                    },
                    {name: 'server', label: 'Server', items: [], rules: [FormRules.required]},
                    {name: 'desc', label: 'Desc'},
                    {name: 'type', label: 'Type'},
                    {name: 'user', label: 'User', default: 'admin'},
                ],
                headers: [
                    {text: 'Name(ID)', value: 'name'},
                    {text: 'Server', value: 'serverID'},
                    {text: 'Desc', value: 'desc'},
                    {text: 'Type', value: 'type'},
                    {text: 'User', value: 'user'},
                    {text: 'Actions', value: 'actions', sortable: false},
                ],
            };
        },

        watch: {
            serverList: {
                handler: function (serverList) {
                    let serverMap = {};
                    (this.fields[1].items = serverList).forEach(item => {
                        serverMap[item.value] = item.text;
                    });
                    this.serverMap = serverMap;
                },
                deep: true
            }
        },

        methods: {
            add: function (item) {
                HTTP.add('/service', item, (result) => {
                    this.$refs.table.saveCallback(result === true);
                    if (result === true) {
                        this.get();
                    } else {
                        alert('Data already exists or the system is abnormal.');
                    }
                });
            },
            del: function (item) {
                HTTP.del('/service?name=' + item.name, (result) => {
                    if (result === true) {
                        this.get();
                    } else {
                        alert('Data unexists or the system is abnormal.');
                    }
                });
            },
            mod: function (item) {
                HTTP.mod('/service', item, (result) => {
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
                HTTP.get('/services', (result) => {
                    this.loading = false;
                    if (result instanceof Array) {
                        (this.list = result).forEach(item => item.serverID = this.serverMap[item.server]);
                    } else {
                        alert('System is abnormal.');
                    }
                });
            },
        }
    };
</script>