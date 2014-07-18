<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html dir="${lang.textDirection}" xml:lang="${lang.code}" lang="${lang.code}">
  <head>
    <title><spring:message code='administration_title'></spring:message></title>
    <link rel="shortcut icon" href="${ctx}/templates/base/web/images/favicon.ico" type="image/x-icon" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="-1" />
	<link rel="stylesheet" type="text/css" href="${ctx}/templates/base/web/javascript/extjs/resources/css/ext-all.css" />
	<script src="${ctx}/templates/base/web/javascript/extjs/ext-all-debug.js"></script>
	<style type="text/css">
	<c:forEach items="${langs}" var="l">
		<c:out value=".icon-${l.countryISO}-win {background-image: url(${ctx}/assets/images/worldflags/${l.countryISO}.png) !important;}" escapeXml="false"></c:out>
	</c:forEach>
    </style> 
    <decorator:head />
  </head>

  <body scroll="no">
    <decorator:body />
  </body>
</html>