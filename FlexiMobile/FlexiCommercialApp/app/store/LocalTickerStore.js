Ext.define('FlexiCommercial.store.LocalTickerStore', {
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
        storeId: 'LocalTickerStore',
        proxy: {
            type: 'localstorage',
            id: 'TickerContent'
        }
    }
});