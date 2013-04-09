Ext.define('FlexiCommercial.writer.QuoteJsonWriter', {
    extend: 'Ext.data.writer.Json',

    alias: 'writer.QuoteJsonWriter',

    config: {
        type: 'json'
    },

    requires: [
        'FlexiCommercial.model.Quote'
    ],

    getRecordData: function (record) {
        var product = record.getProduct();
        var equipment = record.getEquipment();
        var tax = record.getTax();
        var staff = record.getSalesStaff();

        var customJson = {
            uuid: record.get('uuid'),
            term: record.get('term'),
            amount: record.get('amount'),
            monthlypayment: record.get('monthlypayment'),
            monthlypaymentaftertax: record.get('monthlypaymentaftertax'),
            weeklypayment: record.get('weeklypayment'),
            weeklypaymentaftertax: record.get('weeklypaymentaftertax'),
            residual: record.get('residual'),
            submitdate: new Date(),
            customer: {
                name: record.get('name'),
                email: record.get('email'),
                phone: record.get('phone'),
                comment: record.get('comment')
            },
            equipment: {
                name: equipment.get('name')
            },
            product: {
                name: product.get('name')
            },
            salesStaff: {
                uuid: staff.get('uuid'),
                firstname: staff.get('firstName'),
                lastname: staff.get('lastName'),
                email: staff.get('email'),
                phone: staff.get('phone'),
                dealer: {
                    name: staff.get('dealer')
                }
            },
            statuscode: {
                value: "SUBMITTED"
            },
            tax: {
                description: tax.get('description')
            }
        };

        return customJson;
    }

});