<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<c:out value="Ext.namespace('Toc.manufacturers');" escapeXml="false"></c:out>
<c:import url="manufacturers_grid.jsp"></c:import>
<c:import url="manufacturers_dialog.jsp"></c:import>
<c:import url="manufacturers_general_panel.jsp"></c:import>
<c:import url="manufacturers_meta_info_panel.jsp"></c:import>

Ext.override(Toc.desktop.ManufacturersWindow, {

  createWindow : function() {
    var desktop = this.app.getDesktop();
    var win = desktop.getWindow('manufacturers-win');
     
    if (!win) {
      grd = Ext.create('Toc.manufacturers.ManufacturersGrid');
      
      grd.on('create', function() {this.onCreateManufacturer(grd);}, this);
      grd.on('edit', function(record) {this.onEditManufacturer(grd, record);}, this);
      grd.on('notifysuccess', this.onShowNotification, this);
      
      win = desktop.createWindow({
        id: 'manufacturers-win',
        title: '<spring:message code="heading_manufacturers_title"></spring:message>',
        width: 800,
        height: 400,
        iconCls: 'icon-manufacturers-win',
        layout: 'fit',
        items: grd
      });
    }
           
    win.show();
  },
  
  onCreateManufacturer: function(grd) {
    var dlg = this.createManufacturersDialog();
    dlg.setTitle('<spring:message code="heading_new_manufacturers_title"></spring:message>');
    
    this.onSaveSuccess(dlg, grd);
    
    dlg.show();
  },

  createManufacturersDialog: function() {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('manufacturers_dialog-win');
    
    if (!dlg) {
      dlg = desktop.createWindow({}, Toc.manufacturers.ManufacturersDialog);
    }
    
    return dlg;
  },
  
  onEditManufacturer: function(grd, record) {
    var dlg = this.createManufacturersDialog();
    dlg.setTitle(record.get('manufacturerName'));
    
    this.onSaveSuccess(dlg, grd);
    
    dlg.show(record.get('manufacturerId'));
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
