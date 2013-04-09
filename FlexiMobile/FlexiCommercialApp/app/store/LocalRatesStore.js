Ext.define('FlexiCommercial.store.LocalRatesStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Rates'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        model: 'FlexiCommercial.model.Rates',
        storeId: 'LocalRatesStore',
        proxy: {
            type: 'localstorage',
            id: 'Rates'
        }
    }
});