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
        document.getElementById('oltId').innerHTML="切片:"+vndName+"的ONU管理";
    }
    //给table赋值
    function setTable(oData){
        var portList = oData.portList;
        var x;
        for(x in portList){
            var type = portList[x].porttype;
            if(type=="1116"){
                portList[x].porttype = "PON口";
            }else if(type=="132"){
                portList.splice(x);
            }
        }
        var hHtml = template("vDevice-template", {list: portList});
        $("#vDevice-placeholder").html(hHtml);
        $("#vDevice a[data-click='add']").bind("click", setAddOnuLabel);
        $("#vDevice a[data-click='get']").bind("click", getOnuStatus);
    }
    //给OnuModel table赋值
    function setModelTable(oData){
        var hHtml = template("vOnu-template", {list: oData});
        $("#vOnu-placeholder").html(hHtml);
        $("#vOnu a[data-click='deleteOnu']").bind("click", bindONUdelete);
    }

    function setAddOnuLabel(){
        var port = $(this).attr("data-click-data");
        $("#confirmOnu").attr("data-click-data", port); 
        var slot =port.substring(port.indexOf("/")+1, port.lastIndexOf("/"));
        var portNo = port.substring(port.lastIndexOf("/")+1, port.length); 
        var label ="槽位:"+slot+",端口号:"+portNo;
        $("#ponId").content(label);
    }
    
    //绑定ONU确认删除框
    function bindONUdelete(){
        var interfaceName = $(this).attr("data-click-data");
        $("#confirmDeleteOnu").attr("data-click-data", interfaceName);
    }
    //获取该虚拟切片的pon口信息等
    function getVirtualInfo(){
        var nodeId = "vDevice_"+oltId+"_"+vndName;
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/oltUsrManagement/"+ nodeId +".json",
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
    //获取端口下的ONU信息
    function getOnuStatus(){
        var nodeId = "vDevice_"+oltId+"_"+vndName;
        var interfaceName = $(this).attr("data-click-data");
        var pon = interfaceName.replaceAll("/","_");
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/onuManagement/"+ nodeId +"/"+pon+".json",
                contentType: "application/json",
                dataType: "text",
                success: function(oData, oStatus) {
                    setModelTable(JSON.parse(oData));
//                    console.log(oData);
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
    //新增ONU确认键按下 OK 传值OK
    $("#confirmOnu").click(function() {
        var data = $("form").form2object();
        var ifIndex = $("#confirmOnu").attr("data-click-data").toString();
        data.ifIndex = ifIndex ;
        var nodeId = "vDevice_"+oltId+"_"+vndName;
        var oAjaxOption = {
                type: "post",
                url: sContextPath + "/rest/onuManagement/"+nodeId+".json",
                contentType: "application/json",
                dataType: "text",
                data: JSON.stringify(data),
                success: function(oData, oStatus) {
                    initPage();
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
    });
    //删除ONU
    $("#confirmDeleteOnu").click(function() {
        var ifIndex = $(this).attr("data-click-data");
        var nodeId = "vDevice_"+oltId+"_"+vndName;
        var oAjaxOption = {
                type: "delete",
                url: sContextPath + "/rest/onuManagement/"+nodeId+"/"+ ifIndex +".json",
                contentType: "application/json",
                dataType: "text",
                success: function(oData, oStatus) {
                    getOnuStatus();
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
    });
    $("#onuId").bind('keyup', function () {
        var v = $(this).val();
        if(v > 15){
            $(this).val(15);
        }
    });
    String.prototype.replaceAll = function(s1,s2){ 
        return this.replace(new RegExp(s1,"gm"),s2); 
        }
    //画面初始化
    function initPage(){
        setTableTitle();
        getVirtualInfo();
    }
    
    
    
    // =================================== 页面加载 =================================== 
    initPage();

});