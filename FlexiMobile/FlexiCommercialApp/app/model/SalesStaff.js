Ext.define('FlexiCommercial.model.SalesStaff', {
    extend: 'Ext.data.Model',

    config: {
        idProperty: 'uuid',
        identifier: {
            type: 'uuid'
        },
        fields: [
            {
                name: 'uuid',
                mapping: 'uuid',
                type: 'auto'
            },
            {
                name: 'firstName',
                mapping: 'firstname',
                type: 'string'
            },
            {
                name: 'lastName',
                mapping: 'lastname',
                type: 'string'
            },
            {
                name: 'email',
                mapping: 'email',
                type: 'string'
            },
            {
                name: 'phone',
                mapping: 'phone',
                type: 'string'
            },
            {
                name: 'dealer',
                mapping: 'dealer.name',
                type: 'string'
            },
            {
                name: 'acceptTaC',
                type: 'boolean'
            },
            {
                name: 'isCurrent',
                defaultValue: true,
                type: 'boolean'
            },
            {
                name: 'defaultProduct',
                type: 'int'
            },
            {
                name: 'savedRetention',
                type: 'int'
            },
            {
                name: 'submitRetention',
                type: 'int'
            }
        ],
        validations: [
            {
                type: 'presence',
                field: 'firstName',
                message: 'First Name is mandatory'
            },
            {
                type: 'presence',
                field: 'lastName',
                message: 'Last Name is mandatory'
            },
            {
                type: 'presence',
                field: 'dealer',
                message: 'Dealer is mandatory'
            }
        ]
    }
});