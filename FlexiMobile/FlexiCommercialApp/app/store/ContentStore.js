Ext.define('FlexiCommercial.store.ContentStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Content',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        autoLoad: false,
        autoSync: false,
        clearOnPageLoad: false,
        destroyRemovedRecords: false,
        model: 'FlexiCommercial.model.Content',
        remoteGroup: false,
        remoteSort: false,
        storeId: 'ContentStore',
        syncRemovedRecords: false,
        proxy: {
            type: 'rest',
            enablePagingParams: false,
            noCache: false,
            url: '/service/content',
            reader: {
                type: 'json'
            }
        },
        listeners: [
            {
                fn: 'onLoad',
                event: 'load'
            }
        ],
        filters: {
            filterFn: function (item) {
                var start = item.get('activate');
                var end = item.get('expire');
                var now = new Date().getTime();
                if ((start <= now) && (end === null || end >= now)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    },

    onLoad: function (store, records, successful, operation, eOpts) {
        var lStore;

        if (successful) {
            lStore = Ext.getStore('LocalContentStore');
            CommonUtils.populateLocalStore(store, lStore);
            CommonUtils.onlineLoadComplete('content',new Date());
        }
    }

});