Ext.define('FlexiCommercial.model.Equipment', {
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
            }
        ]
    }
});