<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.product_variants.ProductVariantsEntriesDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
    
    config.id = 'product_variants_entries-dialog-win';
    config.title = '<spring:message code="action_heading_new_group_entry"></spring:message>';
    config.width = 440;
    config.modal = true;
    config.iconCls = 'icon-product_variants-win';
    
    config.items = this.buildForm();
    
    config.buttons = [
      {
        text:TocLanguage.btnSave,
        handler: function(){
          this.submitForm();
        }, 
        scope: this
      },
      {
        text: TocLanguage.btnClose,
        handler: function(){
          this.close();
        }, 
        scope: this
      }
    ];
    
    this.addEvents({'savesuccess': true});
    
    this.callParent([config]);
  },
  
  show: function (id, valuesId) {
    this.variantsGroupsId = id || null;
    var variantsValuesId = valuesId || null;
    
    this.frmEntry.form.baseParams['groupsId'] = this.variantsGroupsId;
    
    if (variantsValuesId > 0) {
      this.frmEntry.form.baseParams['valuesId'] = variantsValuesId;
      
      this.frmEntry.load({
        url : '${ctx}/admin/ajax/product-variants/load-product-variants-entry',
        success: function (form, action) {
          Toc.product_variants.ProductVariantsEntriesDialog.superclass.show.call(this);
        },
        failure: function (form, action) {
          Ext.MessageBox.alert(TocLanguage.msgErrTitle, action.result.feedback);
        },
        scope: this
      });
    } else {
      this.callParent();
    }
  },
  
  buildForm: function() {
    this.frmEntry = Ext.create('Ext.form.Panel', {
      url : '${ctx}/admin/ajax/product-variants/save-product-variants-entry',
      baseParams: {},
      border: false,
      bodyPadding: 10,
      fieldDefaults: {
        anchor: '97%',
        labelSeparator: ''
      }
    });
    
    <c:forEach items="${langs}" var="l" varStatus="s">
    	var lang<c:out value="${l.id}" escapeXml="false"></c:out> = Ext.create('Ext.form.TextField', {
            name: 'valuesName[<c:out value="${l.id}" escapeXml="false"></c:out>]',
			<c:choose>
    			<c:when test="${s.index == 0}">
    				fieldLabel: '<spring:message code="field_group_entry_name"></spring:message>',
    			</c:when>
    			<c:otherwise>
    				fieldLabel: "&nbsp;",
    			</c:otherwise>
    		</c:choose>
            allowBlank: false,
            labelStyle: '<c:out value="${l.worldFlagUrl}" escapeXml="false"></c:out>'
          });
          this.frmEntry.add(lang<c:out value="${l.id}" escapeXml="false"></c:out>);    
    </c:forEach>
    return this.frmEntry;
  },
  
  submitForm: function() {
    this.frmEntry.form.submit({
      waitMsg: TocLanguage.formSubmitWaitMsg,
      success:function(form, action){
        this.fireEvent('savesuccess', action.result.feedback);
        this.close();
      },    
      failure: function(form, action) {
        if(action.failureType != 'client'){
          Ext.MessageBox.alert(TocLanguage.msgErrTitle, action.result.feedback);
        }
      },
      scope: this
    });   
  }
});
