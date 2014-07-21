<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.images.ImagesCheckDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
    
    config.id = 'images-check-dialog-win';
    config.layout = 'fit';
    config.width = 480;
    config.height = 300;
    config.modal = true;
    config.iconCls = 'icon-images-win';
    
    config.items = this.buildGrid();
    
    config.buttons = [
      {
        text: TocLanguage.btnClose,
        handler: function () {
          this.close();
        },
        scope: this
      }
    ];
    
    this.callParent([config]);
  },
  
  buildGrid: function() {
    this.grdImages = Ext.create('Ext.grid.Panel', {
      store: Ext.create('Ext.data.Store', {
        fields:[
          'group', 
          'count'
        ],
        pageSize: Toc.CONF.GRID_PAGE_SIZE,
        proxy: {
          type: 'ajax',
          url : '${ctx}/admin/images/check-images',
          reader: {
            type: 'json',
            root: Toc.CONF.JSON_READER_ROOT,
            totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
          }
        },
        autoLoad: true
      }),
      
      border: false,
      
      columns: [
        {header: '<spring:message code="images_check_table_heading_groups"></spring:message>', dataIndex: 'group', flex: 1},
        {header: '<spring:message code="images_check_table_heading_results"></spring:message>', dataIndex: 'count', align: 'center', width: 200},
      ],
      
      tbar: [
        {
          text: TocLanguage.btnRefresh,
          iconCls: 'refresh',
          handler: this.onRefresh,
          scope: this
        }
      ]
    });
    
    return this.grdImages;
  },
  
  onRefresh: function () {
    this.grdImages.getStore().load();
  }
});