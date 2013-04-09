Ext.define('FlexiCommercial.controller.HomeController', {
    extend: 'Ext.app.Controller',

    requires: [
        'FlexiCommercial.view.HomeContainer'
    ],

    config: {
        views: [
            'HomeContainer',
            'CalculateInputPanel'
        ],

        refs: {
            homePanel: '#Home',
            calculateInputPanel: {
                selector: 'CalculateInputPanel',
                xtype: 'CalculateInputPanel',
                autoCreate: true
            },
            flexiBenefitsButton: '#Home [itemId=FlexiBenefitsButton]',
            calculateButton: '#Home [itemId=HomeCalculateButton]',
            latestOffersButton: '#Home [itemId=LatestOffersButton]',
            keyContactsButton: '#Home [itemId=KeyContactsButton]',
            promoTicker: '#Home [itemId=PromoTicker]'
        },

        control: {
            homePanel: {
                activate: 'onHomeActivate'
            },
            flexiBenefitsButton: {
                tap: 'onFlexiBenefitsButtonTap'
            },
            calculateButton: {
                tap: 'onHomeCalculateButtonTap'
            },
            latestOffersButton: {
                tap: 'onLatestOffersButtonTap'
            },
            keyContactsButton: {
                tap: 'onKeyContactsButtonTap'
            },
            promoTicker: {
                initialize: 'onPromoTickerInitialize'
            }
        }
    },

    onFlexiBenefitsButtonTap: function (button, e, options) {
        var panel = Ext.create('FlexiCommercial.view.GenericDisplayPanel', {
            title: 'Flexi Benefits'
        });

        var desc = this.getGenericContent(2);

        if (desc !== null && desc !== undefined) {
            panel.setHtml(desc.get('content'));
        } else {
            panel.setHtml('No Content Found...');
        }

        this.getHomePanel().push(panel);
    },

    onLatestOffersButtonTap: function (button, e, options) {
        var panel = Ext.create('FlexiCommercial.view.GenericDisplayPanel', {
            title: 'Latest Offers'
        });

        var desc = this.getGenericContent(3);

        if (desc !== null && desc !== undefined) {
            panel.setHtml(desc.get('content'));
        } else {
            panel.setHtml('No Content Found...');
        }

        this.getHomePanel().push(panel);
    },

    onKeyContactsButtonTap: function (button, e, options) {
        var panel = Ext.create('FlexiCommercial.view.GenericDisplayPanel', {
            title: 'Key Contacts'
        });

        var desc = this.getGenericContent(4);

        if (desc !== null && desc !== undefined) {
            panel.setHtml(desc.get('content'));
        } else {
            panel.setHtml('No Content Found...');
        }

        this.getHomePanel().push(panel);
    },

    getGenericContent: function (contentType) {
        var store = Ext.getStore(flexi.contentStore);
        var now = new Date().getTime();
        var foundFirst = false;

        store.clearFilter();
        var c = store.findBy(function (item, id) {
            // Only return the first record.
            if (!foundFirst) {
                var start = item.get('activate');
                var end = item.get('expire');
                var type = item.get('type');
                if ((type == contentType) && (start <= now) && (end === null || end >= now)) {
                    foundFirst = true;
                    return true;
                }
            }
            return false
        });
        return store.getAt(c);
    },

    onHomeActivate: function (container, newActiveItem, oldActiveItem, options) {
        // Reset the Home Container
        container.setActiveItem(container.getItems().items[0]);
    },

    onHomeCalculateButtonTap: function (button, e, options) {
        var panel = this.getCalculateInputPanel();

        // In case there is a quote from History around.
        flexi.quoteData = null;
        flexi.currentQuote = null;

        this.getHomePanel().push(panel);
    },

    onPromoTickerInitialize: function (component, options) {
        var store = component.getStore();
        var now = new Date().getTime();

        store.sort([
            {
                property: 'activate',
                direction: 'ASC'
            }
        ]);

        store.filterBy(function (item) {
            var start = item.get('activate');
            var end = item.get('expire');

            return (start <= now) && (end === null || end >= now);
        });
    }
});