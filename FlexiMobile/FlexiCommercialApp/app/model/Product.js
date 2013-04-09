Ext.define('FlexiCommercial.model.Product', {
    extend: 'Ext.data.Model',

    uses: [
        'FlexiCommercial.model.Rates'
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
                name: 'name',
                type: 'string'
            }
        ],
        hasMany: {
            associationKey: 'productid',
            model: 'FlexiCommercial.model.Rates'
        }
    }
});