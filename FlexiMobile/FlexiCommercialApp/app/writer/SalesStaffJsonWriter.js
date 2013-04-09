Ext.define('FlexiCommercial.writer.SalesStaffJsonWriter', {
    extend: 'Ext.data.writer.Json',

    alias: 'writer.SalesStaffJsonWriter',

    config: {
        type: 'json'
    },

    requires: [
        'FlexiCommercial.model.SalesStaff'
    ],

    getRecordData: function (record) {
        var customJson = {
            uuid: record.get('uuid'),
            firstname: record.get('firstName'),
            lastname: record.get('lastName'),
            email: record.get('email'),
            phone: record.get('phone'),
            dealer: {
                name: record.get('dealer')
            }
        };
        return customJson;
    }
});