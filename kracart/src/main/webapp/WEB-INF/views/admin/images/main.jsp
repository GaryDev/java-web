<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<c:out value="Ext.namespace('Toc.images');" escapeXml="false"></c:out>
<c:import url="images_grid.jsp"></c:import>
<c:import url="images_resize_dialog.jsp"></c:import>
<c:import url="images_check_dialog.jsp"></c:import>

Ext.override(Toc.desktop.ImagesWindow, {
 
  createWindow: function(){
    var desktop = this.app.getDesktop();
    var win = desktop.getWindow('images-win');
     
    if(!win){
      var grd = Ext.create('Toc.images.ImagesGrid');
      
      grd.on('checkimages', function(record) {this.onCheckImages(record);}, this);
      grd.on('resizeimages', function(record) {this.onResizeImages(record);}, this);
      
      win = desktop.createWindow({
        id: 'images-win',
        title: '<spring:message code="heading_images_title"></spring:message>',
        width: 800,
        height: 400,
        iconCls: 'icon-images-win',
        layout: 'fit',
        items: grd
      });
    }
    
    win.show();
  },
  
  onCheckImages: function(record) {
    var dlg = this.createImagesCheckDialog();
    dlg.setTitle(record.get('module'));
    
    dlg.show();
  },
  
  onResizeImages: function(record) {
    var dlg = this.createImagesResizeDialog();
    dlg.setTitle(record.get('module'));
    
    dlg.show();
  },
    
  createImagesCheckDialog: function() {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('images-check-dialog-win');
    
    if(!dlg){
      dlg = desktop.createWindow({}, Toc.images.ImagesCheckDialog);
    }
    
    return dlg;
  },
  
  createImagesResizeDialog: function() {
    var desktop = this.app.getDesktop();
    var dlg = desktop.getWindow('images-resize-dialog-win');
    
    if(!dlg){
      dlg = desktop.createWindow({}, Toc.images.ImagesResizeDialog);
    }
    
    return dlg;
  }
});
