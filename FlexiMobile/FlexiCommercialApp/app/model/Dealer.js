Ext.define('FlexiCommercial.model.Dealer', {
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
            }
        ]
    }
});