<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<c:out value="Ext.namespace('Toc.newsletters');" escapeXml="false"></c:out>
<c:import url="newsletters_grid.jsp"></c:import>
<c:import url="newsletters_dialog.jsp"></c:import>
<c:import url="send_emails_dialog.jsp"></c:import>
<c:import url="send_newsletters_dialog.jsp"></c:import>
<c:import url="log_dialog.jsp"></c:import>

Ext.override(Toc.desktop.NewslettersWindow, {

  createWindow: function(){
    var desktop = this.app.getDesktop();
    var win = desktop.getWindow('newsletters-win');
     
    if(!win){
      var grd = Ext.create('Toc.newsletters.NewslettersGrid');
      
      this.registGrdEvents(grd);
      
      win = desktop.createWindow({
        id: 'newsletters-win',
        title: '<spring:message code="heading_newsletters_title"></spring:message>',
        width: 800,
        height: 400,
        iconCls: 'icon-newsletters-win',
        layout: 'fit',
        items: grd
      });
    }

    win.show();
  },
  
  registGrdEvents: function(grd) {
    grd.on('notifysuccess', this.onShowNotification, this);
    grd.on('create', function() {this.onCreateNewsletters(grd);}, this);
    grd.on('edit', function(record) {this.onEditNewsletters(grd, record);}, this);
    grd.on('sendemails', function(newsletterId) {this.onSendEmails(grd, newsletterId);}, this);
    grd.on('log', this.onLog, this);
    grd.on('sendnewsletters', function(newsletterId) {this.onSendNewsletters(grd, newsletterId);}, this);
  },
  
  onCreateNewsletters: function(grd) {
    var dlg = this.createNewslettersDialog();
    
    this.onSaveSuccess(dlg, grd);
    
    dlg.show();
  },
  
  onEditNewsletters: function (grd, record) {
    var dlg = this.createNewslettersDialog();
    dlg.setTitle(record.get('title'));
    
    this.onSaveSuccess(dlg, grd);
    
    dlg.show(record.get('newsletterId'));
  },
  
  onSendEmails: function(grd, newsletterId) {
    var dlg = this.createSendEmailsDialog();
    
    this.onSendSuccess(dlg, grd);
    
    dlg.show(newsletterId);
  },
  
  onSendNewsletters: function(grd, newsletterId) {
    var dlg = this.createSendNewslettersDialog();
    
    this.onSendSuccess(dlg, grd);
    
    dlg.show(newsletterId);
  },
  
  onLog: function(record) {
    var dlg = this.createLogDialog();
    
    dlg.show(record.get('newsletterId'));
  },  
  
  createSendEmailsDialog: function() {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('send-emails-dialog-win');
    
    if (!dlg) {
      dlg = desktop.createWindow({}, Toc.newsletters.SendEmailsDialog);
    }
      
    return dlg;
  },
  
  createNewslettersDialog: function() {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('newsletters-dialog-win');
    
    if (!dlg) {
      dlg = desktop.createWindow({}, Toc.newsletters.NewslettersDialog);
    }
      
    return dlg;
  },
  
  createLogDialog: function() {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('log-dialog-win');
    
    if (!dlg) {
      dlg = desktop.createWindow({}, Toc.newsletters.LogDialog);
    }
      
    return dlg;
  },
  
  createSendNewslettersDialog: function() {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('send-newsletters-dialog-win');
    
    if (!dlg) {
      dlg = desktop.createWindow({}, Toc.newsletters.SendNewslettersDialog);
    }
      
    return dlg;
  },
  
  onSaveSuccess: function(dlg, grd) {
    dlg.on('savesuccess', function(feedback) {
      this.onShowNotification(feedback);
      
      grd.onRefresh();
    }, this);
  },
  
  onSendSuccess: function(dlg, grd) {
    dlg.on('sendsuccess', function(feedback) {
      this.onShowNotification(feedback);
      
      grd.onRefresh();
    }, this);
  },
  
  onShowNotification: function(feedback) {
    this.app.showNotification({
      title: TocLanguage.msgSuccessTitle,
      html: feedback
    });
  }
});