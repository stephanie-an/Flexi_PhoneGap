Ext.define('FlexiCommercial.store.ProductStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Product',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        autoLoad: false,
        autoSync: false,
        clearOnPageLoad: false,
        destroyRemovedRecords: false,
        model: 'FlexiCommercial.model.Product',
        storeId: 'ProductStore',
        syncRemovedRecords: false,
        proxy: {
            type: 'rest',
            enablePagingParams: false,
            noCache: false,
            timeout: 10000,
            url: '/service/product',
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
            lStore = Ext.getStore('LocalProductStore');
            CommonUtils.populateLocalStore(store, lStore);
            CommonUtils.onlineLoadComplete('product',new Date());
        }
    }

});