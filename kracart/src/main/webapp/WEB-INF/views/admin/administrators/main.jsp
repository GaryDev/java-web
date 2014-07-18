<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<c:out value="Ext.namespace('Toc.administrators');" escapeXml="false"></c:out>
<c:import url="administrators_grid.jsp"></c:import>
<c:import url="administrators_dialog.jsp"></c:import>

Ext.override(Toc.desktop.AdministratorsWindow, {

  createWindow : function() {
    var desktop = this.app.getDesktop();
    var win = desktop.getWindow('administrators-win');
     
    if (!win) {
      var grd = Ext.create('Toc.administrators.AdministratorsGrid');
      
      grd.on('notifysuccess', this.onShowNotification, this);
      grd.on('create', function() {this.onCreateAdministrators(grd);}, this);
      grd.on('edit', function(record) {this.onEditAdministrators(grd, record);}, this);
      
      win = desktop.createWindow({
        id: 'administrators-win',
        title: '<spring:message code="heading_administrators_title"></spring:message>',
        width: 800,
        height: 400,
        iconCls: 'icon-administrators-win',
        layout: 'fit',
        items: grd
      });
    }
    
    win.show();
  },
  
  onCreateAdministrators: function(grd) {
    var dlg = this.createAdministratorsDialog({});
    
    this.onSaveSuccess(dlg, grd);
    
    dlg.show();
  },
  
  onEditAdministrators: function(grd, record) {
    var dlg = this.createAdministratorsDialog({'aID': record.get('id')});
    dlg.setTitle(record.get('name'));
    
    dlg.pnlAccessTree.getStore().on('load', function() {dlg.pnlAccessTree.expandAll();}, this);
    
    this.onSaveSuccess(dlg, grd);
    
    dlg.show(record.get('id'));
  },
  
  createAdministratorsDialog: function(config) {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('administrators-dialog-win');
    
    if (!dlg) {
      dlg = desktop.createWindow(config, Toc.administrators.AdministratorsDialog);
      
    }
    
    return dlg;
  },
  
  onSaveSuccess: function(dlg, grd) {
    dlg.on('savesuccess', function(feedback) {
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
