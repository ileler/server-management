<template>
    <div>
        <Table ref="groupsTable" title="Groups" :fields="groupsFields" :headers="groupsHeaders" :loading="groupsLoading"
               :list="groupsList"
               v-on:load="groupsLoad" v-on:add="groupsAdd" v-on:mod="groupsMod" v-on:del="groupsDel"/>
        <hr/>
        <Table ref="serversTable" title="Servers" :fields="serversFields" :headers="serversHeaders" :loading="serversLoading"
               :list="serversList"
               v-on:load="serversLoad" v-on:add="serversAdd" v-on:mod="serversMod" v-on:del="serversDel"/>
        <hr/>
        <Table ref="servicesTable" title="Services" :fields="servicesFields" :headers="servicesHeaders" :loading="servicesLoading"
               :list="servicesList"
               v-on:load="servicesLoad" v-on:add="servicesAdd" v-on:mod="servicesMod" v-on:del="servicesDel"/>
    </div>
</template>

<script>
    import axios from 'axios';
    import Table from './Table';

    var index;

    const HTTP = axios.create({
        baseURL: process.env.VUE_APP_CTX || '/',
        timeout: 3000,
        headers: {}
    });

    const FormRules = {
        required: value => !!value || 'Required.',
        ip: value => /^(?!0)(?!.*\.$)((1?\d?\d|25[0-5]|2[0-4]\d)(\.|$)){4}$/.test(value) || 'IP format is incorrect.',
        port: value => /^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/.test(value) || 'Port format is incorrect.',
        counter: ({min, max}) => {
            return value => ((min === undefined || (value && value.length >= min)) && (max === undefined || (value && value.length <= max))) || ('Incorrect length format ' + (min === undefined ? 0 : min) + '-' + (max === undefined ? 0 : max) + '.');
        },
    }

    const GroupsListProxy = {
        items: () => {
            index.serversFields[1].items = [];
            index.groupsList.forEach(obj => index.serversFields[1].items.push({text: obj.name, value: obj.name}));
        },
        set: (groupsList) => {
            index.groupsList = groupsList;
            GroupsListProxy.items();
        },
        add: (item) => {
            index.groupsList.push(item);
            GroupsListProxy.items();
        },
        mod: (item) => {
            index.groupsList.push(item);
            GroupsListProxy.items();
        },
        del: (item) => {
            index.groupsList.remove(item);
            GroupsListProxy.items();
        },
    };

    const ServersListProxy = {
        items: () => {
            index.servicesFields[1].items = [];
            index.serversList.forEach(obj => index.servicesFields[1].items.push({text: obj.name + ' ('+obj.id+')', value: obj.name}));
        },
        set: (groupsList) => {
            index.serversList = groupsList;
            ServersListProxy.items();
        },
        add: (item) => {
            index.serversList.push(item);
            ServersListProxy.items();
        },
        mod: (item) => {
            index.serversList.push(item);
            ServersListProxy.items();
        },
        del: (item) => {
            index.serversList.remove(item);
            ServersListProxy.items();
        },
    };

    const ServicesListProxy = {
        set: (groupsList) => {
            index.servicesList = groupsList;
        },
        add: (item) => {
            index.servicesList.push(item);
        },
        mod: (item) => {
            index.servicesList.push(item);
        },
        del: (item) => {
            index.servicesList.remove(item);
        },
    };

    const ServiceProxy = {
        add: (path, callback) => (function (item) {
            let result;
            HTTP.post('/' + path, item)
                .then((function (response) {
                    console.log(response);
                    result = response.data;
                }).bind(this))
                .catch((function (error) {
                    console.log(error);
                    result = null;
                }).bind(this))
                .then((function () {
                    // always executed
                    result ? callback(item) : setTimeout(() => alert('Data already exists or the system is abnormal.'), 500);
                }).bind(index));
        }),
        del: (path, callback) => (function (item) {
            let result;
            HTTP.delete('/' + path + '?name=' + item.name)
                .then((function (response) {
                    console.log(response);
                    result = response.data;
                }).bind(this))
                .catch((function (error) {
                    console.log(error);
                    result = null;
                }).bind(this))
                .then((function () {
                    // always executed
                    result && callback(item);
                }).bind(this));
        }),
        mod: (path, callback) => (function (item) {
            let result;
            HTTP.put('/' + path, item)
                .then((function (response) {
                    console.log(response);
                    result = response.data;
                }).bind(this))
                .catch((function (error) {
                    console.log(error);
                    result = null;
                }).bind(this))
                .then((function () {
                    // always executed
                    result && callback(item);
                }).bind(this));
        }),
        get: (path, loadingKey, callback) => (function () {
            let loadStart = Date.now();
            let result;
            this[loadingKey] = true;
            HTTP.get('/' + path)
                .then((function (response) {
                    console.log(response);
                    result = response.data;
                }).bind(this))
                .catch((function (error) {
                    console.log(error);
                    result = null;
                }).bind(this))
                .then((function () {
                    // always executed
                    setTimeout(() => {
                        this[loadingKey] = false;
                        callback(result || []);
                    }, (Date.now() - loadStart) > (1.5 * 1000) ? 0 : 800);    //避免过快更新UI导致用户视觉感觉不舒服(像闪屏)
                }).bind(this));
        }),
    };

    const groups = {
        data: {
            groupsLoading: false,
            groupsList: [],
            groupsFields: [
                {name: 'name', label: 'Name', noEditingAllowed: true, rules: [FormRules.required, FormRules.counter({max: 50})]},
            ],
            groupsHeaders: [
                {text: 'Name(ID)', value: 'name'},
                {text: 'Actions', value: 'actions', sortable: false},
            ],
        },
        methods: {
            groupsLoad: ServiceProxy.get('groups', 'groupsLoading', (items) => GroupsListProxy.set(items)),
            groupsAdd: ServiceProxy.add('group', () => {index.$refs.groupsTable.close(); index.groupsLoad();}),
            groupsMod: ServiceProxy.mod('group', () => {index.$refs.groupsTable.close(); index.groupsLoad();}),
            groupsDel: ServiceProxy.del('group', () => index.groupsLoad()),
        }
    }

    const servers = {
        data: {
            serversLoading: false,
            serversList: [],
            serversFields: [
                {name: 'name', label: 'Name', noEditingAllowed: true, rules: [FormRules.required, FormRules.counter({max: 50})]},
                {name: 'group', label: 'Group', items: [], rules: [FormRules.required]},
                {name: 'ip', label: 'IP', rules: [FormRules.required, FormRules.ip]},
                {name: 'port', label: 'Port', default: 22, rules: [FormRules.required, FormRules.port]},
                {name: 'desc', label: 'Desc'},
                {name: 'username', label: 'Username', rules: [FormRules.required]},
                {name: 'password', label: 'Password', rules: [FormRules.required]},
            ],
            serversHeaders: [
                {text: 'Name(ID)', value: 'name'},
                {text: 'Group', value: 'group'},
                {text: 'IP', value: 'ip'},
                {text: 'Port', value: 'port'},
                {text: 'Username', value: 'username'},
                {text: 'Password', value: 'password'},
                {text: 'Desc', value: 'desc'},
                {text: 'Actions', value: 'actions', sortable: false},
            ],
        },
        methods: {
            serversLoad: ServiceProxy.get('servers', 'serversLoading', (items) => ServersListProxy.set(items)),
            serversAdd: ServiceProxy.add('server', () => {index.$refs.serversTable.close(); index.serversLoad();}),
            serversMod: ServiceProxy.mod('server', () => {index.$refs.serversTable.close(); index.serversLoad();}),
            serversDel: ServiceProxy.del('server', () => index.serversLoad()),
        }
    }

    const services = {
        data: {
            servicesLoading: false,
            servicesList: [],
            servicesFields: [
                {name: 'name', label: 'Name', noEditingAllowed: true, rules: [FormRules.required, FormRules.counter({max: 50})]},
                {name: 'server', label: 'Server', items: [], rules: [FormRules.required]},
                {name: 'desc', label: 'Desc'},
                {name: 'type', label: 'Type'},
                {name: 'user', label: 'User', default: 'wenwen'},
            ],
            servicesHeaders: [
                {text: 'Name(ID)', value: 'name'},
                {text: 'Server', value: 'server'},
                {text: 'Desc', value: 'desc'},
                {text: 'Type', value: 'type'},
                {text: 'User', value: 'user'},
                {text: 'Actions', value: 'actions', sortable: false},
            ]
        },
        methods: {
            servicesLoad: ServiceProxy.get('services', 'servicesLoading', (items) => ServicesListProxy.set(items)),
            servicesAdd: ServiceProxy.add('service', () => {index.$refs.servicesTable.close(); index.servicesLoad();}),
            servicesMod: ServiceProxy.mod('service', () => {index.$refs.servicesTable.close(); index.servicesLoad();}),
            servicesDel: ServiceProxy.del('service', () => index.servicesLoad()),
        }
    }


    export default {
        components: {
            Table,
        },

        data: (vm) => {
            index = vm;
            return Object.assign({}, groups.data, servers.data, services.data);
        },

        methods: Object.assign({}, groups.methods, servers.methods, services.methods)
    };
</script>