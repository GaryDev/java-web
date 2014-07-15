<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
/*
 * Desktop configuration
 */

Ext.onReady(function () {
  var TocDesktop;
  
  TocDesktop = new Toc.desktop.App({
    startConfig: {
      title : '<c:out value="${username}" escapeXml="false"></c:out>'
    },
    
    /**
     * Return modules.
     */
    getModules: function() {
      return <c:out value="${modules}" escapeXml="false"></c:out>;
    },
    
    /**
     * Return the launchers object.
     */
    getLaunchers : function(){
      return <c:out value="${launchers}" escapeXml="false"></c:out>;
    },
    
    /**
     * Return the Styles object.
     */
    getStyles : function(){
      return <c:out value="${styles}" escapeXml="false"></c:out>;
    },
    
    onLogout: function() {
      Ext.Ajax.request({
        url: Toc.CONF.CONN_URL,
        params: {
          action: 'logoff',
        },
        callback: function(options, success, response) {
          result = Ext.decode(response.responseText);
          
          if (result.success == true) {
            window.location = "${ctx}/admin/";
          }
        }
      });
    },
    
    onSettings: function() {
      var winSetting = this.getDesktopSettingWindow();
      
      winSetting.show();
    }
  });
});

<c:out value="${output}" escapeXml="false"></c:out>