Ext.define('FlexiCommercial.view.HomeContainer', {
    extend: 'Ext.navigation.View',
    alias: 'widget.homecontainer',

    requires: [
        'FlexiCommercial.view.HomePanel',
        'FlexiCommercial.view.PromoTicker'
    ],

    config: {
        id: 'HomeContainer',
        itemId: 'HomeContainer',
        items: [
            {
                xtype: 'HomePanel',
                id: 'HomeScreen',
                itemId: 'HomeScreen',
                title: '<img class="logo" src="resources/images/logo-small.png"/>'
            }
        ]
    }

});