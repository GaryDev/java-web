<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

Ext.define('Toc.administrators.AdministratorsDialog', {
  extend: 'Ext.Window',
  
  constructor: function(config) {
    config = config || {};
    
    this.treeLoading = config.treeLoading;
    this.aID = config.aID;
    
    config.id = 'administrators_dialog-win';
    config.title = '<spring:message code="action_heading_new_administrator"></spring:message>';
    config.width = 400;
    config.height = 480;
    config.modal = true;
    config.iconCls = 'icon-administrators-win';
    config.layout = 'fit';
    
    config.items = this.buildForm();
    
    config.buttons = [
      {
        text:TocLanguage.btnSave,
        handler: function(){
          this.submitForm();
        },
        scope:this
      },
      {
        text: TocLanguage.btnClose,
        handler: function(){
          this.close();
        },
        scope:this
      }
    ];
    
    this.addEvents({'savesuccess' : true});
    
    this.callParent([config]);
  },
  
  show: function (administratorsId) {
    var administratorsId = administratorsId || null;
    
    if (administratorsId > 0) {
      this.frmAdministrator.form.baseParams['aID'] = administratorsId;
      
      this.frmAdministrator.load({
        url : '${ctx}/admin/administrators/load-administrator',
        success: function(form, action) {
          Toc.administrators.AdministratorsDialog.superclass.show.call(this);
        },
        failure: function() {
          Ext.Msg.alert(TocLanguage.msgErrTitle, TocLanguage.msgErrLoadData);
        },
        scope: this       
      });
    } else {   
      this.callParent();
    }
  },
  
  buildForm: function() {
    this.frmAdministrator = Ext.create('Ext.form.Panel', {
      url : '${ctx}/admin/administrators/save-administrator',
      baseParams: {},
      border: false,
      layout: 'border',
      fieldDefaults: {
        anchor: '98%',
        labelSeparator: ''
      },
      items: [
        this.getAccessPanel(), 
        this.getAdminPanel()
      ]
    });
    
    return this.frmAdministrator;
  },
  
  getAdminPanel: function() {
    
    this.pnlAdmin = Ext.create('Ext.Panel', {
      region: 'north',
      border: false,
      bodyPadding: 10,
      layout: 'anchor',
      items: [
        {
          xtype: 'textfield', 
          fieldLabel: '<spring:message code="field_username"></spring:message>', 
          name: 'user_name', 
          allowBlank: false
        },
        {
          xtype: 'textfield',
          inputType: 'password',
          fieldLabel: '<spring:message code="field_password"></spring:message>', 
          name: 'user_password', 
          allowBlank: this.aID > 0 ? true : false
        },
        {
          xtype: 'textfield', 
          fieldLabel: '<spring:message code="field_email"></spring:message>', 
          name: 'email_address', 
          allowBlank: false
        }
      ]
    });
    
    return this.pnlAdmin;
  },
  
  getAccessPanel: function() {
    this.chkGlobal = Ext.create('Ext.form.Checkbox', {
      name: 'access_globaladmin', 
      boxLabel: '<spring:message code="global_access"></spring:message>',
      listeners: {
        change: function(chk, checked) {
          if(checked)
            this.refreshTree('on');
          else
            this.refreshTree('off');
        },
        scope: this
      }
    });
    
    var extraParams = {};
    
    if (this.aID > 0)
    {
      extraParams.aID = this.aID;
    }    
   
    var dsAccessTree = Ext.create('Ext.data.TreeStore', {
      proxy: {
        type: 'ajax',
        url : '${ctx}/admin/administrators/get-accesses',
        extraParams: extraParams
      },
      root: {
        id: '0',
        text: '<spring:message code="top_category"></spring:message>',
        leaf: false,
        expandable: true,  
        expanded: true  
      }
    });
    
    this.pnlAccessTree = Ext.create('Ext.tree.TreePanel', {
      name: 'access_modules',
      id: 'access_modules',
      region: 'center',
      store: dsAccessTree,
      bodyPadding: 10,
      rootVisible: false,
      border: false,
      autoScroller: true,
      dockedItems: [{
        xtype: 'toolbar',
        items: [
          this.chkGlobal        
        ]
      }]
    });
    
    return this.pnlAccessTree;
  },
  
  refreshTree: function(param) {
    var proxy = this.pnlAccessTree.getStore().getProxy();
    
    proxy.extraParams['global'] = param;
    
    this.pnlAccessTree.getStore().on('load', function() {
      if (proxy.extraParams['global'] == param)
      {
        this.pnlAccessTree.expandAll();
      }
    }, this);
    
    this.pnlAccessTree.getStore().load();
  },
  
  loadAccessTree: function(administratorsId) {
    this.pnlAccessTree.getStore().on('beforeload', function() {
      var proxy = this.pnlAccessTree.getStore().getProxy();
    
      proxy.extraParams['aID'] = administratorsId;
    }, this);
  },
  
  submitForm : function() {
    var modules = [];
    var checkedRecords = this.pnlAccessTree.getChecked();
    
    if (!Ext.isEmpty(checkedRecords)) {
      Ext.each(checkedRecords, function(record) {
        modules.push(record.get('text'));
      });
    }
    
    this.frmAdministrator.form.submit({
      params: {
        modules: Ext.JSON.encode(modules)
      },
      waitMsg: TocLanguage.formSubmitWaitMsg,
      success: function(form, action) {
         this.fireEvent('savesuccess', action.result.feedback);
         this.close();  
      },    
      failure: function(form, action) {
        if (action.failureType != 'client') {
          Ext.MessageBox.alert(TocLanguage.msgErrTitle, action.result.feedback);
        }
      },  
      scope: this
    });   
  }
});
