Ext.define('FlexiCommercial.controller.ResultController', {
    extend: 'Ext.app.Controller',
    alias: 'controller.ResultController',

    alternateClassName: [
        'ResultController'
    ],
    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.store.LocalQuoteStore',
        'FlexiCommercial.store.QuoteStore'
    ],

    config: {
        views: [
            'ResultPanel'
        ],

        refs: {
            resultPanel: 'ResultPanel',
            calculateInputPanel: {
                selector: 'CalculateInputPanel',
                xtype: 'CalculateInputPanel',
                autoCreate: true
            },
            saveButton: 'ResultPanel button[itemId=SaveNavButton]',
            submitButton: 'ResultPanel button[itemId=SubmitNavButton]'
        },

        control: {
            saveButton: {
                tap: 'onNavigateSaveButtonTap'
            },
            submitButton: {
                tap: 'onNavigateSubmitButtonTap'
            }
        }
    },

    onNavigateSaveButtonTap: function (button, e, options) {
        var resultsPanel = this.getResultPanel();
        var homePanel = resultsPanel.getParent();
        var returnPanel;

        if (homePanel.getId() == 'Home') {
            this.saveCurrentQuote(true);
            homePanel.pop(2);
        } else if (homePanel.getId() == 'SavedHistory') {
            var btnFunction = button.getText();
            if (btnFunction === 'Save') {
                this.saveCurrentQuote(false);
                homePanel.pop(2);
            } else {
                // Editing from Saved History
                returnPanel = homePanel.getComponent('CalculateInputPanel');

                if (returnPanel === null || returnPanel === undefined) {
                    returnPanel = Ext.create('FlexiCommercial.view.CalculateInputPanel', { mode: 'edit' });
                }
                returnPanel.mode = 'edit';
                homePanel.push(returnPanel);
            }
        } else {
            // This option is not available for Submitted or Pending.
            console.error('Trying to Edit a Submitted or Pending Quote from ' + homePanel.getId());
        }
    },

    onNavigateSubmitButtonTap: function (button, e, options) {
        var resultsPanel, homePanel, isNewQuote;

        resultsPanel = this.getResultPanel();
        homePanel = resultsPanel.getParent();
        isNewQuote = (homePanel.getId() === 'Home');

        this.submitCurrentQuote(isNewQuote);
        Ext.Msg.show({
            message: 'Thank you. Your quote has been submitted to FlexiCommercial.',
            width: 300,
            buttons: Ext.MessageBox.OK
        });
        homePanel.pop(2);
    },

    handleResultsShow: function (parent, component, quote) {
        // TODO replace this mess with the quote object form setData mapping?
        //var btnPanel = component.getComponent('ResultsNavigationPanel');
        var calcResult = component.getComponent('CalculationResults');
        var custResult = component.getComponent('CustomerResults');
        var dealResult = component.getComponent('DealerResults');

        calcResult.getComponent('MonthlyResult').setValue("$" + quote.get('monthlypayment') + ".00");
        calcResult.getComponent('MonthlyAfterTaxResult').setValue("$" + quote.get('monthlypaymentaftertax') + ".00");
        calcResult.getComponent('WeeklyResult').setValue("$" + quote.get('weeklypayment') + ".00");
        calcResult.getComponent('WeeklyAfterTaxResult').setValue("$" + quote.get('weeklypaymentaftertax') + ".00");

        var residual = quote.get('residual');
        if (residual !== null && residual !== undefined) {
            calcResult.getComponent('ResidualResult').setValue("$" + residual + ".00");
        } else {
            calcResult.getComponent('ResidualResult').setHidden(true);
        }

        var product = quote.getProduct();
        var equipment = quote.getEquipment();
        var tax = quote.getTax();

        custResult.getComponent('CustomerNameResult').setValue(quote.get('name'));
        custResult.getComponent('CustomerEmailResult').setValue(quote.get('email'));
        custResult.getComponent('CustomerPhoneResult').setValue(quote.get('phone'));
        var comment = quote.get('comment')
        var cmtComponent = custResult.getComponent('CustomerCommentResult');
        cmtComponent.setValue(comment);
        var lines = comment.split('\n').length;
        if (lines > 5) {
            cmtComponent.setMaxRows(lines);
        } else {
            cmtComponent.setMaxRows(5);
        }
        custResult.getComponent('ProductResult').setValue(product.get('name'));
        custResult.getComponent('TermResult').setValue(quote.get('term'));
        custResult.getComponent('AmountResult').setValue("$" + quote.get('amount'));
        custResult.getComponent('EquipmentResult').setValue(equipment.get('name'));
        custResult.getComponent('TaxResult').setValue(tax.get('description'));

        var staff = quote.getSalesStaff();

        dealResult.getComponent('StaffNameResult').setValue(staff.get('firstName') + ' ' + staff.get('lastName'));
        dealResult.getComponent('StaffEmailResult').setValue(staff.get('email'));
        dealResult.getComponent('StaffPhoneResult').setValue(staff.get('phone'));
        dealResult.getComponent('DealerResult').setValue(staff.get('dealer'));
    },

    saveCurrentQuote: function (isNewQuote) {
        var qStore, quote, cust;
        var cDate = new Date();

        qStore = Ext.getStore(flexi.quoteStore);
        quote = flexi.quoteData;

        quote.set('status', 'SAVED');
        quote.set('lastdate', cDate);

        if (isNewQuote) {
            quote.set('createdate', cDate);
            qStore.add(quote);
        } else {
            quote.endEdit();
        }
        quote.setDirty();
        qStore.sync();

        // Reset the Current Quote if we Save it.
        flexi.currentQuote = null;
        flexi.quoteData = null;

        Ext.Msg.show({
            message: 'Your quote has been saved.',
            width: 300,
            buttons: Ext.MessageBox.OK
        });
    },

    submitCurrentQuote: function (isNewQuote) {
        var store, cStore, quote, cust;
        var cDate = new Date();

        store = Ext.getStore(flexi.quoteStore);
        quote = flexi.quoteData

        quote.set('status', 'PENDING');
        quote.set('lastdate', cDate);

        if (isNewQuote) {
            quote.set('createdate', cDate);
            store.add(quote);
        } else {
            // Commit any changes
            quote.endEdit();
        }
        quote.setDirty();
        //store.sync();

        // This is specifically to push submitted quotes to the Server. Do not use the
        // global flexi store name.
        var OnlineStore = Ext.getStore('QuoteStore');
        var result = OnlineStore.add(quote);
        result[0].setDirty();
        OnlineStore.sync();

        flexi.currentQuote = null;
        flexi.quoteData = null;
    }

});