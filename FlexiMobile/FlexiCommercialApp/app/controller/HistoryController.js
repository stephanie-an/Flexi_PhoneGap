Ext.define('FlexiCommercial.controller.HistoryController', {
    extend: 'Ext.app.Controller',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.store.LocalQuoteStore',
        'FlexiCommercial.store.LocalProductStore',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        views: [
            'HistoryPanel'
        ],

        refs: {
            savedHistoryPanel: '#SavedHistory',
            sentHistoryPanel: '#SentHistory',
            pendingHistoryPanel: '#PendingHistory',
            historyList: '[itemId=HistoryList]',
            resultPanel: {
                selector: 'ResultPanel',
                xtype: 'ResultPanel',
                autoCreate: true
            },
            saveButton: 'ResultPanel button[itemId=SaveNavButton]',
            submitButton: 'ResultPanel button[itemId=SubmitNavButton]',
            retryAllButton: '#PendingHistory [itemId=RetryAllButton]'
        },

        control: {
            historyList: {
                itemtap: 'onHistoryItemTap'
            },
            savedHistoryPanel: {
                activate: 'onSavedHistoryPanelActivate'
            },
            sentHistoryPanel: {
                activate: 'onSentHistoryPanelActivate'
            },
            pendingHistoryPanel: {
                activate: 'onPendingHistoryPanelActivate'
            },
            retryAllButton: {
                tap: 'onRetryAllButtonTap'
            }
        }
    },

    onHistoryItemTap: function (list, index, target, record, e, options) {
        var histDisplay, histPanel, histResult,
            status, saveBtn, submitBtn,
            _resultController;

        histDisplay = list.getParent();
        histPanel = histDisplay.getParent();
        histResult = this.getResultPanel();

        flexi.currentQuote = record.getId();
        flexi.quoteData = record;

        _resultController = this.getApplication().getController('ResultController');
        _resultController.handleResultsShow(histPanel, histResult, record);

        saveBtn = this.getSaveButton();
        submitBtn = this.getSubmitButton();

        status = record.get('status');
        if (status == 'SAVED') {
            // Retask Save to Edit.
            saveBtn.setText('Edit');
            saveBtn.enable();
            saveBtn.setHidden(false);

            // Reset Submit so it's not Retry.
            submitBtn.setText('Send');
            submitBtn.enable();
            submitBtn.setHidden(false);
        } else if (status == 'SUBMITTED') {
            // Disable Save and Submit
            saveBtn.disable();
            saveBtn.setHidden(true);

            submitBtn.disable();
            submitBtn.setHidden(true);
        } else {
            // Disable Save and retask Submit to Retry.
            saveBtn.disable();
            saveBtn.setHidden(true);

            submitBtn.setText('Retry');
            submitBtn.enable();
            submitBtn.setHidden(false);
        }

        histPanel.push(histResult);
    },

    onSavedHistoryPanelActivate: function (container, newActiveItem, oldActiveItem, options) {
        var store = container.getComponent('HistoryDisplay').getComponent('HistoryList').getStore();

        if (store !== null && store !== undefined) {
           store.clearFilter();
            store.filter([
                {
                    property: 'salesstaff_id',
                    value: flexi.currentUser
                },
                {
                    property: 'status',
                    value: 'SAVED'
                }
            ]);

            store.sort({
                property: 'lastdate',
                direction: 'DESC'
            });
        } else {
            console.error('There was no store!!!');
        }
        // Reset the Container
        container.setActiveItem(container.getItems().items[0]);
    },

    onSentHistoryPanelActivate: function (container, newActiveItem, oldActiveItem, options) {
        var store = container.getComponent('HistoryDisplay').getComponent('HistoryList').getStore();

        if (store !== null && store !== undefined) {
            store.clearFilter();
            store.filter([
                {
                    property: 'salesstaff_id',
                    value: flexi.currentUser
                },
                {
                    property: 'status',
                    value: 'SUBMITTED'
                }
            ]);

            store.sort({
                property: 'lastdate',
                direction: 'DESC'
            });
        } else {
            console.error('There was no store!!!');
        }
        // Reset the Container
        container.setActiveItem(container.getItems().items[0]);
    },

    onPendingHistoryPanelActivate: function (container, newActiveItem, oldActiveItem, options) {
        var store = container.getComponent('HistoryDisplay').getComponent('HistoryList').getStore();

        if (store !== null && store !== undefined) {
            store.clearFilter();
            store.filter([
                {
                    property: 'salesstaff_id',
                    value: flexi.currentUser
                },
                {
                    property: 'status',
                    value: 'PENDING'
                }
            ]);

            store.sort({
                property: 'lastdate',
                direction: 'DESC'
            });
        } else {
            console.error('There was no store!!!');
        }

        // Reset the Container
        container.setActiveItem(container.getItems().items[0]);
    },

    onRetryAllButtonTap: function (button) {
        var store = Ext.getStore(flexi.quoteStore);

        CommonUtils.retryPendingQuotes(store);

        store.clearFilter();
        store.filter([
            {
                property: 'salesstaff_id',
                value: flexi.currentUser
            },
            {
                property: 'status',
                value: 'PENDING'
            }
        ]);

        store.sort({
            property: 'lastdate',
            direction: 'DESC'
        });
    }

});