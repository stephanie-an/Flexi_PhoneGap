Ext.define('FlexiCommercial.common.Utils', {
    singleton: true,

    alternateClassName: 'CommonUtils',

    requires: [
        'FlexiCommercial.model.RuntimeConfig',
        'FlexiCommercial.store.RuntimeConfigStore'
    ],

    getCurrentUser: function () {
        var store = Ext.getStore(flexi.salesStaffStore);
        var c = store.find('isCurrent', true);
        var cUser;

        if (c != -1) {
            // Get the Current User
            cUser = store.getAt(c);
            flexi.currentUser = cUser.getId();
        }
        return cUser;
    },

    getItemById: function (id, store) {
        var c = store.find('id', id);

        return store.getAt(c);
    },

    getEquipmentById: function (id) {
        var store = Ext.getStore(flexi.equipmentStore);

        return FlexiCommercial.common.Utils.getItemById(id, store);
    },

    getProductById: function (id) {
        var store = Ext.getStore(flexi.productStore);

        return FlexiCommercial.common.Utils.getItemById(id, store);
    },

    getTaxById: function (id) {
        var store = Ext.getStore(flexi.taxStore);

        return FlexiCommercial.common.Utils.getItemById(id, store);
    },

    getRateByFilter: function (product, term, amount) {
        var store = Ext.getStore(flexi.ratesStore);
        var productId = product.get('id');

        var results = store.queryBy(function (item) {
            var rProductId, rTerm, rMin, rMax;

            rProductId = item.get('productid');
            rTerm = item.get('term');
            rMin = item.get('minamount');
            rMax = item.get('maxamount');

            return (rProductId == productId) &&
                (rTerm == term) &&
                (rMin <= amount) &&
                (rMax >= amount);
        });

        return results.first();
    },

    findQuotesByStatus: function (status) {
        var store = Ext.getStore(flexi.quoteStore);
        var results;

        if (status !== null && status !== undefined) {
            results = store.queryBy(function (item) {
                var qStatus = item.get('status');
                return qStatus === status;
            });
        }

        return results;
    },

    loadOnlineStores: function() {
        var cStore = Ext.getStore('ContentStore');
        var eStore = Ext.getStore('EquipmentStore');
        var pStore = Ext.getStore('ProductStore');
        var rStore = Ext.getStore('RatesStore');
        var tStore = Ext.getStore('TaxStore');
        var tickStore = Ext.getStore('TickerStore');

        var configStore = Ext.getStore('RuntimeConfigStore');
        configStore.load();
        var config = configStore.getAt(0);

        if (config == undefined) {
            // No config exists, create it!
            config = new FlexiCommercial.model.RuntimeConfig();
            config.set('contentlastcheck',new Date(0));
            config.set('equipmentlastcheck',new Date(0));
            config.set('productlastcheck',new Date(0));
            config.set('rateslastcheck',new Date(0));
            config.set('taxlastcheck',new Date(0));
            config.set('tickerlastcheck',new Date(0));
            configStore.add(config);
            config.setDirty();
            configStore.sync();
        }
        cStore.getProxy().setExtraParam('lastmodified',Ext.Date.format(config.get('contentlastcheck'),'Uu'));
        cStore.load();

        eStore.getProxy().setExtraParam('lastmodified',Ext.Date.format(config.get('equipmentlastcheck'),'Uu'));
        eStore.load();

        pStore.getProxy().setExtraParam('lastmodified',Ext.Date.format(config.get('productlastcheck'),'Uu'));
        pStore.load();

        rStore.getProxy().setExtraParam('lastmodified',Ext.Date.format(config.get('rateslastcheck'),'Uu'));
        rStore.load();

        tStore.getProxy().setExtraParam('lastmodified',Ext.Date.format(config.get('taxlastcheck'),'Uu'));
        tStore.load();

        tickStore.getProxy().setExtraParam('lastmodified',Ext.Date.format(config.get('tickerlastcheck'),'Uu'));
        tickStore.load();
    },

    onlineLoadComplete: function(storeType, lastdate) {
        var configStore = Ext.getStore('RuntimeConfigStore');
        var storeConfigField = storeType + 'lastcheck';
        var config = configStore.getAt(0);

        if (config !== undefined) {
            config.set(storeConfigField,lastdate);
            config.setDirty();
            configStore.sync();
        }
    },

    populateLocalStore: function (online, local) {
        online.clearFilter();
        local.clearFilter();

        online.each(function (record) {
            var _id, data, lRecord;

            _id = record.getId();
            lRecord = local.getById(_id);
            if (lRecord === null || lRecord == undefined) {
                // Add  a new record!
                data = local.add(record.data);
                data[0].setDirty();
            } else {
                // Update The Existing Records
                lRecord.setData(record.data);
                lRecord.setDirty();
            }
        });
        local.sync();
        local.fireEvent('refresh');
    },

    purgeNewQuotes: function (store) {
        store.queryBy(function (item) {
            var qStatus;

            qStatus = item.get('status');
            if (qStatus !== 'NEW') {
                return false;
            }

            store.remove(item);
            return true;
        });
        store.sync();
    },

    removeExpiredItems: function (store) {
        var now = new Date();

        store.each(function (item) {
            var end = item.get('expire');
            if (end !== null && end < now) {
                store.remove(item);
            }
        });
        store.sync();
    },

    removeOldQuotes: function (status, period) {
        var qStore = Ext.getStore(flexi.quoteStore);

        if (status !== null && status !== undefined) {
            if (period !== null && period !== undefined && period > 0) {
                var clearDate = Ext.Date.add(new Date(), Ext.Date.DAY, period * -7);
                qStore.clearFilter();
                qStore.each(function (record) {
                    var rStatus = record.get('status');
                    var rDate = record.get('lastdate');
                    if (rStatus == status && rDate < clearDate) {
                        qStore.remove(record);
                    }
                });
                qStore.sync();
            }
        }
    },

    retryPendingQuotes: function (store) {
        var onlineStore = Ext.getStore('QuoteStore');

        store.clearFilter();
        store.filter('status', 'PENDING');
        store.each(function (item) {
            item.set('lastdate', new Date());
            item.setDirty();
            store.sync();
            var result = onlineStore.add(item);
            result[0].setDirty();
            onlineStore.sync();
        });
    },

    disableAllPanels: function(home) {
        var tabItems = home.getTabBar().getItems();

        tabItems.each(function (item,index) {
           item.disable();
        });
    },

    enableAllPanels: function(home) {
        var tabItems = home.getTabBar().getItems();

        tabItems.each(function (item,index) {
            item.enable();
        });
    }
});

