<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.newsletters.LogDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
    
    config.id = 'log-dialog-win';
    config.title = '<spring:message code="heading_newsletters_title"></spring:message>';
    config.layout = 'fit';
    config.width = 600;
    config.height = 350;
    
    config.items = this.buildGrid();
    
    config.buttons = [
      {
        text: TocLanguage.btnClose,
        handler: function() { 
          this.close();
        },
        scope: this
      }
    ];
    
    this.callParent([config]);
  },
  
  show: function(newslettersId) {
    this.grdLog.getStore().getProxy().extraParams['newsletters_id'] = newslettersId;
    this.grdLog.getStore().load();
     
    this.callParent();
  },
  
  buildGrid: function() {
    var dsLog = Ext.create('Ext.data.Store', {
      fields:[
        'email_address', 
        'sent',
        'date_sent'
      ],
      pageSize: Toc.CONF.GRID_PAGE_SIZE,
      proxy: {
        type: 'ajax',
        url : '${ctx}/admin/newsletters/list-log',
        reader: {
          type: 'json',
          root: Toc.CONF.JSON_READER_ROOT,
          totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
        }
      }
    });
    
    this.grdLog = Ext.create('Ext.grid.Panel', {
      store: dsLog,
      border: false,
      columns: [
        {header: '<spring:message code="table_heading_email_addresses"></spring:message>', dataIndex: 'email_address', flex: 1},
        {header: '<spring:message code="table_heading_sent"></spring:message>', width: 100, align: 'center', dataIndex: 'sent'},
        {header: '<spring:message code="table_heading_date_sent"></spring:message>', width: 150, align: 'center', dataIndex: 'date_sent'},
      ],
      dockedItems: [{
        xtype: 'pagingtoolbar',
        store: dsLog,
        dock: 'bottom',
        displayInfo: true
      }]
    });
    
    return this.grdLog;
  }
});