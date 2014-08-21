<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.product_variants.MainPanel', {
  extend: 'Ext.Panel',
  
  constructor: function(config) {
    config = config || {};
    
    config.layout = 'border';
    config.border = false;
    
    config.grdVariantsEntries = Ext.create('Toc.product_variants.ProductVariantsEntriesGrid');
    config.grdVariantsGroups = Ext.create('Toc.product_variants.ProductVariantsGroupsGrid');
    
    config.grdVariantsGroups.on('selectchange', this.onGrdVariantsGroupsSelectChange, this);
    config.grdVariantsGroups.getStore().on('load', this.onGrdVariantsGroupsLoad, this);
    config.grdVariantsEntries.getStore().on('load', this.onGrdVariantsEntriesLoad, this);
    
    config.items = [config.grdVariantsGroups, config.grdVariantsEntries];
    
    this.callParent([config]);    
  },
  
  onGrdVariantsGroupsLoad: function() {
    if (this.grdVariantsGroups.getStore().getCount() > 0) {
      this.grdVariantsGroups.getSelectionModel().select(0);
      var record = this.grdVariantsGroups.getStore().getAt(0);
      
      this.onGrdVariantsGroupsSelectChange(record);
    }else {
      this.grdVariantsEntries.onRefresh();
    }
  },
  
  onGrdVariantsGroupsSelectChange: function(record) {
    this.grdVariantsEntries.setTitle('<spring:message code="heading_product_variants_title"></spring:message>:  '+ record.get('groupsName'));
    this.grdVariantsEntries.iniGrid(record);
  },
  
  onGrdVariantsEntriesLoad: function() {
    var record = this.grdVariantsGroups.getSelectionModel().getLastSelected() || null;
    if (record) {
      record.set('total_entries', this.grdVariantsEntries.getStore().getCount());
      record.commit();
    }
  } 
});
