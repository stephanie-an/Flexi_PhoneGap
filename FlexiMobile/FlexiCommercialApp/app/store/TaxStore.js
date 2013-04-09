Ext.define('FlexiCommercial.store.TaxStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Tax',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        autoLoad: false,
        autoSync: false,
        clearOnPageLoad: false,
        destroyRemovedRecords: false,
        model: 'FlexiCommercial.model.Tax',
        storeId: 'TaxStore',
        syncRemovedRecords: false,
        proxy: {
            type: 'rest',
            enablePagingParams: false,
            noCache: false,
            timeout: 10000,
            url: '/service/tax',
            reader: {
                type: 'json'
            }
        },
        listeners: [
            {
                fn: 'onTaxStoreLoad',
                event: 'load'
            }
        ]
    },

    onTaxStoreLoad: function (store, records, successful, operation, eOpts) {
        var lStore;

        if (successful) {
            lStore = Ext.getStore('LocalTaxStore');
            CommonUtils.populateLocalStore(store, lStore);
            CommonUtils.onlineLoadComplete('tax',new Date());
        }
    }

});