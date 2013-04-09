Ext.define('FlexiCommercial.store.EquipmentStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Equipment',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        autoLoad: false,
        autoSync: false,
        clearOnPageLoad: false,
        destroyRemovedRecords: false,
        model: 'FlexiCommercial.model.Equipment',
        remoteGroup: false,
        remoteFilter: false,
        remoteSort: false,
        storeId: 'EquipmentStore',
        syncRemovedRecords: false,
        proxy: {
            type: 'rest',
            enablePagingParams: false,
            noCache: false,
            timeout: 10000,
            url: '/service/equipment',
            reader: {
                type: 'json'
            }
        },
        listeners: [
            {
                fn: 'onLoad',
                event: 'load'
            }
        ]
    },

    onLoad: function (store, records, successful, operation, eOpts) {
        var lStore;

        if (successful) {
            lStore = Ext.getStore('LocalEquipmentStore');
            CommonUtils.populateLocalStore(store, lStore);
            CommonUtils.onlineLoadComplete('equipment',new Date());
        }
    }

});