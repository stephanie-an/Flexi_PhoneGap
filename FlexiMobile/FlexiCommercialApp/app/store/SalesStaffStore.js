Ext.define('FlexiCommercial.store.SalesStaffStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.SalesStaff',
        'FlexiCommercial.writer.SalesStaffJsonWriter'
    ],

    config: {
        autoSync: true,
        clearOnPageLoad: false,
        destroyRemovedRecords: false,
        model: 'FlexiCommercial.model.SalesStaff',
        storeId: 'SalesStaffStore',
        syncRemovedRecords: false,
        proxy: {
            type: 'rest',
            noCache: false,
            url: '/service/salesstaff',
            reader: {
                type: 'json'
            },
            writer: {
                type: 'SalesStaffJsonWriter'
            }
        }
    }
});