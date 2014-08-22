<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<c:out value="Ext.namespace('Toc.product_variants');" escapeXml="false"></c:out>
<c:import url="product_variants_entries_dialog.jsp"></c:import>
<c:import url="product_variants_entries_grid.jsp"></c:import>
<c:import url="product_variants_groups_dialog.jsp"></c:import>
<c:import url="product_variants_groups_grid.jsp"></c:import>
<c:import url="product_variants_main_panel.jsp"></c:import>

Ext.override(Toc.desktop.ProductVariantsWindow, {
  createWindow: function () {
    var desktop = this.app.getDesktop();
    var win = desktop.getWindow('product_variants-win');
    
    if (!win) {
      var pnl = Ext.create('Toc.product_variants.MainPanel');
      
      pnl.grdVariantsGroups.on('notifysuccess', this.onShowNotification, this);
      pnl.grdVariantsGroups.on('create', function() {this.onCreateVariantsGroups(pnl.grdVariantsGroups);}, this);
      pnl.grdVariantsGroups.on('edit', function(record) {this.onEditVariantsGroups(pnl.grdVariantsGroups, record);}, this);
      
      pnl.grdVariantsEntries.on('notifysuccess', this.onShowNotification, this);
      pnl.grdVariantsEntries.on('create', function() {this.onCreateVariantsEntries(pnl.grdVariantsEntries);}, this);
      pnl.grdVariantsEntries.on('edit', function(record) {this.onEditVariantsEntries(pnl.grdVariantsEntries, record);}, this);
      
      win = desktop.createWindow({
        id: 'product_variants-win',
        title: '<spring:message code="heading_product_variants_title"></spring:message>',
        width: 800,
        height: 400,
        iconCls: 'icon-product_variants-win',
        layout: 'fit',
        items: pnl
      });
    }
    
    win.show();
  },
  
  onCreateVariantsGroups: function(grdGroups) {
    var dlg = this.createProductVariantsGroupsDialog();
    
    this.onSaveSuccess(dlg, grdGroups);
    
    dlg.show();
  },
  
  onCreateVariantsEntries: function(grdEntries) {
    if (grdEntries.variantsGroupsId) {
      var dlg = this.createProductVariantsEntriesDialog();
      
      this.onSaveSuccess(dlg, grdEntries);
      
      dlg.show(grdEntries.variantsGroupsId);
    }else {
      Ext.MessageBox.alert(TocLanguage.msgInfoTitle, TocLanguage.msgMustSelectOne);
    }
  },
  
  onEditVariantsGroups: function(grdGroups, record) {
    var dlg = this.createProductVariantsGroupsDialog();
    dlg.setTitle(record.get('groupsName'));
    
    this.onSaveSuccess(dlg, grdGroups);
    
    dlg.show(record.get('groupsId'));
  },
  
  onEditVariantsEntries: function(grdEntries, record) {
    var variantsValuesId = record.get('valuesId');
    var dlg = this.createProductVariantsEntriesDialog();
    
    dlg.setTitle(grdEntries.variantsGroupsName);
    
    this.onSaveSuccess(dlg, grdEntries);
    
    dlg.show(grdEntries.variantsGroupsId, variantsValuesId);
  },
  
  createProductVariantsGroupsDialog: function () {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('product_variants_groups-dialog-win');
    
    if (!dlg) {
      dlg = desktop.createWindow({}, Toc.product_variants.ProductVariantsGroupsDialog);
    }
    
    return dlg;
  },
  
  createProductVariantsEntriesDialog: function () {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('product_variants_entries-dialog-win');
    
    if (!dlg) {
      dlg = desktop.createWindow({}, Toc.product_variants.ProductVariantsEntriesDialog);
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
