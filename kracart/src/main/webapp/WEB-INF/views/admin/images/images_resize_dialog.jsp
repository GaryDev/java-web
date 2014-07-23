<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.images.ImagesResizeDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
    
    config.id = 'images-resize-dialog-win';
    config.layout = 'fit';
    config.width = 480;
    config.height = 300;
    config.modal = true;
    config.iconCls = 'icon-images-win';
    
    config.items = this.buildForm();
    
    config.buttons = [
      {
        id: 'btn-execute-resize-images',
        text: TocLanguage.tipExecute,
        handler: function () {
          Ext.getCmp('btn-execute-resize-images').hide();
          this.submitForm();
        },
        scope: this
      },
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
  
  buildForm: function() {
    var dsImageGroups = Ext.create('Ext.data.Store', {
      fields:[
        'id', 
        'text'
      ],
      pageSize: Toc.CONF.GRID_PAGE_SIZE,
      proxy: {
        type: 'ajax',
        url : '${ctx}/admin/images/get-imagegroups',
        reader: {
          type: 'json',
          root: Toc.CONF.JSON_READER_ROOT,
          totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
        }
      },
      autoLoad: true
    });
    
    this.lstImage = Ext.create('Ext.ux.form.MultiSelect', {
      fieldLabel: '<spring:message code="images_resize_field_groups"></spring:message>',
      store: dsImageGroups,
      name: 'groups[]',
      width: 400,
      height: 150,
      legend: '<spring:message code="images_resize_table_heading_groups"></spring:message>',
      displayField: 'text',
      valueField: 'id'
    });
    
    this.chkImage = Ext.create('Ext.form.Checkbox', {
      fieldLabel: '<spring:message code="images_resize_field_overwrite_images"></spring:message>',
      name: 'overwrite',
      inputValue: '1'
    });
    
    this.frmImage = Ext.create('Ext.form.Panel', {
      border: false,
      bodyPadding: 10,
      fieldDefaults: {
        labelSeparator: '',
        labelWidth: 150
      },
      items:[this.lstImage, this.chkImage]
    });
    
    return this.frmImage;
  },
  
  submitForm: function() {
    var groups = Ext.JSON.encode(this.lstImage.getValue()) || '';
    var overwrite = this.chkImage.getValue() ? 1 : '';
    
    this.removeAll();
    
    this.grdImages = Ext.create('Ext.grid.Panel', {
      border: false,
      store: Ext.create('Ext.data.Store', {
        fields:[
          'group', 
          'count'
        ],
        pageSize: Toc.CONF.GRID_PAGE_SIZE,
        proxy: {
          type: 'ajax',
          url : '${ctx}/admin/images/list-imagesresize-result',
          conn: { timeout: 600000 },
          extraParams: {
            overwrite: overwrite,
            groups: groups
          },
          reader: {
            type: 'json',
            root: Toc.CONF.JSON_READER_ROOT,
            totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
          }
        },
        autoLoad: true
      }),
      
      columns: [
        {header: '<spring:message code="images_resize_table_heading_groups"></spring:message>', dataIndex: 'group', flex: 1},
        {header: '<spring:message code="images_resize_table_heading_total_resized"></spring:message>', dataIndex: 'count'},
      ]
    });
    
    this.add(this.grdImages);
    this.doLayout();
  }
});