<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.manufacturers.ManufacturersDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
    
    config.id = 'manufacturers_dialog-win';
    config.title = '<spring:message code="action_heading_new_manufacturer"></spring:message>';
    config.width = 500;
    config.height = 380;
    config.modal = true;
    config.layout = 'fit';
    config.iconCls = 'icon-manufacturers-win';
    
    config.items = this.buildForm();
    
    config.buttons = [
      {
        text: TocLanguage.btnSave,
        handler: function() {
          this.submitForm();
        },
        scope: this
      },
      {
        text: TocLanguage.btnClose,
        handler: function() { 
          this.close();
        },
        scope: this
      }
    ];
    
    this.addEvents({'savesuccess' : true});
    
    this.callParent([config]);  
  },
  
  show: function(id) {
    var manufacturersId = id || null;
    
    if (manufacturersId > 0) {
      this.frmManufacturer.baseParams['manufacturerId'] = manufacturersId;
      
      this.frmManufacturer.load({
        url : '${ctx}/admin/ajax/manufacturers/load-manufacturer',
        success: function(form, action) {
          var imgHtml = action.result.data.manufacturerImage;
          
          if (imgHtml) {
            this.pnlGeneral.getComponent('manufactuerer_image_panel').update(imgHtml);
          }
          
          Toc.manufacturers.ManufacturersDialog.superclass.show.call(this);
        },
        failure: function() {
          Ext.Msg.alert(TocLanguage.msgErrTitle, TocLanguage.msgErrLoadData);
        },
        scope: this       
      });
    } else {   
      this.callParent();
    }
  },
  
  buildForm: function() {
    this.pnlGeneral = Ext.create('Toc.manufacturers.GeneralPanel');
    this.pnlMetaInfo = Ext.create('Toc.manufacturers.MetaInfoPanel');
    
    var tabManufacturers = Ext.create('Ext.tab.Panel', {
      activeTab: 0,
      border: false,
      defaults:{
        hideMode:'offsets'
      },
      deferredRender: false,
      items: [
        this.pnlGeneral,
        this.pnlMetaInfo
      ]
    });
    
    this.frmManufacturer = Ext.create('Ext.form.Panel', {
      id: 'form-manufacturers',
      layout: 'fit',
      border: false,
      fileUpload: true,
      fieldDefaults: {
        labelSeparator: '',
        labelWidth: 100,
        anchor: '97%'
      },
      url : '${ctx}/admin/ajax/manufacturers/save-manufacturer',
      baseParams: {},
      items: tabManufacturers
    });
    
    return this.frmManufacturer;
  },
  
  submitForm : function() {
    this.frmManufacturer.form.submit({
      waitMsg: TocLanguage.formSubmitWaitMsg,
      success: function(form, action) {
         this.fireEvent('savesuccess', action.result.feedback);
         this.close();  
      },    
      failure: function(form, action) {
        if (action.failureType != 'client') {
          Ext.MessageBox.alert(TocLanguage.msgErrTitle, action.result.feedback);
        }
      },  
      scope: this
    });   
  }
});