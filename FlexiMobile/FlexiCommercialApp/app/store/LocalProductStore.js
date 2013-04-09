Ext.define('FlexiCommercial.store.LocalProductStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Product'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        remoteGroup: false,
        remoteFilter: false,
        remoteSort: false,
        model: 'FlexiCommercial.model.Product',
        storeId: 'LocalProductStore',
        proxy: {
            type: 'localstorage',
            id: 'Product'
        },
        sorters: [
            {
                property: 'name',
                direction: 'ASC'
            }
        ]
    }
});