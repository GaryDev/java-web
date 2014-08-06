<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.newsletters.NewslettersDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
    
    config.id = 'newsletters-dialog-win';
    config.title = '<spring:message code="action_heading_new_newsletter"></spring:message>';
    config.width = 700;
    config.height = 400;
    
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
  
  show: function (newsletterId) {
    this.newsletterId = newsletterId || null;
    
    if (this.newsletterId > 0) {
      this.frmNewsletter.form.baseParams['newsletterId'] = this.newsletterId;
      
      this.frmNewsletter.load({
        url: '${ctx}/admin/ajax/newsletters/load-newsletter',
        success: function(form, action) {
          Toc.newsletters.NewslettersDialog.superclass.show.call(this);
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
    this.dsModules = Ext.create('Ext.data.Store', {
      fields:[
        'id', 
        'text'
      ],
      pageSize: Toc.CONF.GRID_PAGE_SIZE,
      proxy: {
        type: 'ajax',
        url : '${ctx}/admin/ajax/newsletters/get-modules',
        reader: {
          type: 'json',
          root: Toc.CONF.JSON_READER_ROOT,
          totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
        }
      },
      autoLoad: true
    });
    
    this.frmNewsletter = Ext.create('Ext.form.Panel', {
      url: '${ctx}/admin/ajax/newsletters/save-newsletter',
      baseParams: {},
      border: false,
      bodyPadding: 10,
      fieldDefaults: {
        labelSeparator: '',
        anchor: '97%',
        labelWidth: 100
      },
      items: [ 
        {
          xtype: 'combo',
          name: 'module',
          fieldLabel: '<spring:message code="field_module"></spring:message>', 
          store: this.dsModules,
          queryMode: 'local',
          valueField: 'id',
          editable: false,
          displayField: 'text',
          forceSelection: true  
        },
        {
          xtype: 'textfield', 
          name: 'title', 
          fieldLabel: '<spring:message code="field_title"></spring:message>', 
          allowBlank: false
        },
        {
          xtype: 'htmleditor',
          name: 'content', 
          fieldLabel: '<spring:message code="field_content"></spring:message>', 
          height: 250
        }
      ]
    });
    
    return this.frmNewsletter;
  },
  
  submitForm : function() {
    this.frmNewsletter.form.submit({
      waitMsg: TocLanguage.formSubmitWaitMsg,
      success: function(form, action){
         this.fireEvent('savesuccess', action.result.feedback);
         this.close();  
      },    
      failure: function(form, action) {
        if (action.failureType != 'client') {
          Ext.Msg.alert(TocLanguage.msgErrTitle, action.result.feedback);
        }
      },  
      scope: this
    });   
  }
});