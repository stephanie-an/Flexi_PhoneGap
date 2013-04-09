Ext.define('FlexiCommercial.view.HistoryDisplayPanel', {
    extend: 'Ext.Container',
    alias: 'widget.HistoryDisplayPanel',

    alternateClassName: [
        'HistoryDisplayPanel'
    ],
    requires: [
        'Ext.plugin.ListPaging'
    ],

    config: {
        itemId: 'HistoryDisplayPanel',
        layout: {
            type: 'fit'
        },
        items: [
            {
                xtype: 'list',
                itemId: 'HistoryList',
                styleHtmlContent: true,
                layout: {
                    type: 'fit'
                },
                itemTpl: new Ext.XTemplate(
                    '<div class="row">',
                    '    <div class="cell" style="width: 35%">',
                    '        <div class="cell-constrain">',
                    '            {lastdate:date("Y-m-d H:i:s")}<br/>',
                    '            <tpl for="Product">',
                    '                {name:this.trimproduct}',
                    '            </tpl>',
                    '        </div>',
                    '    </div>',
                    '',
                    '    <div class="cell" style="width: 45%">',
                    '        <div class="cell-constrain">',
                    '            <strong>',
                    '                {name}',
                    '            </strong><br/>',
                    '            <tpl for="Equipment">',
                    '                {name}',
                    '            </tpl>',
                    '        </div>',
                    '    </div>',
                    '',
                    '    <div class="cell"style="width: 20%">',
                    '        <div class="cell-constrain">',
                    '            ${amount}<br/>',
                    '            {term} Months',
                    '        </div>',
                    '    </div>',
                    '</div>',
                    {
                        trimproduct: function (value) {
                            return value.replace("Commercial ", "");
                        }
                    }
                ),
                scrollToTopOnRefresh: false,
                store: 'LocalQuoteStore',
                itemHeight: 63,
                preventSelectionOnDisclose: false,
                refreshHeightOnUpdate: false,
                variableHeights: false
            }
        ]
    }

});