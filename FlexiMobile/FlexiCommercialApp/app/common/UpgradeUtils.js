Ext.define('FlexiCommercial.common.UpgradeUtils', {
    singleton: true,

    alternateClassName: 'UpgradeUtils',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.model.Quote',
        'FlexiCommercial.store.LocalQuoteStore'
    ],

    loadConfig: function(configStore) {
        configStore.load();
        var config = configStore.getAt(0);

        if (config == undefined) {
            // No config exists, create it!
            config = new FlexiCommercial.model.RuntimeConfig();
            config.set('version',0);
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

        return config;
    },

    updateApplication: function() {
        var done = false;
        var configStore = Ext.getStore('RuntimeConfigStore');
        var config = this.loadConfig(configStore);

        var version = config.get('version');
        if (version == null || version == undefined) {
            version = 0;
        }

        // Extend this when version change.
        while (!done) {
            switch (version) {
                case 0: version = this.upgrade0to1(config);
                        break;
                default: done = true;
            }
        }

        // Check if we have products, if not reset config
        var localProductStore = Ext.getStore('LocalProductStore');
        if (localProductStore.getAllCount() == 0) {
            config.set('contentlastcheck',new Date(0));
            config.set('equipmentlastcheck',new Date(0));
            config.set('productlastcheck',new Date(0));
            config.set('rateslastcheck',new Date(0));
            config.set('taxlastcheck',new Date(0));
            config.set('tickerlastcheck',new Date(0));
        }

        config.set('version',version);
        config.setDirty();
        configStore.sync();
    },

    upgrade0to1: function(config) {
        console.info('Updating app from v0 to v1');

        // Quote format changed, copy Customer into Quote, and delete Customer.
        var store = Ext.getStore('LocalQuoteStore');
        Ext.define('Customers', {
            extend: 'Ext.data.Model',
            config: {
                idProperty: 'uuid',
                identifier: {
                    type: 'uuid'
                },
                fields: [
                    { name: 'uuid', type: 'auto' },
                    { name: 'name', type: 'string' },
                    { name: 'email', type: 'string' },
                    { name: 'phone', type: 'string' },
                    { name: 'comment', type: 'string' }
                ]
            }
        });
        var cStore = Ext.create('Ext.data.Store', {
            model: 'Customers',
            proxy: {
                type: 'localstorage',
                id: 'Customer'
            }
        });

        cStore.load();

        store.clearFilter();
        store.each(function(item, index, length) {
            var custId = item.get('customer_id');
            if (custId !== null && custId !== undefined && custId.length > 0) {
                var custRecord = cStore.getById(custId);
                if (custRecord !== null && custRecord !== undefined) {
                    item.set('name',custRecord.get('name'));
                    item.set('email',custRecord.get('email'));
                    item.set('phone',custRecord.get('phone'));
                    item.set('comment',custRecord.get('comment'));
                    cStore.remove(custRecord);
                    item.set('customer_id',null);
                    item.setDirty();
                } else {
                    console.info('Removing Broken record');
                    store.remove(item);
                }
            }
        });
        cStore.sync();
        store.sync();

        // config fields changed, add new fields
        config.set('contentlastcheck',new Date(0));
        config.set('equipmentlastcheck',new Date(0));
        config.set('productlastcheck',new Date(0));
        config.set('rateslastcheck',new Date(0));
        config.set('taxlastcheck',new Date(0));
        config.set('tickerlastcheck',new Date(0));
        // should be no need to sync, it will be done automagically in the updateApplication

        return 1;
    }
});

