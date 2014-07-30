<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.newsletters.SendEmailsDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
  
    config.id = 'send-emails-dialog-win';
    config.title = '<spring:message code="heading_newsletters_title"></spring:message>';
    config.width = 600;
    config.layout = 'fit';
    config.modal = true;
    
    config.items = this.buildForm();
    
    config.buttons = [
      {
        text: '<spring:message code="button_ok"></spring:message>',
        id: 'btn-send-emails',
        handler: this.onAction,
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
    
    this.addEvents({'sendsuccess' : true});
    
    this.callParent([config]);
  },
  
  show: function (newsletterId) {
    this.newsletterId = newsletterId || null;
    
    this.callParent();
  },
  
  onAction: function() {
    text = Ext.getCmp('btn-send-emails').getText();
    
    if (text == '<spring:message code="button_ok"></spring:message>') {
      this.showConfirmation();
    } else {
      this.sendEmails();
    }
  },
  
  sendEmails: function() {
    var batch = Ext.JSON.encode(this.selAudience.getValue());
  
    this.pnlSendEmail.el.mask('<spring:message code="sending_please_wait"></spring:message>', 'x-mask-loading');
    
    Ext.Ajax.request({
      url: '${ctx}/admin/newsletters/send-emails',
      params: {
        newsletterId: this.newsletterId,
        batch: batch
      },
      callback: function(options, success, response) {
        var result = Ext.decode(response.responseText);
        
        if (result.success == true) {
         this.fireEvent('sendsuccess', result.feedback);
         this.close();        
        } else {
          Ext.MessageBox.alert(TocLanguage.msgErrTitle, result.feedback);
        }
        
        this.pnlSendEmail.el.unmask();
      },
      scope: this
    }); 
  },
  
  showConfirmation: function() {
    var batch = Ext.JSON.encode(this.selAudience.getValue());
    
    if ( Ext.isEmpty(batch) ) {
      Ext.MessageBox.alert(TocLanguage.msgInfoTitle, TocLanguage.msgMustSelectOne);
      return;
    }  
  
    this.pnlSendEmail.el.mask(TocLanguage.formSubmitWaitMsg, 'x-mask-loading');
    
    Ext.Ajax.request({
      url: '${ctx}/admin/newsletters/get-emails-confirmation',
      params: {
        newsletterId: this.newsletterId,
        batch: batch
      },
      callback: function(options, success, response) {
        var result = Ext.decode(response.responseText);
        
        if (result.success == true) {
          this.pnlSendEmail.removeAll();
          
          this.pnlSendEmail.update(result.confirmation);
          Ext.getCmp('btn-send-emails').setText('<spring:message code="button_send"></spring:message>');
        } else {
          Ext.MessageBox.alert(TocLanguage.msgErrTitle, result.feedback);
        }
        
        this.pnlSendEmail.el.unmask();
      },
      scope: this
    }); 
  },
  
  getAudienceSelectionForm: function() {
    var dsAudience = Ext.create('Ext.data.Store', {
      fields:[
        'id', 
        'text'
      ],
      pageSize: Toc.CONF.GRID_PAGE_SIZE,
      proxy: {
        type: 'ajax',
        url : '${ctx}/admin/newsletters/get-emails-audience',
        reader: {
          type: 'json',
          root: Toc.CONF.JSON_READER_ROOT,
          totalProperty: Toc.CONF.JSON_READER_TOTAL_PROPERTY
        }
      },
      autoLoad: true
    });
    
    var selAudience = Ext.create('Ext.ux.form.MultiSelect', {
      store: dsAudience,
      style: 'padding: 15px;',
      border: false,
      name: 'customers',
      width: 550,
      height: 250,
      legend: '<spring:message code="newsletter_customer"></spring:message>',
      displayField: 'text',
      valueField: 'id'
    });
    
    return selAudience;
  },
  
  buildForm: function() {
    this.selAudience = this.getAudienceSelectionForm();
    
    this.pnlSendEmail = Ext.create('Ext.Panel', {
      border: false,
      bodyPadding: 10,
      items: this.selAudience
    });
    
    return this.pnlSendEmail;
  }
});