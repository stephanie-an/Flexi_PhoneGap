Ext.define('FlexiCommercial.store.LocalDealerStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Dealer'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        model: 'FlexiCommercial.model.Dealer',
        storeId: 'LocalDealerStore',
        proxy: {
            type: 'localstorage',
            id: 'Dealer'
        }
    }
});