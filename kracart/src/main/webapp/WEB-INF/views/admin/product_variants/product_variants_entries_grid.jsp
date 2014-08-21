<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.product_variants.ProductVariantsEntriesGrid', {
  extend: 'Ext.grid.Panel',
  
  constructor: function(config) {
    config = config || {};
    
    config.title = '<spring:message code="heading_product_variants_title"></spring:message>';
    config.region = 'east';
    config.border = false;
    config.split = true;
    config.minWidth = 240;
    config.maxWidth = 320;
    config.width = 260;
    config.viewConfig = {emptyText: TocLanguage.gridNoRecords};
    
    config.store = Ext.create('Ext.data.Store', {
      fields: ['valuesId', 'valuesName'],
      pageSize: Toc.CONF.GRID_PAGE_SIZE,
      proxy: {
        type: 'ajax',
        url : '${ctx}/admin/ajax/product-variants/list-product-variants-entries',
        reader: {
          type: 'json',
          root: Toc.CONF.JSON_READER_ROOT,
          totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
        }
      }
    });
    
    config.selModel = Ext.create('Ext.selection.CheckboxModel');
    config.columns = [
      { header: '<spring:message code="table_heading_entries"></spring:message>', dataIndex: 'valuesName', flex: 1},
      {
        xtype:'actioncolumn', 
        width: 60,
        header: '<spring:message code="table_heading_action"></spring:message>',
        items: [{
          iconCls: 'icon-action icon-edit-record',
          tooltip: TocLanguage.tipEdit,
          handler: function(grid, rowIndex, colIndex) {
            var rec = grid.getStore().getAt(rowIndex);
            
            this.fireEvent('edit', rec);
          },
          scope: this
        },{
            iconCls: 'icon-action icon-delete-record',
            tooltip: TocLanguage.tipDelete,
            handler: function(grid, rowIndex, colIndex) {
              var rec = grid.getStore().getAt(rowIndex);
              
              this.onDelete(rec);
            },
            scope: this                
        }]
      }
    ];
    
    config.tbar = [
      {
        text: TocLanguage.btnAdd,
        iconCls: 'add',
        handler: function() {this.fireEvent('create', this.variantsGroupsId);},
        scope: this
      },
      '-',
      {
        text: TocLanguage.btnDelete,
        iconCls: 'remove',
        handler: this.onBatchDelete,
        scope: this
      },
      '-',
      {
        text: TocLanguage.btnRefresh,
        iconCls: 'refresh',
        handler: this.onRefresh,
        scope: this
      }
    ];
    
    this.variantsGroupsId = null;
    this.variantsGroupsName = null;
    
    this.addEvents({'notifysuccess': true, 'create': true, 'edit': true});
    
    this.callParent([config]);
  },
  
  iniGrid: function(record) {
    this.variantsGroupsId = record.get('groupsId');
    this.variantsGroupsName = record.get('groupsName');
    
    this.getStore().getProxy().extraParams['groupsId'] = record.get('groupsId');
    this.onRefresh();
  },
  
  onDelete: function(record) {
    var variantsValuesId = record.get('valuesId');
    var variantsGroupsId = this.variantsGroupsId;
    
    Ext.MessageBox.confirm(
      TocLanguage.msgWarningTitle, 
      TocLanguage.msgDeleteConfirm, 
      function (btn) {
        if (btn == 'yes') {
          Ext.Ajax.request({
            waitMsg: TocLanguage.formSubmitWaitMsg,
            url : '${ctx}/admin/ajax/product-variants/delete-product-variants-entry',
            params: {
              valuesId: variantsValuesId,
              groupsId: variantsGroupsId
            },
            callback: function (options, success, response) {
              var result = Ext.decode(response.responseText);
              
              if (result.success == true) {
                this.fireEvent('notifysuccess', result.feedback);
                this.onRefresh();
              } else {
                Ext.MessageBox.alert(TocLanguage.msgErrTitle, result.feedback);
              }
            },
            scope: this
          });
        }
      }, 
      this
    );
  },
  
  onBatchDelete: function() {
    var selections = this.selModel.getSelection();
    var variantsGroupsId = this.variantsGroupsId;
    
    keys = [];
    Ext.each(selections, function(item) {
      keys.push(item.get('valuesId'));
    });
    
    if (keys.length > 0) {
      var batch = Ext.JSON.encode(keys);
      
      Ext.MessageBox.confirm(
        TocLanguage.msgWarningTitle, 
        TocLanguage.msgDeleteConfirm,
        function(btn) {
          if (btn == 'yes') {
            Ext.Ajax.request({
              waitMsg: TocLanguage.formSubmitWaitMsg,
              url : '${ctx}/admin/ajax/product-variants/delete-product-variants-entries',
              params: {
                batch: batch, 
                groupsId: variantsGroupsId
              },
              callback: function(options, success, response) {
                var result = Ext.decode(response.responseText);
                
                if (result.success == true) {
                  this.fireEvent('notifysuccess', result.feedback);
                  
                  this.onRefresh();
                } else {
                  Ext.MessageBox.alert(TocLanguage.msgErrTitle, result.feedback);
                }
              }, 
              scope: this
            });
          }
        }, 
        this
      );
    } else {
      Ext.MessageBox.alert(TocLanguage.msgInfoTitle, TocLanguage.msgMustSelectOne);
    }
  },
  
  onRefresh: function() {
    this.getStore().load();
  }
});
