Ext.define('FlexiCommercial.store.QuoteStore', {
    extend: 'Ext.data.Store',

    requires: [
        'FlexiCommercial.model.Quote',
        'FlexiCommercial.writer.QuoteJsonWriter'
    ],

    config: {
        clearOnPageLoad: false,
        model: 'FlexiCommercial.model.Quote',
        storeId: 'QuoteStore',
        proxy: {
            type: 'rest',
            url: '/service/quote',
            appendId: true,
            actionMethods: {
                create: 'POST',
                read: 'GET',
                update: 'PUT',
                destroy: 'DELETE'
            },
            reader: {
                type: 'json'
            },
            writer: {
                type: 'QuoteJsonWriter'
            }
        },
        listeners: [
            {
                fn: 'onJsonstoreWrite',
                event: 'write'
            }
        ]
    },

    onJsonstoreWrite: function (store, operation, eOpts) {
        var qStore = Ext.getStore('LocalQuoteStore');
        var cDate = new Date();
        var id, localPos, lCopy;

        if (operation.wasSuccessful()) {
            // We succesfully sent to the server, so set the status to SUBMITTED.
            var records = operation.getRecords();

            records.forEach(function (item, index, array) {
                item.set('status', 'SUBMITTED');
                item.set('lastdate',cDate);
                item.setDirty();
            });
           qStore.sync();
        } else {
            console.error('Why did we get an exception from the server but we worked?');
            console.error(response);
        }
    }

});