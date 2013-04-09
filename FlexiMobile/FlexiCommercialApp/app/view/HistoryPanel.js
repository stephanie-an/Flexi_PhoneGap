Ext.define('FlexiCommercial.view.HistoryPanel', {
    extend: 'Ext.navigation.View',
    alias: 'widget.HistoryPanel',

    alternateClassName: [
        'HistoryPanel'
    ],
    requires: [
        'FlexiCommercial.view.HistoryDisplayPanel'
    ],

    config: {
        itemId: 'HistoryPanel',
        items: [
            {
                xtype: 'HistoryDisplayPanel',
                itemId: 'HistoryDisplay',
                title: '<img class="logo" src="resources/images/logo-small.png"/>'
            }
        ]
    }

});