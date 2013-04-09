Ext.define('FlexiCommercial.model.Content', {
    extend: 'Ext.data.Model',

    config: {
        idProperty: 'uuid',
        identifier: {
            type: 'uuid'
        },
        fields: [
            {
                name: 'uuid',
                type: 'auto'
            },
            {
                name: 'type',
                type: 'int'
            },
            {
                name: 'productid',
                type: 'int'
            },
            {
                name: 'activate',
                type: 'int'
            },
            {
                name: 'expire',
                type: 'int'
            },
            {
                name: 'content',
                type: 'string'
            },
            {
                name: 'imagelink',
                type: 'string'
            }
        ]
    }
});