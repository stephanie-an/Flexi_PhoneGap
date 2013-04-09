Ext.define('FlexiCommercial.store.RuntimeConfigStore', {
    extend: 'Ext.data.Store',

    requires: [
        'FlexiCommercial.model.RuntimeConfig'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        model: 'FlexiCommercial.model.RuntimeConfig',
        storeId: 'RuntimeConfigStore',
        proxy: {
            type: 'localstorage',
            id: 'RuntimeConfig'
        }
    }
});