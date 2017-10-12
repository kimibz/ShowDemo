+$(function () {
    // 严格模式
    "use strict";
    
    // =================================== 变量声明 =================================== //
    // 取得ContextPath    
    function getContextPath() {
        var sPathName = document.location.pathname;
        var iIndex = sPathName.substr(1).indexOf("/");
        var sResult = sPathName.substr(0, iIndex+1);
        return sResult;
    }
    var sContextPath = getContextPath();
    //获取olt的ID
    var oltId = "zte";
    var username = $("#username").content();
    // =================================== 方法执行 =================================== 
    //设置table标题
    function setTableTitle(){
        document.getElementById('oltId').innerHTML="用户:"+username+"的设备管理";
    }
    function setVndMangement(vndName){
        document.getElementById('vndMangement').innerHTML="虚拟切片:"+vndName;
    }
    //给table赋值
    function setTable(oData){
        var hHtml = template("device-template", {list: oData});
        $("#device-placeholder").html(hHtml);
        $("#device a[data-click='get']").bind("click", getVirtualInfo);
    }
    //关闭/运行切片确认键按下
    $("#Switch").click(function() {
          var vnd_name = $(this).attr("data-click-data");
          //关闭值为0 开启为1
          var statusChange = "1";
          var title = $("#SwitchModalTitle").text();
          if($("#SwitchModalTitle").text() == "确认关闭切片?"){
              var statusChange = "0";
          }
          var oAjaxOption = {
                type: "put",
                url: sContextPath + "/rest/"+oltId+"/changeStatus/"+vnd_name+".json",
                contentType: "application/json",
                data: statusChange,
                dataType: "json",
                success: function(oData, oStatus) {
                    getVirtualList();
                },
                error: function(oData, oStatus, eErrorThrow) {
                    util.handleAjaxError(oData, oStatus, eErrorThrow);
                },
                complete: function (oXmlHttpRequest, oStatus) {
                    getVirtualList();
                    $.unblockUI();
                }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    });
    //获取该用户下切片的ID,NAME和STATUS列表
    function getVirtualList(){
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/showDeviceByUser/"+username+".json",
                contentType: "application/json",
                dataType: "text",
                success: function(oData, oStatus) {
                    setTable(JSON.parse(oData));
                },
                error: function(oData, oStatus, eErrorThrow) {
                    util.handleAjaxError(oData, oStatus, eErrorThrow);
                },
                complete: function (oXmlHttpRequest, oStatus) {
                    $.unblockUI();
                }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    }
    //获取该虚拟切片的pon口信息等
    function getVirtualInfo(){
        var vnd_name = $(this).attr("data-click-data");
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/"+oltId+"/VirtualInfo/"+vnd_name+".json",
                contentType: "application/json",
                dataType: "text",
                success: function(oData, oStatus) {
                    setInterfaceTable(JSON.parse(oData));
                },
                error: function(oData, oStatus, eErrorThrow) {
                    util.handleAjaxError(oData, oStatus, eErrorThrow);
                },
                complete: function (oXmlHttpRequest, oStatus) {
                    $.unblockUI();
                }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    }
    //刷新模态框数据
    function refreshVirtualInfo(){
        var title = document.getElementById('vndMangement').innerHTML;
        var vnd_name = title.substring(5);
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/"+oltId+"/VirtualInfo/"+vnd_name+".json",
                contentType: "application/json",
                dataType: "text",
                success: function(oData, oStatus) {
                    setInterfaceTable(JSON.parse(oData));
                },
                error: function(oData, oStatus, eErrorThrow) {
                    util.handleAjaxError(oData, oStatus, eErrorThrow);
                },
                complete: function (oXmlHttpRequest, oStatus) {
                    $.unblockUI();
                }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    }
    //给模态框的interface表格赋值
    function setInterfaceTable(oData){
        var hHtml = template("vDevice-template", {list: oData.assigned_interface});
        $("#vDevice-placeholder").html(hHtml);
        setVndMangement(oData.vndName);
//        $("#vDevice a[data-click='delete']").bind("click", bindPONdelete);
//        $("#DeletePon").bind("click", deletePON);
        $("#subscribe").content(oData.description);
        $("#status").content(oData.status);
        $("#deploy_mpu").content(oData.deploy_mpu);        
    }
    function setVndMangement(vndName){
        document.getElementById('vndMangement').innerHTML="设备:"+vndName;
    }
    String.prototype.replaceAll = function(s1,s2){ 
        return this.replace(new RegExp(s1,"gm"),s2); 
        }
    //画面初始化
    function initPage(){
        setTableTitle();
        getVirtualList();
    }
    
    
    
    // =================================== 页面加载 =================================== 
    //$("#VLAN").val("222");
    initPage();

});