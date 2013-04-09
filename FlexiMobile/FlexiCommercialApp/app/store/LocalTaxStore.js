Ext.define('FlexiCommercial.store.LocalTaxStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Tax'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        model: 'FlexiCommercial.model.Tax',
        storeId: 'LocalTaxStore',
        proxy: {
            type: 'localstorage',
            id: 'Tax'
        }
    }
});