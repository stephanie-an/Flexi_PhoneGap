Ext.define('FlexiCommercial.model.Rates', {
    extend: 'Ext.data.Model',

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
                name: 'minamount',
                type: 'int'
            },
            {
                name: 'maxamount',
                type: 'int'
            },
            {
                name: 'term',
                type: 'int'
            },
            {
                name: 'rate',
                type: 'float'
            },
            {
                name: 'productid',
                type: 'int'
            }
        ]
    }
});