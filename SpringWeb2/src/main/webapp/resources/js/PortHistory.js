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
    // 取得OLT
    function getOltId(){
        var sPathName = document.location.pathname;
        var iIndex = sPathName.substr(1).lastIndexOf("/");
        var sResult = sPathName.substr(0, iIndex+1);
        var index = sResult.substr(1).lastIndexOf("/");
        var result = sResult.substr(index+2, sResult.length);
        return result;
    }
    // 取得VND
    function getVndName(){
        var sPathName = document.location.pathname;
        var iIndex = sPathName.substr(1).lastIndexOf("/");
        var sResult = sPathName.substr(iIndex+2, sPathName.length);
        return sResult;
    }
    var sContextPath = getContextPath();
    //获取olt的ID
    var oltId = getOltId();
    var vndName = getVndName();
    var username = $("#username").content();
    // =================================== 方法执行 =================================== 
    //设置table标题
    function setTableTitle(){
        document.getElementById('vndName').innerHTML="设备:"+vndName+"的端口历史数据";
    }
    //给table赋值
    function setTable(oData){
        for (var x in oData){
            var strdate2 = util.timeStamp2String(oData[x].time);
            oData[x].time = strdate2;
        }
        var hHtml = template("device-template", {list: oData});
        $("#device-placeholder").html(hHtml);
    }
    
    //获取该用户下切片的ID,NAME和STATUS列表
    function getVirtualList(){
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/history/"+oltId+"/"+ vndName+".json",
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
    //画面初始化
    function initPage(){
        setTableTitle();
        getVirtualList();
    }
    
    
    
    // =================================== 页面加载 =================================== 
    //$("#VLAN").val("222");
    initPage();

});