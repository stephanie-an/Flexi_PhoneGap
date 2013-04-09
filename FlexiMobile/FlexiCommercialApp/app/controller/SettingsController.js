Ext.define('FlexiCommercial.controller.SettingsController', {
    extend: 'Ext.app.Controller',

    requires: [
        'Ext.data.identifier.Uuid',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        control: {
            "#SettingsPanel": {
                initialize: 'onSettingsPanelInitialize'
            },
            "#Settings": {
                show: 'onSettingsShow'
            },
            "button#SubmitSettings": {
                tap: 'onSubmitSettingsTap'
            },
            "button#NewUserButton": {
                tap: 'onNewUserButtonTap'
            }
        }
    },

    onSettingsPanelInitialize: function (component, options) {
        var _form = Ext.getCmp('SettingsPanel');
        var cUser = CommonUtils.getCurrentUser();
        var userSettings = _form.getComponent('UserSettings');
        var defSettings = _form.getComponent('DefaultSettings');
        var appSettings = _form.getComponent('ApplicationSettings');

        if (cUser !== null && cUser !== undefined) {
            // Initializing the Settings Form with Current User
            userSettings.getComponent('firstName').setValue(cUser.get('firstName'));
            userSettings.getComponent('lastName').setValue(cUser.get('lastName'));
            userSettings.getComponent('email').setValue(cUser.get('email'));
            userSettings.getComponent('phone').setValue(cUser.get('phone'));
            userSettings.getComponent('dealer').setValue(cUser.get('dealer'));
            defSettings.getComponent('defaultProduct').setValue(cUser.get('defaultProduct'));
            appSettings.getComponent('savedRetention').setValue(cUser.get('savedRetention'));
            appSettings.getComponent('submitRetention').setValue(cUser.get('submitRetention'));
        }
    },

    onSettingsShow: function (component, options) {
        // Delegate to the Panel Initialization routine.
        this.onSettingsPanelInitialize(component, options);

        // Scroll to the top...
        var scroller = component.getScrollable().getScroller();
        scroller.refresh();
        scroller.scrollTo(0, 0);
    },

    onSubmitSettingsTap: function (button, e, options) {
        var store = Ext.getStore(flexi.salesStaffStore);
        var _form = Ext.getCmp('SettingsPanel');
        var userSettings = _form.getComponent('UserSettings');
        var defSettings = _form.getComponent('DefaultSettings');
        var appSettings = _form.getComponent('ApplicationSettings');
        var homePage = Ext.ComponentManager.get('Home');
        var tabPanel = Ext.ComponentManager.get('TabPanel');
        var user = CommonUtils.getCurrentUser();
        var message = 'There were errors in the form';
        var newUser = false;

        e.stopPropagation();

        if (user === null || user === undefined) {
            newUser = true;
            user = Ext.create('FlexiCommercial.model.SalesStaff', {
                acceptTaC: false,
                isCurrent: true
            });
            store.add(user);
            store.sync();
        }

        user.beginEdit(); // Start transaction!

        // Validate that everything is good to go
        var firstNameInput = userSettings.getComponent('firstName');
        user.set('firstName', firstNameInput.getValue());
        var lastNameInput = userSettings.getComponent('lastName');
        user.set('lastName', lastNameInput.getValue());
        var emailInput = userSettings.getComponent('email');
        var email = emailInput.getValue();
        user.set('email', email);
        var phoneInput = userSettings.getComponent('phone');
        var phone = phoneInput.getValue();
        user.set('phone', phone);
        var dealerInput = userSettings.getComponent('dealer');
        user.set('dealer', dealerInput.getValue());
        user.set('defaultProduct', defSettings.getComponent('defaultProduct').getValue());
        user.set('savedRetention', appSettings.getComponent('savedRetention').getValue());
        user.set('submitRetention', appSettings.getComponent('submitRetention').getValue());

        var noErrors = true;

        var errors = user.validate();
        var error;
        if (!errors.isValid()) {
            // There were Validation Errors!!

            noErrors = false;
            error = errors.getByField('email');
            if (error.length > 0) {
                if (email.length > 0) {
                    // Only valid error if there is an email address.
                    emailInput.addCls('x-invalid');
                    emailInput.focus();
                    message += '<br/>' + error[0].getMessage();
                }
            } else {
                emailInput.removeCls('x-invalid');
            }

            error = errors.getByField('firstName');
            if (error.length > 0) {
                firstNameInput.addCls('x-invalid');
                firstNameInput.focus();
                message += '<br/>' + error[0].getMessage();
            } else {
                firstNameInput.removeCls('x-invalid');
            }

            error = errors.getByField('lastName');
            if (error.length > 0) {
                lastNameInput.addCls('x-invalid');
                lastNameInput.focus();
                message += '<br/>' + error[0].getMessage();
            } else {
                lastNameInput.removeCls('x-invalid');
            }

            error = errors.getByField('dealer');
            if (error.length > 0) {
                dealerInput.addCls('x-invalid');
                dealerInput.focus();
                message += '<br/>' + error[0].getMessage();
            } else {
                dealerInput.removeCls('x-invalid');
            }
        }

        if ((email === null || email.length == 0) &&
            (phone === null || phone.length == 0)) {
            emailInput.addCls('x-invalid');
            phoneInput.addCls('x-invalid');
            emailInput.focus();
            message += '<br/>No Contact details supplied';
            noErrors = false;
        } else {
            emailInput.removeCls('x-invalid');
            phoneInput.removeCls('x-invalid');
        }

        if (!noErrors) {
            Ext.Msg.alert('Error', message, Ext.emptyFn);
            noErrors = true;
        } else {
            var acceptedTaC = user.get('acceptTaC');

            if (acceptedTaC) {
                CommonUtils.enableAllPanels(tabPanel);
                tabPanel.setActiveItem(homePage);

                // TODO move this to a LocalSalesStaffStore onWrite listener.
                //
                // Upload the new user to the Server
                //

                var OnlineStore = Ext.getStore('SalesStaffStore');
                OnlineStore.add(user);
                user.setDirty();
                store.sync();
                OnlineStore.sync();
            } else {
                if (!this.tacpopup) {
                    var me = this;

                    this.tacpopup = Ext.Viewport.add({
                        xtype: 'panel',
                        top: 50,
                        centered: true,
                        id: 'TermsPanel',
                        itemId: 'TermsPanel',
                        minHeight: '200px',
                        minWidth: '300px',
                        styleHtmlContent: true,
                        hideOnMaskTap: false,
                        layout: {
                            align: 'center',
                            pack: 'top',
                            type: 'vbox'
                        },
                        modal: true,
                        items: [
                            {
                                xtype: 'container',
                                html: 'I agree to the Flexitools Calculator Terms and Conditions. In the event of a discrepancy between this Application and any Official FlexiGroup Quote, the latter shall prevail.',
                                margin: '0 0 10 0'
                            },
                            {
                                xtype: 'container',
                                layout: {
                                    type: 'hbox'
                                },
                                items: [
                                    {
                                        xtype: 'button',
                                        handler: function (button, event) {
                                            // User accepted TaC return home after saving
                                            user.set('acceptTaC', true);
                                            acceptedTaC = true;
                                            me.tacpopup.hide();
                                            CommonUtils.enableAllPanels(tabPanel);
                                            tabPanel.setActiveItem(homePage);

                                            // TODO move this to a LocalSalesStaffStore onWrite listener.
                                            //
                                            // Upload the new user to the Server
                                            //

                                            var OnlineStore = Ext.getStore('SalesStaffStore');
                                            OnlineStore.add(user);
                                            user.setDirty();
                                            store.sync();
                                            OnlineStore.sync();
                                        },
                                        margin: '10 10 10 10',
                                        text: 'Accept'
                                    },
                                    {
                                        xtype: 'button',
                                        handler: function (button, event) {
                                            // User did not accept TaC don't go anywhere
                                            me.tacpopup.hide();
                                        },
                                        margin: '10 10 10 10',
                                        text: 'Reject'
                                    }
                                ]
                            },
                            {
                                xtype: 'container',
                                html: '<small>Flexitools Calculator is a tool for calculating the payments on various FlexiGroup products. See <a href="http://www.flexirent.com">www.flexirent.com</a> for more information or call 1300 857 213.</small>',
                                margin: '10 0 0 0'
                            }
                        ]
                    });
                }
                this.tacpopup.show();
            }
        }
    },

    onNewUserButtonTap: function (button, e, options) {
        var _form = Ext.getCmp('SettingsPanel');
        var tabPanel = Ext.ComponentManager.get('TabPanel');

        if (!this.newuserpopup) {
            var me = this;

            this.newuserpopup = Ext.Viewport.add({
                xtype: 'panel',
                centered: true,
                minHeight: '200px',
                minWidth: '180px',
                styleHtmlContent: true,
                hideOnMaskTap: false,
                layout: {
                    align: 'center',
                    pack: 'center',
                    type: 'vbox'
                },
                modal: true,
                items: [
                    {
                        xtype: 'container',
                        html: 'Creating a new user will delete all Saved and Submitted quotes from this device.<br/>' +
                            'Are you sure you wish to proceed?',
                        margin: '0 0 10 0'
                    },
                    {
                        xtype: 'container',
                        layout: {
                            type: 'hbox'
                        },
                        items: [
                            {
                                xtype: 'button',
                                handler: function (button, event) {
                                    me.clearUserFromDevice(_form);
                                    CommonUtils.disableAllPanels(tabPanel);
                                    me.newuserpopup.hide();
                                },
                                margin: '10 10 10 10',
                                text: 'Accept'
                            },
                            {
                                xtype: 'button',
                                handler: function (button, event) {
                                    // User canceled
                                    me.newuserpopup.hide();
                                },
                                margin: '10 10 10 10',
                                text: 'Cancel'
                            }
                        ]
                    }
                ]
            });
        }
        this.newuserpopup.show();
    },

    clearUserFromDevice: function (_form) {
        var cUser = CommonUtils.getCurrentUser();
        var store = Ext.getStore(flexi.salesStaffStore);
        var userSettings = _form.getComponent('UserSettings');
        var defSettings = _form.getComponent('DefaultSettings');
        var appSettings = _form.getComponent('ApplicationSettings');

        // Remove the Old User
        if (cUser !== null && cUser !== undefined) {
            // We have a current user, remove them.
            cUser.set('isCurrent', false);
            cUser.setDirty();
            store.sync();
            store.remove(cUser);
        }
        // Remove any other old users (should never happen).
        store.each(function (record) {
            store.remove(record);
        });
        store.sync();

        // Remove old quotes.
        var qStore = Ext.getStore(flexi.quoteStore);

        qStore.clearFilter();

        qStore.each(function (record) {
            if (record.get('status') !== 'PENDING') {
                qStore.remove(record);
            }
        });
        qStore.sync();

        userSettings.getComponent('firstName').setValue('');
        userSettings.getComponent('lastName').setValue('');
        userSettings.getComponent('email').setValue('');
        userSettings.getComponent('phone').setValue('');
        userSettings.getComponent('dealer').setValue('');
        defSettings.getComponent('defaultProduct').setValue(0);
        appSettings.getComponent('savedRetention').setValue(4);
        appSettings.getComponent('submitRetention').setValue(4);
    }

});