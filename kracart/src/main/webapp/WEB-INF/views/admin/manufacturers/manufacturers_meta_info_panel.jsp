<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.manufacturers.MetaInfoPanel', {
  extend: 'Ext.tab.Panel',
  
  constructor: function(config) {
    config = config || {};
    
    config.title = '<spring:message code="section_meta"></spring:message>';
    config.activeTab = 0;
    config.deferredRender = false;
    config.border = false;
    
    config.items = this.buildForm();
    
    this.callParent([config]);
  },
  
  buildForm: function() {
    var panels = [];
    
    <c:forEach items="${langs}" var="l" varStatus="s">
    	var lang<c:out value="${l.code}" escapeXml="false"></c:out> = Ext.create('Ext.Panel', {
    		title: '<c:out value="${l.name}" escapeXml="false"></c:out>',
    		iconCls: 'icon-<c:out value="${l.countryISO}" escapeXml="false"></c:out>-win',
    		layout: 'anchor',
            border: false,
            bodyPadding: 8,
			items: [
	            {xtype: 'textfield', fieldLabel: '<spring:message code="field_page_title"></spring:message>' , name: 'meta[<c:out value="${l.id}" escapeXml="false"></c:out>].pageTitle'},
	            {xtype: 'textarea', fieldLabel: '<spring:message code="field_meta_keywords"></spring:message>', name: 'meta[<c:out value="${l.id}" escapeXml="false"></c:out>].metaKeywords'},
	            {xtype: 'textarea', fieldLabel: '<spring:message code="field_meta_description"></spring:message>', name: 'meta[<c:out value="${l.id}" escapeXml="false"></c:out>].metaDescription'},
	            {
	              xtype: 'textfield',
	              fieldLabel: '<spring:message code="field_manufacturer_url"></spring:message>',
	              labelStyle: '<c:out value="${l.worldFlagUrl}" escapeXml="false"></c:out>',
	              name: 'meta[<c:out value="${l.id}" escapeXml="false"></c:out>].manufacturerFriendlyUrl'
	            }
          	]
    	});
    	panels.push(lang<c:out value="${l.code}" escapeXml="false"></c:out>);
    </c:forEach>
    return panels;
  }
});
