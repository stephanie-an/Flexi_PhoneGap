Ext.define('FlexiCommercial.controller.AppController', {
    extend: 'Ext.app.Controller',

    requires: [
        'Ext.data.identifier.Uuid',
        'Ext.data.proxy.LocalStorage',
        'Ext.data.proxy.Rest',
        'Ext.data.proxy.JsonP',
        'Ext.MessageBox',
        'FlexiCommercial.model.SalesStaff',
        'FlexiCommercial.model.Equipment',
        'FlexiCommercial.model.Product',
        'FlexiCommercial.model.Tax',
        'FlexiCommercial.model.Rates',
        'FlexiCommercial.store.LocalSalesStaffStore',
        'FlexiCommercial.store.LocalEquipmentStore',
        'FlexiCommercial.store.LocalProductStore',
        'FlexiCommercial.store.LocalTaxStore',
        'FlexiCommercial.store.LocalRatesStore',
        'FlexiCommercial.store.LocalContentStore',
        'FlexiCommercial.store.LocalQuoteStore',
        'FlexiCommercial.common.UpgradeUtils',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        views: [
            'TabPanel'
        ],

        refs: {
            tabPanel: '#TabPanel',
            settingsTab: '#Settings'
        },

        control: {
            tabPanel: {
                activeitemchange: 'onTabPanelActiveItemChange'
            }
        }
    },

    onTabPanelActiveItemChange: function (tabPanel, newActiveItem, oldActiveItem, eOpts) {
        var nItems;

        // If old active item is a navigation panel then unwind any previous navigation
        if (oldActiveItem instanceof Ext.navigation.View) {
            nItems = oldActiveItem.getInnerItems().length;
            if (nItems > 1) {
                // Pop all items except the first to return to base view state
                oldActiveItem.pop(nItems - 1);
            }
        }
    },

    launch: function () {
        var accepted, tabPanel, settingsTab, cUser, savedCleanup, submitCleanup;

        //
        // Find the Current Active User or go straight to Settings if none found.
        //

        tabPanel = this.getTabPanel();
        settingsTab = this.getSettingsTab();
        cUser = CommonUtils.getCurrentUser();

        if (cUser === null || cUser === undefined) {
            // There was no current user, so get it first
            CommonUtils.disableAllPanels(tabPanel);
            tabPanel.setActiveItem(settingsTab);
        } else {
            // We have a current user...
            if (cUser.get('email') === null) {
                // User hasn't saved anything, so treat it like it doesn't exist
                CommonUtils.disableAllPanels(tabPanel);
                tabPanel.setActiveItem(settingsTab);
            } else {
                // We have a real current user!
                accepted = cUser.get('acceptTaC');
                if (!accepted) {
                    // Hasn't Accepted, go to settings!
                    CommonUtils.disableAllPanels(tabPanel);
                    tabPanel.setActiveItem(settingsTab);
                } else {
                    savedCleanup = cUser.get('savedRetention');
                    submitCleanup = cUser.get('submitRetention');
                    CommonUtils.removeOldQuotes('SAVED', savedCleanup);
                    CommonUtils.removeOldQuotes('SUBMITTED', submitCleanup);
                }
            }
        }
    },

    init: function (application) {
        UpgradeUtils.updateApplication();

        // Define a global to hold everything we need to reference regularly.
        flexi = {
            contentStore: 'LocalContentStore',
            dealerStore: 'LocalDealerStore',
            equipmentStore: 'LocalEquipmentStore',
            productStore: 'LocalProductStore',
            salesStaffStore: 'LocalSalesStaffStore',
            ratesStore: 'LocalRatesStore',
            taxStore: 'LocalTaxStore',
            quoteStore: 'LocalQuoteStore'
        };

        // Clear up anything that should be removed.
        var localContentStore = Ext.getStore('LocalContentStore');
        var localTickerStore = Ext.getStore('LocalTickerStore');
        var localQuoteStore = Ext.getStore('LocalQuoteStore');

        CommonUtils.removeExpiredItems(localContentStore);
        CommonUtils.removeExpiredItems(localTickerStore);
        CommonUtils.purgeNewQuotes(localQuoteStore);

        // Retry anything Pending to flush them out if possible.
        CommonUtils.retryPendingQuotes(localQuoteStore);

        CommonUtils.loadOnlineStores();
    }
});