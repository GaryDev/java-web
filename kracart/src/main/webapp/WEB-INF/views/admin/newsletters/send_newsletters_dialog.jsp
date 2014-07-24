<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.newsletters.SendNewslettersDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
    
    config.id = 'send-newsletters-dialog-win';
    config.title = '<spring:message code="heading_newsletters_title"></spring:message>';
    config.layout = 'fit';
    config.modal = true;
    config.width = 600;
    
    config.items = this.buildForm();
    
    config.buttons = [
      {
        id: 'btn-send-newsletters',
        text: '<spring:message code="button_send"></spring:message>',
        handler: function() { 
          this.sendEmails();
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
    
    this.addEvents({'sendsuccess': true});
    
    this.callParent([config]);
  },
  
  show: function (newslettersId) {
    this.newslettersId = newslettersId;
    
    Ext.Ajax.request({
      url: '${ctx}/admin/newsletters/get-newsletters-confirmation',
      params: {
        newsletters_id: this.newslettersId
      },
      callback: function(options, success, response) {
        var result = Ext.decode(response.responseText);
        
        if (result.success == true) {
          this.frmNewsletter.update(result.confirmation);
          
          if (result.execute == true) {
            Ext.getCmp('btn-send-newsletters').setText('<spring:message code="button_send"></spring:message>');
          } else {
            Ext.getCmp('btn-send-newsletters').hide();
          }
        } else {
          Ext.MessageBox.alert(TocLanguage.msgErrTitle, result.feedback);
        }
        
        this.frmNewsletter.el.unmask();
      },
      scope: this
    }); 
        
    Toc.newsletters.SendNewslettersDialog.superclass.show.call(this);
  },
  
  sendEmails: function() {
    this.frmNewsletter.el.mask('<spring:message code="sending_please_wait"></spring:message>', 'x-mask-loading');
    
    Ext.Ajax.request({
      url: '${ctx}/admin/newsletters/send-newsletters',
      params: {
        newsletters_id: this.newslettersId
      },
      callback: function(options, success, response) {
        var result = Ext.decode(response.responseText);
        
        if (result.success == true) {
         this.fireEvent('sendsuccess', result.feedback);
         this.close();        
        } else {
          Ext.MessageBox.alert(TocLanguage.msgErrTitle, result.feedback);
        }
        
        this.frmNewsletter.el.unmask();
      },
      scope: this
    }); 
  },
  
  buildForm: function() {
    this.frmNewsletter = Ext.create('Ext.Panel', {
      border: false,
      bodyPadding: 10
    });
    
    return this.frmNewsletter;
  }
});