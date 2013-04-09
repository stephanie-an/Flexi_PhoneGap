Ext.define('FlexiCommercial.store.RatesStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Rates',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        autoLoad: false,
        autoSync: false,
        clearOnPageLoad: false,
        destroyRemovedRecords: false,
        model: 'FlexiCommercial.model.Rates',
        storeId: 'RatesStore',
        syncRemovedRecords: false,
        proxy: {
            type: 'rest',
            enablePagingParams: false,
            noCache: false,
            timeout: 10000,
            url: '/service/rates',
            reader: {
                type: 'json'
            }
        },
        listeners: [
            {
                fn: 'onRatesStoreLoad',
                event: 'load'
            }
        ]
    },

    onRatesStoreLoad: function (store, records, successful, operation, eOpts) {
        var lStore;
        if (successful) {
            lStore = Ext.getStore('LocalRatesStore');
            CommonUtils.populateLocalStore(store, lStore);
            CommonUtils.onlineLoadComplete('rates',new Date());
        }
    }

});