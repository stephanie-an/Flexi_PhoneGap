Ext.define('FlexiCommercial.model.Tax', {
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
                name: 'description',
                type: 'string'
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
                name: 'rate',
                type: 'float'
            },
            {
                name: 'multiplyFactor',
                type: 'float'
            }
        ]
    }
});