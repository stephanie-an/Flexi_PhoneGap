Ext.define('FlexiCommercial.view.Settings', {
    extend: 'Ext.Container',
    alias: 'widget.settings',

    requires: [
        'Ext.Label',
        'Ext.field.Hidden',
        'Ext.field.Select',
        'Ext.field.Number',
        'Ext.field.Email'
    ],

    config: {
        id: 'Settings',
        scrollable: 'vertical',
        items: [
            {
                xtype: 'toolbar',
                title: '<img class="logo" src="resources/images/logo-small.png"/>'
            },
            {
                xtype: 'panel',
                layout: {
                    pack: 'end',
                    type: 'hbox'
                },
                items: [
                    {
                        xtype: 'button',
                        id: 'NewUserButton',
                        tabIndex: 10,
                        itemId: 'NewUserButton',
                        margin: '10 18 0 0',
                        styleHtmlContent: true,
                        ui: 'round',
                        text: 'New User'
                    }
                ]
            },
            {
                xtype: 'panel',
                id: 'SettingsPanel',
                itemId: 'SettingsPanel',
                styleHtmlContent: true,
                layout: {
                    type: 'vbox'
                },
                items: [
                    {
                        xtype: 'container',
                        border: 1,
                        id: 'UserSettings',
                        itemId: 'UserSettings',
                        style: 'border-style: solid;',
                        defaults: {
                            labelWidth: '35%'
                        },
                        items: [
                            {
                                xtype: 'label',
                                html: 'User Details',
                                styleHtmlContent: true
                            },
                            {
                                xtype: 'textfield',
                                id: 'firstName',
                                tabIndex: 1,
                                label: 'First Name',
                                name: 'firstName',
                                required: true
                            },
                            {
                                xtype: 'textfield',
                                id: 'lastName',
                                tabIndex: 2,
                                label: 'Last Name',
                                name: 'lastName',
                                required: true
                            },
                            {
                                xtype: 'emailfield',
                                id: 'email',
                                tabIndex: 3,
                                label: 'Email',
                                name: 'email',
                                autoComplete: true,
                                placeHolder: 'email@example.com'
                            },
                            {
                                xtype: 'textfield',
                                id: 'phone',
                                tabIndex: 4,
                                label: 'Phone',
                                name: 'phone'
                            },
                            {
                                xtype: 'textfield',
                                id: 'dealer',
                                tabIndex: 5,
                                label: 'Dealer',
                                name: 'dealer',
                                required: true,
                                placeHolder: 'Sample Co'
                            }
                        ]
                    },
                    {
                        xtype: 'spacer',
                        styleHtmlContent: true
                    },
                    {
                        xtype: 'container',
                        border: 1,
                        id: 'DefaultSettings',
                        itemId: 'DefaultSettings',
                        style: 'border-style: solid;',
                        items: [
                            {
                                xtype: 'label',
                                html: 'Default Product',
                                styleHtmlContent: true
                            },
                            {
                                xtype: 'selectfield',
                                id: 'defaultProduct',
                                tabIndex: 6,
                                labelAlign: 'top',
                                name: 'defaultProduct',
                                displayField: 'name',
                                store: 'LocalProductStore',
                                valueField: 'id'
                            }
                        ]
                    },
                    {
                        xtype: 'spacer',
                        styleHtmlContent: true
                    },
                    {
                        xtype: 'container',
                        border: 1,
                        id: 'ApplicationSettings',
                        itemId: 'ApplicationSettings',
                        style: 'border-style: solid;',
                        defaults: {
                            labelWidth: '35%'
                        },
                        items: [
                            {
                                xtype: 'label',
                                html: 'Keep Quotes for:',
                                styleHtmlContent: true
                            },
                            {
                                xtype: 'selectfield',
                                id: 'savedRetention',
                                tabIndex: 7,
                                name: 'savedRetention',
                                label: 'Saved',
                                options: [
                                    {text: 'Forever', value: 0},
                                    {text: '2 Weeks', value: 2},
                                    {text: '4 Weeks', value: 4},
                                    {text: '3 Months', value: 13},
                                    {text: '1 Year', value: 52}
                                ],
                                value: 4
                            },
                            {
                                xtype: 'selectfield',
                                id: 'submitRetention',
                                tabIndex: 8,
                                name: 'submitRetention',
                                label: 'Sent',
                                options: [
                                    {text: 'Forever', value: 0},
                                    {text: '2 Weeks', value: 2},
                                    {text: '4 Weeks', value: 4},
                                    {text: '3 Months', value: 13},
                                    {text: '1 Year', value: 52}
                                ],
                                value: 4
                            }
                        ]
                    },
                    {
                        xtype: 'spacer',
                        height: 20,
                        styleHtmlContent: true
                    },
                    {
                        xtype: 'container',
                        layout: {
                            align: 'center',
                            type: 'vbox'
                        },
                        html: '<small><em>Flexi Commercial is brought to you by FlexiGroup.<br/>Flexirent Capital Pty Ltd holds Australian Credit Licence number 394735</em></small>'
                    },
                    {
                        xtype: 'spacer',
                        height: 20,
                        styleHtmlContent: true
                    },
                    {
                        xtype: 'container',
                        layout: {
                            align: 'center',
                            type: 'vbox'
                        },
                        items: [
                            {
                                xtype: 'button',
                                itemId: 'SubmitSettings',
                                tabIndex: 9,
                                styleHtmlContent: true,
                                ui: 'confirm-round',
                                width: '80%',
                                text: 'Save'
                            }
                        ]
                    }
                ]
            }
        ]
    }

});