<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.images.ImagesGrid', {
  extend: 'Ext.grid.Panel',
  
  constructor: function(config) {
    config = config || {};
    
    config.border = false;
    config.region = 'center';
    
    config.store = Ext.create('Ext.data.Store', {
      fields:[
        'module', 
        'run'
      ],
      pageSize: Toc.CONF.GRID_PAGE_SIZE,
      proxy: {
        type: 'ajax',
        url : '${ctx}/admin/images/list-images',
        reader: {
          type: 'json',
          root: Toc.CONF.JSON_READER_ROOT,
          totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
        }
      },
      autoLoad: true
    });
    
    config.columns =[
      {header: '<spring:message code="table_heading_modules"></spring:message>', dataIndex: 'module', flex: 1},
      {
        xtype: 'actioncolumn', 
        width: 50,
        header: '<spring:message code="table_heading_action"></spring:message>',
        items: [{
          tooltip: TocLanguage.tipExecute,
          iconCls: 'icon-action icon-execute-record',
          handler: function(grid, rowIndex, colIndex) {
            var rec = grid.getStore().getAt(rowIndex);
            
            this.fireEvent(rec.get('run'), rec);
          },
          scope: this
        }]
      }
    ];
    
    config.tbar = [
      {
        text: TocLanguage.btnRefresh,
        iconCls: 'refresh',
        handler: this.onRefresh,
        scope: this
      }
    ];
    
    this.addEvents({'checkimages': true, 'resizeimages': true});
    
    this.callParent([config]);
  },
  
  onRefresh: function() {
    this.getStore().load();
  }
});