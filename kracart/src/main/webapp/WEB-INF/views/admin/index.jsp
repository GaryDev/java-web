<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" type="text/css" href="${ctx}/templates/base/web/css/desktop.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/templates/base/web/css/action.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/templates/base/web/css/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/templates/base/web/css/icons-shortcuts.css" />

<div id="x-loading-mask" style="width:100%; height:100%; background:#000000; position:absolute; z-index:20000; left:0; top:0;">&#160;</div>
<div id="x-loading-panel" style="position:absolute;left:40%;top:40%;border:1px solid #9c9f9d;padding:2px;background:#d1d8db;width:300px;text-align:center;z-index:20001;">
<div class="x-loading-panel-mask-indicator" style="border:1px solid #c1d1d6;color:#666;background:white;padding:10px;margin:0;padding-left: 20px;height:130px;text-align:left;">
  <img class="x-loading-panel-logo" style="display:block;margin-bottom:15px;" src="${ctx}/templates/base/web/images/tomatocart.jpg" />
  <img src="${ctx}/templates/base/web/images/loading.gif" style="width:16px;height:16px;vertical-align:middle" />&#160;
  <span id="load-status"><spring:message code="init_system"></spring:message></span>
  <div style="font-size:10px; font-weight:normal; margin-top:15px;">Copyright &copy; 2012 TomatoCart Shopping Cart Solution</div>
</div>
</div> 

  <script type="text/javascript">
    Ext.namespace("Toc");
    
    Toc.CONF = {
      TEMPLATE: 'default',
      CONN_URL: '${ctx}/admin/index',
      LOAD_URL: '${ctx}/admin/ajax/index/load-module-view',
      PDF_URL: '${ctx}/pdf',
      GRID_PAGE_SIZE : <c:out value="${pageSize}" escapeXml="false"></c:out>,
      GRID_STEPS : <c:out value="${steps}" escapeXml="false"></c:out>,
      JSON_READER_ROOT: '${jsonReaderRoot}',
      JSON_READER_TOTAL_PROPERTY: '${jsonReaderTotal}'
    };
    
    Toc.Languages = [];
    <c:forEach items="${jsonLang}" var="l">
	<c:out value="Toc.Languages.push('${l}');" escapeXml="false"></c:out>
	</c:forEach>
  
    var TocLanguage = {};
    TocLanguage = <c:out value="${jsonLangDef}" escapeXml="false"></c:out>;
    
    Ext.BLANK_IMAGE_URL = '${ctx}/templates/base/web/images/s.gif';
    
    Ext.Ajax.timeout= 300000; // 300 seconds
    Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
    Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout * 2 });
    Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });
  </script>

  <!-- TOC DESKTOP JS LIBRARY -->
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/core/classes.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/core/TocApp.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/core/TocModule.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/core/TocDesktop.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/settings/backgrounds.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/settings/modules.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/settings/settings.js"></script>
  
  <!-- TOC EXTENSION JS LIBRARY -->
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/Format.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/ColorPicker.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/CheckColumn.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/MultiSelect.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/layout/component/form/MultiSelect.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/portal/PortalPanel.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/portal/Portlet.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/portal/PortalDropZone.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/portal/PortalColumn.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/portal/GridPortlet.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/RowExpander.js"></script>
  <script type="text/javascript" src="${ctx}/templates/base/web/javascript/desktop/ux/notification.js"></script>

   <!-- GNERATING TOC DESKTOP -->
  <script type="text/javascript" src="${ctx}/admin/ajax/index/desktop"></script>