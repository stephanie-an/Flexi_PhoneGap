Ext.define('FlexiCommercial.store.LocalContentStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Content'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        model: 'FlexiCommercial.model.Content',
        storeId: 'LocalContentStore',
        proxy: {
            type: 'localstorage',
            id: 'Content'
        }
    }
});