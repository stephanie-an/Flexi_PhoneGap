Ext.define('FlexiCommercial.model.RuntimeConfig', {
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
                name: 'version',
                type: 'int'
            },
            {
                name: 'contentlastcheck',
                type: 'date'
            },
            {
                name: 'equipmentlastcheck',
                type: 'date'
            },
            {
                name: 'productlastcheck',
                type: 'date'
            },
            {
                name: 'rateslastcheck',
                type: 'date'
            },
            {
                name: 'taxlastcheck',
                type: 'date'
            },
            {
                name: 'tickerlastcheck',
                type: 'date'
            }
        ]
    }
});