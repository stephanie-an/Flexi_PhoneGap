Ext.define('FlexiCommercial.view.GenericDisplayPanel', {
    extend: 'Ext.Container',
    alias: 'widget.GenericDisplayPanel',

    config: {
        id: 'GenericDisplayPanel',
        itemId: 'GenericDisplayPanel',
        autoDestroy: true,
        styleHtmlContent: true,
        layout: {
            type: 'fit'
        },
        scrollable: 'vertical'
    }
});