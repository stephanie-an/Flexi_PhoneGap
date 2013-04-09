Ext.define('FlexiCommercial.store.LocalEquipmentStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Equipment'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        remoteGroup: false,
        remoteFilter: false,
        remoteSort: false,
        model: 'FlexiCommercial.model.Equipment',
        storeId: 'LocalEquipmentStore',
        proxy: {
            type: 'localstorage',
            id: 'Equipment'
        },
        sorters: [
            {
                property: 'name',
                direction: 'ASC'
            }
        ]
    }
});