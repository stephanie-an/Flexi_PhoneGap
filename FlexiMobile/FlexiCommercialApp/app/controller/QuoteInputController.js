Ext.define('FlexiCommercial.controller.QuoteInputController', {
    extend: 'Ext.app.Controller',

    requires: [
        'Ext.data.identifier.Uuid',
        'Ext.MessageBox',
        'FlexiCommercial.model.Quote',
        'FlexiCommercial.store.LocalQuoteStore',
        'FlexiCommercial.store.QuoteStore',
        'FlexiCommercial.common.Utils'
    ],

    config: {
        refs: {
            calculateInputPanel: 'CalculateInputPanel',
            productInput: 'CalculateInputPanel [itemId=ProductInput]',
            termInput: 'CalculateInputPanel [itemId=TermInput]',
            amountInput: 'CalculateInputPanel [itemId=AmountInput]',
            calculateButton: 'CalculateInputPanel [itemId=CalculateButton]'
        },
        control: {
            calculateInputPanel: {
                show: 'onCalculateInputPanelShow'
            },
            "#Calculate": {
                activate: 'onCalculateActivate'
            },
            productInput: {
                change: 'onProductInputChange'
            },
            termInput: {
                change: 'onTermInputChange'
            },
            amountInput: {
                change: 'onAmountInputChange'
            },
            calculateButton: {
                tap: 'onCalculateButtonTap'
            }
        }
    },

    onCalculateInputPanelShow: function (component, options) {
        if (flexi.currentQuote === null || flexi.currentQuote === undefined) {
            this.resetCalculateFields(component);
        } else {
            this.populateCalculateFields(component, flexi.quoteData);
        }

        // Scroll back to the top...
        var scroller = component.getScrollable().getScroller();
        scroller.refresh();
        scroller.scrollTo(0, 0);
    },

    onCalculateActivate: function (container, newActiveItem, oldActiveItem, options) {
        var calcInput = container.getComponent('CalcInput');
        if (flexi.currentQuote === null || flexi.currentQuote === undefined) {
            this.resetCalculateFields(calcInput);
        } else {
            this.populateCalculateFields(calcInput, flexi.quoteData);
        }

        // Scroll back to the top
        var scroller = calcInput.getScrollable().getScroller();
        scroller.refresh();
        scroller.scrollTo(0, 0);
    },

    onProductInputChange: function (selectfield, newValue, oldValue, options) {
        var _parent = selectfield.getParent();
        var termInput = _parent.child('#TermInput');

        termInput.setOptions(this.getValidTerms(_parent));
        this.validateAmountField(_parent);
    },

    onTermInputChange: function (selectfield, newValue, oldValue, options) {
        var _parent = selectfield.getParent();
        this.validateAmountField(_parent);
    },

    onAmountInputChange: function (textfield, newValue, oldValue, options) {
        var _parent = textfield.getParent();
        var termInput = _parent.child('#TermInput');

        termInput.setOptions(this.getValidTerms(_parent));
        this.validateAmountField(_parent);
    },

    /*************************************************************************
     * Here lies all the calculation logic.                                  *
     *************************************************************************/

    onCalculateButtonTap: function (button, e, options) {
        var _resultController = this.getApplication().getController('ResultController');
        var calcInput = this.getCalculateInputPanel();
        var home = calcInput.getParent();
        var calcResult = Ext.getCmp('ResultPanel');
        var fDetails = calcInput.getComponent('FinanceDetails');
        var cDetails = calcInput.getComponent('CustomerDetails');
        var cUser = CommonUtils.getCurrentUser();
        var message = 'There were errors in the form';
        var monthlypayment;
        var monthlypaymentaftertax;
        var weeklypayment;
        var weeklypaymentaftertax;
        var residual;
        var noErrors = true;
        var quote;

        // ****************************************************************************
        // Find the selected product details.

        var equip = CommonUtils.getEquipmentById(fDetails.getComponent('EquipmentInput').getValue());
        var prod = CommonUtils.getProductById(fDetails.getComponent('ProductInput').getValue());
        var tax = CommonUtils.getTaxById(fDetails.getComponent('TaxInput').getValue());

        // ****************************************************************************
        // Do the calculations.

        var amountInput = fDetails.getComponent('AmountInput');
        var term = fDetails.getComponent('TermInput').getValue();
        var amount = amountInput.getValue();

        var _rate = CommonUtils.getRateByFilter(prod, term, amount);
        var rate = 0;
        if (_rate !== undefined) {
            rate = _rate.get('rate');
        }
        var _rRate = CommonUtils.getRateByFilter(prod, term, 0);
        var rRate = 0;
        if (_rRate !== undefined) {
            rRate = _rRate.get('rate');
        }

        var rawMthPay = amount * rate / 100;
        monthlypayment = Math.ceil(rawMthPay);
        weeklypayment = Math.ceil(rawMthPay * 12 / 52);

        var tRate = tax.get('rate');
        var mFactor = tax.get('multiplyFactor');

        monthlypaymentaftertax = Math.ceil(rawMthPay * mFactor);
        weeklypaymentaftertax = Math.ceil(rawMthPay * 12 / 52 * mFactor);

        if (rRate > 0) {
            residual = Math.ceil(amount * rRate / 100);
        }

        // ****************************************************************************
        // Get the Customer Details.

        var nameInput = cDetails.getComponent('NameInput');
        var emailInput = cDetails.getComponent('EmailInput');
        var phoneInput = cDetails.getComponent('PhoneInput');
        var commentInput = cDetails.getComponent('CommentsInput');
        var name = nameInput.getValue();
        var email = emailInput.getValue();
        var phone = phoneInput.getValue();
        var comment = commentInput.getValue();

        // ****************************************************************************
        // Create the Local Quote.

        if (flexi.quoteData === null || flexi.quoteData === undefined) {
            // This is a brand new quote!
           var uuid = new Ext.data.identifier.Uuid();
            quote = Ext.create('FlexiCommercial.model.Quote', {
                uuid: uuid.generate(),
                term: term,
                amount: amount,
                name: name,
                email: email,
                phone: phone,
                comment: comment,
                monthlypayment: monthlypayment,
                monthlypaymentaftertax: monthlypaymentaftertax,
                weeklypayment: weeklypayment,
                weeklypaymentaftertax: weeklypaymentaftertax,
                residual: residual,
                status: 'NEW'
            });
        } else {
            // We are editing an existing quote so grab a copy with the same Id!
            quote = flexi.quoteData;
            quote.beginEdit(); // Start transaction!
            quote.set('term', term);
            quote.set('amount', amount);
            quote.set('name', name);
            quote.set('email', email);
            quote.set('phone', phone);
            quote.set('comment', comment);
            quote.set('monthlypayment', monthlypayment);
            quote.set('monthlypaymentaftertax', monthlypaymentaftertax);
            quote.set('weeklypayment', weeklypayment);
            quote.set('weeklypaymentaftertax', weeklypaymentaftertax);
            quote.set('residual', residual);
        }

        // ****************************************************************************
        // Link the associations up to the quote

        quote.setEquipment(equip.getId());
        quote.setProduct(prod.getId());
        quote.setSalesStaff(cUser.getId());
        quote.setTax(tax.getId());

        calcInput.setRecord(quote);

        // ****************************************************************************
        // Validate the Customer Fields.

        if (name === null || name.length == 0) {
            nameInput.addCls('x-invalid');
            nameInput.focus();
            message += '<br/>Customer Name is Mandetory';
            noErrors = false;
        } else {
            nameInput.removeCls('x-invalid');
        }

        if ((email === null || email.length == 0) &&
            (phone === null || phone.length == 0)) {
            emailInput.addCls('x-invalid');
            phoneInput.addCls('x-invalid');
            emailInput.focus();
            message += '<br/>No Customer Contact details supplied';
            noErrors = false;
        } else {
            if (email !== null && email.length > 0) {
                var emailValidation = /^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/;
                if (!emailValidation.test(email)) {
                    emailInput.addCls('x-invalid');
                    emailInput.focus();
                    message += '<br/>Email Address is invalid';
                    noErrors = false;
                } else {
                    emailInput.removeCls('x-invalid');
                }
            }
            phoneInput.removeCls('x-invalid');
        }

        // ****************************************************************************
        // Validate the Quote Amount

        var minMax = this.getMinAndMaxAmounts(prod.get('id'), term);
        var min = minMax.min;
        var max = minMax.max;
        if ((amount === null) || (amount < min) || (amount > max)) {
            amountInput.addCls('x-invalid');
            amountInput.focus();
            message += '<br/>Amount must be between $' + min + ' and $' + max;
            noErrors = false;
        } else {
            amountInput.removeCls('x-invalid');
        }

        if (noErrors) {
            // ****************************************************************************
            // Display the Results Panel, passing this quote to the 'show' event handler.

            flexi.currentQuote = quote.getId();
            flexi.quoteData = quote;

            if (calcResult === null || calcResult === undefined) {
                calcResult = Ext.create('FlexiCommercial.view.ResultPanel');
            }
            _resultController.handleResultsShow(home, calcResult, quote);
            var resultsNav = calcResult.getComponent('NavigationBar');
            var saveBtn = resultsNav.getComponent('SaveNavButton');
            var submitBtn = resultsNav.getComponent('SubmitNavButton');

            // Reset the dynamic button label.
            saveBtn.setText('Save');
            saveBtn.enable();
            saveBtn.setHidden(false);

            submitBtn.setText('Send');
            submitBtn.enable();
            submitBtn.setHidden(false);

            if (calcInput.mode == 'edit') {
                // We are editting a saved quote. pop back to result rather than push.
                home.pop();
            } else {
                home.push(calcResult);
            }
        } else {
            // ****************************************************************************
            // Display and error alert and stay where we are.
            Ext.Msg.alert('Error', message, Ext.emptyFn);
            noErrors = true;
        }
    },

    resetCalculateFields: function (inputPanel) {
        // Set the Default Product from the current user
        var cUser = CommonUtils.getCurrentUser();

        var fDetails = inputPanel.getComponent('FinanceDetails');
        var cDetails = inputPanel.getComponent('CustomerDetails');

        // Reset the Product Input to user default
        var productPicker = fDetails.getComponent('ProductInput');

        if (cUser !== null && cUser !== undefined) {
            var defProduct = cUser.get('defaultProduct');
            if (defProduct !== null && defProduct !== undefined) {
                productPicker.setValue(defProduct);
            } else {
                productPicker.setValue(0);
            }
        }

        // Reset the Form Fields
        fDetails.getComponent('EquipmentInput').reset();
        var termInput = fDetails.getComponent('TermInput');
        termInput.reset();
        var amtInput = fDetails.getComponent('AmountInput');
        amtInput.setMinValue(0);
        amtInput.setMaxValue(100000);
        amtInput.reset();
        fDetails.getComponent('TaxInput').reset();

        cDetails.getComponent('NameInput').reset();
        cDetails.getComponent('EmailInput').reset();
        cDetails.getComponent('PhoneInput').reset();
        cDetails.getComponent('CommentsInput').reset();
    },

    populateCalculateFields: function (inputPanel, quote) {
        var fDetails = inputPanel.getComponent('FinanceDetails');
        var cDetails = inputPanel.getComponent('CustomerDetails');
        var product = quote.getProduct();
        var equipment = quote.getEquipment();
        var tax = quote.getTax();

        // Reset the Product Input to user default
        var productPicker = fDetails.getComponent('ProductInput');
        productPicker.setValue(product.get('id'));

        // Reset the Form Fields
        fDetails.getComponent('EquipmentInput').setValue(equipment.get('id'));
        fDetails.getComponent('TermInput').setValue(quote.get('term'));
        fDetails.getComponent('AmountInput').setValue(quote.get('amount'));
        fDetails.getComponent('TaxInput').setValue(tax.get('id'));

        cDetails.getComponent('NameInput').setValue(quote.get('name'));
        cDetails.getComponent('EmailInput').setValue(quote.get('email'));
        cDetails.getComponent('PhoneInput').setValue(quote.get('phone'));
        var commentInput = cDetails.getComponent('CommentsInput');
        var comment = quote.get('comment');
        var lines = comment.split('\n').length;
        if (lines > 5) {
            commentInput.setMaxRows(lines);
        }
        commentInput.setValue(comment);
    },

    validateAmountField: function (parent) {
        var amountInputField = parent.child('#AmountInput');
        var term = parent.child('#TermInput').getValue();

        if (term !== null) {
            this.setMinAndMaxAmounts(parent, term);

            var minValue = amountInputField.getMinValue();
            var maxValue = amountInputField.getMaxValue();
            var newValue = amountInputField.getValue();

            var message = 'Amount must be between $' + minValue + ' and $' + maxValue;

            if ((newValue !== null) && ((newValue < minValue) || (newValue > maxValue))) {
                amountInputField.addCls('x-invalid');

                parent.setActiveItem(amountInputField);
                amountInputField.focus();
                Ext.Msg.show({
                    title: 'Error',
                    message: message,
                    width: 300,
                    buttons: Ext.MessageBox.OK
                });
            } else {
                amountInputField.removeCls('x-invalid');
            }
        }
    },

    setMinAndMaxAmounts: function (financeset, term) {
        var amtInput = financeset.getComponent('AmountInput');
        var product = financeset.getComponent('ProductInput').getValue();

        var minMax = this.getMinAndMaxAmounts(product, term);

        amtInput.setMinValue(minMax.min);
        amtInput.setMaxValue(minMax.max);
    },

    getValidTerms: function (financeset) {
        var amtInput = financeset.getComponent('AmountInput').getValue();
        var product = financeset.getComponent('ProductInput').getValue();
        var choices = [];
        var validOptions = [];

        var store = Ext.getStore(flexi.ratesStore);

        var rates = store.queryBy(function (item) {
            var qProduct = item.get('productid');
            var min = item.get('minamount');
            var max = item.get('maxamount');
            if (qProduct == product) {
                if (amtInput !== null) {
                    if (amtInput < min || amtInput > max) {
                        return false;
                    }
                }
                var qRate = item.get('term');
                Ext.Array.include(choices, qRate);
                return true;
            } else {
                return false;
            }
        });

        Ext.Array.each(choices, function (term) {
            Ext.Array.include(validOptions, {
                text: term + ' Months',
                value: term
            });
        });

        return validOptions;
    },

    getMinAndMaxAmounts: function (product, term) {
        var store = Ext.getStore(flexi.ratesStore);
        var minValues = [];
        var maxValues = [];

        var rates = store.queryBy(function (item) {
            var qProduct = item.get('productid');
            var qTerm = item.get('term');
            var qMin = item.get('minamount');
            var qMax = item.get('maxamount');
            // Ignore the term if it's null and return All values
            // Also exclude the MAGIC fields with a 0 min
            if ((qProduct == product) && ((term === null) || (qTerm == term)) && (qMin > 0)) {
                Ext.Array.include(minValues, qMin);
                Ext.Array.include(maxValues, qMax);
                return true;
            } else {
                return false;
            }
        });

        var minimum = Ext.Array.min(minValues);
        var maximum = Ext.Array.max(maxValues);

        var result = {
            min: minimum,
            max: maximum
        };

        return result;
    }

});