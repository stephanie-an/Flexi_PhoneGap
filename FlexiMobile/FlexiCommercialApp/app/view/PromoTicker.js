Ext.define('FlexiCommercial.view.PromoTicker', {
    extend: 'Ext.dataview.DataView',
    alias: 'widget.promoticker',

    alternateClassName: [
        'PromoTicker'
    ],

    requires: [
        'FlexiCommercial.model.Content',
        'FlexiCommercial.store.LocalTickerStore'
    ],

    config: {
        id: 'PromoTicker',
        itemId: 'PromoTicker',
        padding: 10,
        styleHtmlContent: true,
        itemCls: 'ticker-text',
        scrollable: null,
        store: 'LocalTickerStore',
        itemTpl: '{content}',
        tickerSpeed: 20
    },

    initialize: function () {
        var itemContainer;

        this.callParent();

        // Set to start off screen
        itemContainer = this.getItemContainer();
        itemContainer.dom.style.marginLeft = window.innerWidth + 'px';

        this.getStore().on('refresh', this.startAnimation, this);
        this.getStore().load();
    },

    getItemContainer: function () {
        return this.getInnerItems()[0].innerElement;
    },

    startAnimation: function () {
        var i, items, nItems,
            realWidth = 0,
            margin = window.innerWidth,
            itemContainer = this.getItemContainer();

        if (this.animId) {
            this.stopAnimation(this.animId);
        }

        itemContainer = this.getItemContainer();
        items = itemContainer.dom.children;
        for (i = 0, nItems = items.length; i < nItems; i++) {
            var width = items[i].offsetWidth;
            if (width == 0) {
                // Nasty Hack... was working, then stopped. No idea why!
                // HTML wasn't ready, so guess it at 8px per character
                realWidth += items[i].innerHTML.length * 8
            } else {
                realWidth += width;
            }
        }
        itemContainer.dom.style.width = realWidth + 'px';

        // Get width of item container which will set the bounds of how far we have to scroll
        // before looping the animation
        this.animId = setInterval(function onInterval() {
            // Check if item has moved off screen, in which case we append to end

            // Check if container has cycled completely off screen
            if ((margin + itemContainer.dom.offsetWidth) < 0) {
                margin = window.innerWidth;
            }

            itemContainer.dom.style.marginLeft = margin-- + 'px';
        }, this.config.tickerSpeed);
    },

    stopAnimation: function () {
        clearInterval(this.animId);
        this.animId = null;
    }
});