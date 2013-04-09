Ext.define('FlexiCommercial.view.TabPanel', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.tabpanel',

    requires: [
        'FlexiCommercial.view.HomeContainer',
        'FlexiCommercial.view.HistoryPanel',
        'FlexiCommercial.view.Settings'
    ],

    config: {
        fullscreen: true,
        id: 'TabPanel',
        items: [
            {
                xtype: 'homecontainer',
                id: 'Home',
                itemId: 'Home',
                scrollable: true,
                title: 'Home',
                iconCls: 'home'
            },
            {
                xtype: 'HistoryPanel',
                id: 'SavedHistory',
                itemId: 'SavedHistory',
                title: 'Saved',
                iconCls: 'organize'
            },
            {
                xtype: 'HistoryPanel',
                id: 'SentHistory',
                itemId: 'SentHistory',
                title: 'Sent',
                iconCls: 'favorites'
            },
            {
                xtype: 'HistoryPanel',
                id: 'PendingHistory',
                itemId: 'PendingHistory',
                title: 'Pending',
                iconCls: 'download',
                navigationBar: {
                    items: [
                        {
                            xtype: 'button',
                            itemId: 'RetryAllButton',
                            text: 'Retry All',
                            align: 'right'
                        }
                    ]
                }
            },
            {
                xtype: 'settings',
                title: 'Settings',
                id: 'Settings',
                itemId: 'Settings',
                iconCls: 'settings'
            }
        ],
        tabBar: {
            docked: 'bottom',
            id: 'TabBar',
            itemId: 'TabBar'
        }
    },

    initialize: function () {
        var buttons,
            self = this;

        this.callParent();

        // This is a little hack to make our tabs clickable even if they're
        // currently active. Tab Panel always inserts the TabBar at position 1,
        // so you need to add 1 to the index EXCEPT for index 0! yeah!
        buttons = this.getTabBar().getItems();
        buttons.each(function (button, index) {
            button.on('tap', function () {
                this.onTabPanelButtonTap(button, index > 0 ? index + 1 : 0);
            }, this);
        }, this);
    },

    onTabPanelButtonTap: function (button, index) {
        var item, nItems;

        item = this.getItems().getAt(index);
        if (item instanceof Ext.navigation.View) {
            nItems = item.getInnerItems().length;
            if (nItems > 1) {
                // Pop all items except the first to return to base view state
                item.pop(nItems - 1);
            }
        }
    }

});