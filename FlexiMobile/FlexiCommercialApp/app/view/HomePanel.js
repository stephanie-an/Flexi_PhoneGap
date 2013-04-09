Ext.define('FlexiCommercial.view.HomePanel', {
    extend: 'Ext.Panel',
    alias: 'widget.HomePanel',

    config: {
        id: 'HomePanel',
        itemId: 'HomePanel',
        layout: {
            type: 'vbox'
        },
        scrollable: 'vertical',
        items: [
            {
                xtype: 'container',
                id: 'Row1',
                itemId: 'Row1',
                padding: '10 10 10 10',
                styleHtmlContent: true,
                layout: {
                    align: 'center',
                    pack: 'center',
                    type: 'hbox'
                },
                items: [
                    {
                        xtype: 'button',
                        height: '140px',
                        id: 'FlexiBenefitsButton',
                        itemId: 'FlexiBenefitsButton',
                        styleHtmlContent: true,
                        cls: 'home-button',
                        width: '140px',
                        icon: 'resources/images/FlexiBenefits-Button.png',
                        iconCls: 'home-button-icon'
                    },
                    {
                        xtype: 'spacer',
                        width: 20
                    },
                    {
                        xtype: 'button',
                        height: '140px',
                        id: 'HomeCalculateButton',
                        itemId: 'HomeCalculateButton',
                        styleHtmlContent: true,
                        cls: 'home-button',
                        width: '140px',
                        icon: 'resources/images/Quote-Button.png',
                        iconCls: 'home-button-icon'
                    }
                ]
            },
            {
                xtype: 'container',
                id: 'Row2',
                itemId: 'Row2',
                padding: '10 10 10 10',
                styleHtmlContent: true,
                layout: {
                    align: 'center',
                    pack: 'center',
                    type: 'hbox'
                },
                items: [
                    {
                        xtype: 'button',
                        height: '140px',
                        id: 'LatestOffersButton',
                        itemId: 'LatestOffersButton',
                        styleHtmlContent: true,
                        cls: 'home-button',
                        width: '140px',
                        icon: 'resources/images/NewsOffers-Button.png',
                        iconCls: 'home-button-icon'
                    },
                    {
                        xtype: 'spacer',
                        width: 20
                    },
                    {
                        xtype: 'button',
                        height: '140px',
                        id: 'KeyContactsButton',
                        itemId: 'KeyContactsButton',
                        styleHtmlContent: true,
                        cls: 'home-button',
                        width: '140px',
                        icon: 'resources/images/KeyContacts-Button.png',
                        iconCls: 'home-button-icon'
                    }
                ]
            },
            {
                xtype: 'promoticker',
                docked: 'bottom',
                height: '40px'
            }
        ]
    }

});