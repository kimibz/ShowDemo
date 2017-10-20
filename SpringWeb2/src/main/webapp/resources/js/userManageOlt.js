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
        var index = vnd_name.lastIndexOf("_");
        var nodeId = "vDevice_"+vnd_name.substring(0,index)+"_"+vnd_name.substring(index+1,vnd_name.length);
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/oltUsrManagement/"+ nodeId +".json",
                contentType: "application/json",
                dataType: "text",
                success: function(oData, oStatus) {
                    setInterfaceTable(JSON.parse(oData),vnd_name);
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
    function setInterfaceTable(oData,vnd_name){
        var x;
        var portList = oData.portList;
        //修改TYPE 并且当type=1116时,
        for(x in portList){
            var type = portList[x].porttype;
            if(type=="1116"){
                portList[x].porttype = "PON口";
            }else if(type=="132"){
                portList[x].porttype = "上联口";
            }
        }
        var hHtml = template("vDevice-template", {list: oData.portList});
        $("#vDevice-placeholder").html(hHtml);
        var index = vnd_name.lastIndexOf("_");
        var title = vnd_name.substring(index+1,vnd_name.length)
        setVndMangement(title);
//        $("#vDevice a[data-click='delete']").bind("click", bindPONdelete);
//        $("#DeletePon").bind("click", deletePON);
        $("#device_type").content(oData.device_type);
        $("#device_version").content(oData.device_version);
        $("#system_version").content(oData.system_version);        
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