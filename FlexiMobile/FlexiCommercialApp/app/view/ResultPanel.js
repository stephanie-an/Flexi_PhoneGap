Ext.define('FlexiCommercial.view.ResultPanel', {
    extend: 'Ext.Container',
    alias: 'widget.ResultPanel',

    requires: [
        'Ext.SegmentedButton',
        'Ext.form.FieldSet'
    ],

    config: {
        id: 'ResultPanel',
        itemId: 'ResultPanel',
        title: 'Quote Summary',
        autoDestroy: false,
        layout: {
            type: 'vbox'
        },
        scrollable: 'vertical',
        items: [
            {
                xtype: 'container',
                id: 'NavigationBar',
                itemId: 'NavigationBar',
                margin: '10 0 0 0',
                layout: {
                    pack: 'end',
                    type: 'hbox'
                },
                items: [
                    {
                        xtype: 'button',
                        itemId: 'SaveNavButton',
                        margin: '0 10 0 0',
                        minWidth: '90px',
                        text: 'Save'
                    },
                    {
                        xtype: 'button',
                        itemId: 'SubmitNavButton',
                        margin: '0 10 0 0',
                        minWidth: '90px',
                        text: 'Send'
                    }
                ]
            },
            {
                xtype: 'fieldset',
                id: 'CalculationResults',
                itemId: 'CalculationResults',
                margin: '0 10 10 10',
                styleHtmlContent: true,
                defaults: {
                    labelWidth: '60%',
                    clearIcon: false,
                    readOnly: true
                },
                title: '',
                items: [
                    {
                        xtype: 'textfield',
                        id: 'MonthlyResult',
                        itemId: 'MonthlyResult',
                        label: 'Monthly Pmt'
                    },
                    {
                        xtype: 'textfield',
                        id: 'MonthlyAfterTaxResult',
                        itemId: 'MonthlyAfterTaxResult',
                        label: 'Monthly Pmt After Tax',
                        required: true
                    },
                    {
                        xtype: 'textfield',
                        id: 'WeeklyResult',
                        itemId: 'WeeklyResult',
                        clearIcon: false,
                        label: 'Weekly Pmt'
                    },
                    {
                        xtype: 'textfield',
                        id: 'WeeklyAfterTaxResult',
                        itemId: 'WeeklyAfterTaxResult',
                        label: 'Weekly Pmt After Tax',
                        required: true
                    },
                    {
                        xtype: 'textfield',
                        id: 'ResidualResult',
                        itemId: 'ResidualResult',
                        label: 'Residual'
                    },
                    {
                        xtype: 'container',
                        html: '<small>*Tax deductions may apply. Deduction rules are liable to change. ' +
                            'FlexiCommercial does not provide legal, tax or accounting advice. ' +
                            'See your tax advisor for details. ' +
                            'Quoted lease amounts are an estimate only and include GST, based on potential tax deductions, ' +
                            'are paid monthly & are subject to change without notice. ' +
                            'Final rental payments will be supplied upon approval. ' +
                            'Please see product <a href="http://flexicommercial.com.au/termsconditions">Terms and Conditions</a> for full details.</small>',
                        id: 'TaxDisclaimerContainer',
                        itemId: 'TaxDisclaimerContainer',
                        padding: '0 15 0 15',
                        style: 'text-align: center;'
                    }
                ]
            },
            {
                xtype: 'fieldset',
                id: 'CustomerResults',
                itemId: 'CustomerResults',
                margin: '0 10 10 10',
                styleHtmlContent: true,
                defaults: {
                    labelWidth: '30%',
                    clearIcon: false,
                    readOnly: true
                },
                title: '',
                items: [
                    {
                        xtype: 'textfield',
                        id: 'CustomerNameResult',
                        itemId: 'CustomerNameResult',
                        label: 'Name'
                    },
                    {
                        xtype: 'textfield',
                        id: 'CustomerEmailResult',
                        itemId: 'CustomerEmailResult',
                        label: 'Email'
                    },
                    {
                        xtype: 'textfield',
                        id: 'CustomerPhoneResult',
                        itemId: 'CustomerPhoneResult',
                        clearIcon: false,
                        label: 'Phone',
                        readOnly: true
                    },
                    {
                        xtype: 'textareafield',
                        id: 'CustomerCommentResult',
                        itemId: 'CustomerCommentResult',
                        label: 'Comments'
                    },
                    {
                        xtype: 'textfield',
                        id: 'ProductResult',
                        itemId: 'ProductResult',
                        label: 'Product'
                    },
                    {
                        xtype: 'textfield',
                        id: 'TermResult',
                        itemId: 'TermResult',
                        label: 'Term'
                    },
                    {
                        xtype: 'textfield',
                        id: 'AmountResult',
                        itemId: 'AmountResult',
                        label: 'Amount'
                    },
                    {
                        xtype: 'textfield',
                        id: 'EquipmentResult',
                        itemId: 'EquipmentResult',
                        label: 'Equipment'
                    },
                    {
                        xtype: 'textfield',
                        id: 'TaxResult',
                        itemId: 'TaxResult',
                        label: 'Tax Bracket'
                    }
                ]
            },
            {
                xtype: 'fieldset',
                id: 'DealerResults',
                itemId: 'DealerResults',
                margin: '0 10 10 10',
                styleHtmlContent: true,
                defaults: {
                    labelWidth: '30%',
                    clearIcon: false,
                    readOnly: true
                },
                title: '',
                items: [
                    {
                        xtype: 'textfield',
                        id: 'StaffNameResult',
                        itemId: 'StaffNameResult',
                        label: 'Salesperson'
                    },
                    {
                        xtype: 'textfield',
                        id: 'StaffEmailResult',
                        itemId: 'StaffEmailResult',
                        label: 'Email'
                    },
                    {
                        xtype: 'textfield',
                        id: 'StaffPhoneResult',
                        itemId: 'StaffPhoneResult',
                        clearIcon: false,
                        label: 'Phone',
                        readOnly: true
                    },
                    {
                        xtype: 'textfield',
                        id: 'DealerResult',
                        itemId: 'DealerResult',
                        label: 'Dealer'
                    }
                ]
            }
        ]
    }

});