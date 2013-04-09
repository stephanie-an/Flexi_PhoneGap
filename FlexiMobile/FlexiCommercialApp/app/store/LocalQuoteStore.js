Ext.define('FlexiCommercial.store.LocalQuoteStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.store.LocalEquipmentStore',
        'FlexiCommercial.store.LocalProductStore',
        'FlexiCommercial.store.LocalSalesStaffStore',
        'FlexiCommercial.store.LocalTaxStore',
        'FlexiCommercial.model.Quote'
    ],

    config: {
        autoLoad: true,
        autoSync: true,
        clearOnPageLoad: false,
        remoteGroup: false,
        remoteFilter: false,
        remoteSort: false,
        model: 'FlexiCommercial.model.Quote',
        storeId: 'LocalQuoteStore',
        listeners: [
            {
                fn: 'onLoad',
                event: 'load'
            }
        ],
        proxy: {
            type: 'localstorage',
            id: 'Quote'
        },
        sorters: [
            {
                property: 'lastdate',
                direction: 'ASC'
            }
        ]
    },

    onLoad: function (store, records, successful, operation, eOpts) {
        var eStore = Ext.getStore('LocalEquipmentStore');
        var pStore = Ext.getStore('LocalProductStore');
        var sStore = Ext.getStore('LocalSalesStaffStore');
        var tStore = Ext.getStore('LocalTaxStore');

        Ext.each(records, function (record) {
            var eId = record.get('equipment_id');
            record.setEquipment(eStore.getById(eId));

            var pId = record.get('product_id');
            record.setProduct(pStore.getById(pId));

            var sId = record.get('salesstaff_id');
            record.setSalesStaff(sStore.getById(sId));

            var tId = record.get('tax_id');
            record.setTax(tStore.getById(tId));
        });
    }
});