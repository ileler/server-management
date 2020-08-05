<template>
    <div>
        <GroupTable ref="groupTable" v-on:list="setGroupList"/>
        <hr/>
        <ServerTable ref="serverTable" v-on:list="setServerList" :groupList="groupList"/>
        <hr/>
        <ServiceTable ref="serviceTable" :serverList="serverList"/>
    </div>
</template>

<script>
    import GroupTable from './table/GroupTable';
    import ServerTable from './table/ServerTable';
    import ServiceTable from './table/ServiceTable';

    export default {
        components: {
            GroupTable,
            ServerTable,
            ServiceTable,
        },

        data: () => {
            return {
                groupList: [],
                serverList: []
            };
        },

        mounted() {
            this.$refs.groupTable.get();
        },

        methods: {
            setGroupList: function (list) {
                this.groupList = [];
                this.$refs.serverTable.get();
                list.forEach(item => this.groupList.push(item.name));
            },
            setServerList: function (list) {
                this.serverList = [];
                this.$refs.serviceTable.get();
                list.forEach(item => this.serverList.push({text: item.name + '('+item.id+')', value: item.name}));
            },
        }
    };
</script>