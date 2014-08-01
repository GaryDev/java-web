<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.manufacturers.GeneralPanel', {
  extend: 'Ext.Panel',
  
  constructor: function(config) {
    config = config || {};
    
    config.title = '<spring:message code="section_general"></spring:message>';
    config.border = false;
    config.layout = 'anchor';
    config.bodyPadding = 8;
    config.items = this.buildForm();
      
    this.callParent([config]);
  },
  
  buildForm: function() {
    var items = [];
    
    items.push({xtype: 'textfield', fieldLabel: '<spring:message code="field_name"></spring:message>', name: 'manufacturerName', allowBlank: false});
    items.push({xtype: 'panel', id: 'manufactuerer_image_panel', border: false, html: ''});
    items.push({xtype: 'fileuploadfield', fieldLabel: '<spring:message code="field_image"></spring:message>', name: 'manufacturerImage'});
    
    <c:forEach items="${langs}" var="l" varStatus="s">
    	this.lang<c:out value="${l.id}" escapeXml="false"></c:out> = Ext.create('Ext.form.TextField', {
    		name: 'manufacturerUrl',
    		<c:choose>
    			<c:when test="${s.index == 0}">
    				fieldLabel: '<spring:message code="field_url"></spring:message>',
    			</c:when>
    			<c:otherwise>
    				fieldLabel: "&nbsp;",
    			</c:otherwise>
    		</c:choose>
    		labelStyle: '<c:out value="${l.worldFlagUrl}" escapeXml="false"></c:out>',
          	value: 'http://'
    	});
    	items.push(this.lang<c:out value="${l.id}" escapeXml="false"></c:out>);
    </c:forEach>
    return items;
  }
});
