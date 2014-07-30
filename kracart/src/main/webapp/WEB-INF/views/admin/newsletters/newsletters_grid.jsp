<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.newsletters.NewslettersGrid', {
  extend: 'Ext.grid.Panel',
  
  constructor: function(config) {
    config = config || {};
    
    config.border = false;
    config.viewConfig = {emptyText: TocLanguage.gridNoRecords};
    
    config.store = Ext.create('Ext.data.Store', {
      fields:[
        'newsletterId', 
        'title',
        'size',
        'module',
        'sent',
        'actionClass'
      ],
      pageSize: Toc.CONF.GRID_PAGE_SIZE,
      proxy: {
        type: 'ajax',
        url : '${ctx}/admin/newsletters/list-newsletters',
        reader: {
          type: 'json',
          root: Toc.CONF.JSON_READER_ROOT,
          totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
        }
      },
      autoLoad: true
    });
    
    config.selModel = Ext.create('Ext.selection.CheckboxModel');
    config.columns =[
      {header: '<spring:message code="table_heading_newsletters"></spring:message>', dataIndex: 'title', flex: 1},
      {header: '<spring:message code="table_heading_size"></spring:message>', width: 60, align: 'center', dataIndex: 'size'},
      {header: '<spring:message code="table_heading_module"></spring:message>', width: 140, align: 'center', dataIndex: 'module'},
      {header: '<spring:message code="table_heading_sent"></spring:message>', width: 60, align: 'center', dataIndex: 'sent'},
      {
        xtype: 'actioncolumn', 
        width: 130,
        header: '<spring:message code="table_heading_action"></spring:message>',
        items: [{
          tooltip: TocLanguage.tipEdit,
          getClass: this.getEditClass,
          handler: function(grid, rowIndex, colIndex) {
            var rec = grid.getStore().getAt(rowIndex);
            
            this.fireEvent('edit', rec);
          },
          scope: this
        },
        {
          tooltip: '<spring:message code="icon_log"></spring:message>',
          getClass: this.getLogClass,
          handler: function(grid, rowIndex, colIndex) {
            var rec = grid.getStore().getAt(rowIndex);
            
            this.fireEvent('log', rec);
          },
          scope: this
        },
        {
          tooltip: '<spring:message code="icon_email_send"></spring:message>',
          getClass: this.getEmailClass,
          handler: function(grid, rowIndex, colIndex) {
            var rec = grid.getStore().getAt(rowIndex);
            
            this.onSendEmails(rec);
          },
          scope: this
        },
        {
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
        handler: function() {this.fireEvent('create');},
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
        iconCls:'refresh',
        handler: this.onRefresh,
        scope: this
      }
    ];
    
    config.dockedItems = [{
      xtype: 'pagingtoolbar',
      store: config.store,
      dock: 'bottom',
      displayInfo: true
    }];
    
    this.addEvents({'notifysuccess': true, 'create': true, 'edit': true, 'sendmails': true, 'log': true, 'sendnewsletters': true});
    
    this.callParent([config]);
  },
  
  onDelete: function(record) {
    var newsletterId = record.get('newsletterId');
    
    Ext.MessageBox.confirm(
      TocLanguage.msgWarningTitle, 
      TocLanguage.msgDeleteConfirm, 
      function (btn) {
        if (btn == 'yes') {
          Ext.Ajax.request({
            waitMsg: TocLanguage.formSubmitWaitMsg,
            url: '${ctx}/admin/newsletters/delete-newsletter',
            params: {
              newsletterId: newsletterId
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
    
    keys = [];
    Ext.each(selections, function(item) {
      keys.push(item.get('newsletterId'));
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
              url: '${ctx}/admin/newsletters/delete-newsletters',
              params: {
                batch: batch
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
  
  onSendEmails: function(record) {
    var module = record.get('module');
    var newsletterId = record.get('newsletterId');
    
    switch(module) {
      case 'email':
        this.fireEvent('sendemails', newsletterId);
        break;
      
      case 'newsletter':
        this.fireEvent('sendnewsletters', newsletterId); 
        break;
    }
  },
  
  onRefresh: function() {
    this.getStore().load();
  },
  
  getEditClass: function(v, meta, rec) {
    switch (rec.get('actionClass')) {
      case 'icon-log-record':
        return 'icon-action';
        break;
        
      default:
        return 'icon-action icon-edit-record';
    }
  },
  
  getLogClass: function(v, meta, rec) {
    switch (rec.get('actionClass')) {
      case 'icon-log-record':
        return 'icon-action icon-log-record';
        break;
        
      default:
        return 'icon-action-hide';
    }
  },
  
  getEmailClass: function(v, meta, rec) {
    switch (rec.get('actionClass')) {
      case 'icon-send-email-record':
        return 'icon-action icon-send-email-record';
        break;
        
      default:
        return 'icon-action-hide';
    }
  }
});