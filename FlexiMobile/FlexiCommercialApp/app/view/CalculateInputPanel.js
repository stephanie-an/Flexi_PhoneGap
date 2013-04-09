Ext.define('FlexiCommercial.view.CalculateInputPanel', {
    extend: 'Ext.Container',
    alias: 'widget.CalculateInputPanel',

    config: {
        id: 'CalculateInputPanel',
        itemId: 'CalculateInputPanel',
        title: 'Quote Details',
        styleHtmlContent: true,
        layout: {
            type: 'vbox'
        },
        scrollable: 'vertical',
        items: [
            {
                xtype: 'fieldset',
                id: 'FinanceDetails',
                itemId: 'FinanceDetails',
                styleHtmlContent: true,
                title: 'Finance Details',
                items: [
                    {
                        xtype: 'selectfield',
                        id: 'ProductInput',
                        tabIndex: 1,
                        itemId: 'ProductInput',
                        label: 'Product',
                        required: true,
                        displayField: 'name',
                        store: 'LocalProductStore',
                        valueField: 'id'
                    },
                    {
                        xtype: 'selectfield',
                        id: 'EquipmentInput',
                        tabIndex: 2,
                        itemId: 'EquipmentInput',
                        label: 'Asset Type',
                        required: true,
                        displayField: 'name',
                        store: 'LocalEquipmentStore',
                        valueField: 'id'
                    },
                    {
                        xtype: 'numberfield',
                        id: 'AmountInput',
                        tabIndex: 3,
                        itemId: 'AmountInput',
                        label: 'Amount',
                        required: true,
                        maxLength: 6,
                        maxValue: 100000,
                        minValue: 0
                    },
                    {
                        xtype: 'selectfield',
                        id: 'TermInput',
                        tabIndex: 4,
                        itemId: 'TermInput',
                        label: 'Term',
                        required: true,
                        autoCapitalize: true,
                        options: [
                            {
                                text: '12 Months',
                                value: 12
                            },
                            {
                                text: '24 Months',
                                value: 24
                            },
                            {
                                text: '36 Months',
                                value: 36
                            },
                            {
                                text: '48 Months',
                                value: 48
                            },
                            {
                                text: '60 Months',
                                value: 60
                            }
                        ]
                    },
                    {
                        xtype: 'selectfield',
                        id: 'TaxInput',
                        tabIndex: 5,
                        itemId: 'TaxInput',
                        label: 'Tax Bracket',
                        required: true,
                        displayField: 'description',
                        store: 'LocalTaxStore',
                        valueField: 'id'
                    }
                ]
            },
            {
                xtype: 'fieldset',
                id: 'CustomerDetails',
                itemId: 'CustomerDetails',
                styleHtmlContent: true,
                title: 'Customer Details',
                items: [
                    {
                        xtype: 'textfield',
                        id: 'NameInput',
                        tabIndex: 6,
                        itemId: 'NameInput',
                        label: 'Name',
                        required: true,
                        maxLength: 255
                    },
                    {
                        xtype: 'emailfield',
                        id: 'EmailInput',
                        tabIndex: 7,
                        itemId: 'EmailInput',
                        clearIcon: false,
                        label: 'Email',
                        placeHolder: 'email@example.com'
                    },
                    {
                        xtype: 'textfield',
                        id: 'PhoneInput',
                        tabIndex: 8,
                        itemId: 'PhoneInput',
                        clearIcon: false,
                        label: 'Phone'
                    },
                    {
                        xtype: 'textareafield',
                        id: 'CommentsInput',
                        tabIndex: 9,
                        itemId: 'CommentsInput',
                        label: 'Comments',
                        labelAlign: 'top',
                        autoComplete: true,
                        autoCorrect: true,
                        placeHolder: '(i.e. best day and time to contact)',
                        maxLength: 65535,
                        maxRows: 5,
                        listeners: {
                            keyup: function(fld, e){
                                if (e.browserEvent.keyCode == 13) {
                                    // Resize the Input Box as return char(13) is hit.
                                    var maxRows = this.getMaxRows();
                                    var lines = this.getValue().split('\n').length;
                                    if (lines >= maxRows) {
                                        var height = (lines+2) * 17;
                                        this.setHeight(height + 'px');
                                    }
                                }
                            }
                        }
                    }
                ]
            },
            {
                xtype: 'container',
                border: 0,
                padding: '10 10 10 10',
                styleHtmlContent: true,
                layout: {
                    align: 'center',
                    type: 'vbox'
                },
                items: [
                    {
                        xtype: 'button',
                        height: 26,
                        itemId: 'CalculateButton',
                        tabIndex: 10,
                        styleHtmlContent: true,
                        ui: 'confirm-round',
                        width: '80%',
                        badgeText: '',
                        text: 'Calculate',
                        listeners: {
                            release: function(fld, e){
                                // Hack around the iPhone keyboard display popping back up!
                                if (Ext.os.is.iOS) {
                                    var commentsInput = fld.getParent().getParent().getComponent('CustomerDetails').getComponent('CommentsInput');
                                    commentsInput.setReadOnly(true);
                                    commentsInput.disable();
                                    commentsInput.blur();
                                    Ext.defer(function() {
                                        if (commentsInput !== null && commentsInput !== undefined) {
                                            commentsInput.blur();
                                            commentsInput.setReadOnly(false);
                                            commentsInput.enable();
                                        }
                                    }, 500);
                                }
                            }
                        }
                    }
                ]
            }
        ]
    }

});