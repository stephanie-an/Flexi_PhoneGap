Ext.define('FlexiCommercial.store.LocalSalesStaffStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.SalesStaff'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        model: 'FlexiCommercial.model.SalesStaff',
        storeId: 'LocalSalesStaffStore',
        proxy: {
            type: 'localstorage',
            id: 'SalesStaff'
        }
    }
});