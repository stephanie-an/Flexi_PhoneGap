Ext.define('FlexiCommercial.model.Quote', {
    extend: 'Ext.data.Model',

    requires: [
        'FlexiCommercial.model.Equipment',
        'FlexiCommercial.model.Product',
        'FlexiCommercial.model.SalesStaff',
        'FlexiCommercial.model.Tax'
    ],
    uses: [
        'FlexiCommercial.model.Equipment',
        'FlexiCommercial.model.Product',
        'FlexiCommercial.model.SalesStaff',
        'FlexiCommercial.model.Tax'
    ],

    config: {
        idProperty: 'uuid',
        identifier: {
            type: 'uuid'
        },
        fields: [
            {
                name: 'id',
                type: 'int'
            },
            {
                name: 'uuid',
                type: 'auto'
            },
            {
                name: 'term',
                type: 'int'
            },
            {
                name: 'amount',
                type: 'float'
            },
            {
                name: 'name',
                type: 'string'
            },
            {
                name: 'email',
                type: 'string'
            },
            {
                name: 'phone',
                type: 'string'
            },
            {
                name: 'comment',
                type: 'string'
            },
            {
                name: 'monthlypayment',
                type: 'float'
            },
            {
                name: 'monthlypaymentaftertax',
                type: 'float'
            },
            {
                name: 'weeklypayment',
                type: 'float'
            },
            {
                name: 'weeklypaymentaftertax',
                type: 'float'
            },
            {
                name: 'residual',
                type: 'float'
            },
            {
                name: 'status',
                type: 'string'
            },
            {
                name: 'createdate',
                type: 'date'
            },
            {
                name: 'lastdate',
                type: 'date'
            },
            {
                name: 'customer_id',
                type: 'auto'
            }
        ],
        hasOne: [
            {
                model: 'FlexiCommercial.model.Equipment',
                primaryKey: 'uuid'
            },
            {
                model: 'FlexiCommercial.model.Product',
                primaryKey: 'uuid'
            },
            {
                model: 'FlexiCommercial.model.SalesStaff',
                primaryKey: 'uuid'
            },
            {
                model: 'FlexiCommercial.model.Tax',
                primaryKey: 'uuid'
            }
        ],
        validations: [
            {
                type: 'presence',
                field: 'term'
            },
            {
                type: 'presence',
                field: 'amount'
            },
            {
                type: 'presence',
                field: 'name'
            },
            {
                type: 'presence',
                field: 'equipment_id'
            },
            {
                type: 'presence',
                field: 'product_id'
            },
            {
                type: 'presence',
                field: 'salesstaff_id'
            },
            {
                type: 'presence',
                field: 'tax_id'
            }
        ]
    }
});