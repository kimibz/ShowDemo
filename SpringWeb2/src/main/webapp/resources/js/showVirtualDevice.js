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
    var oltId = function() {
        var aslocatoin = location.href.split("/");
        return aslocatoin[aslocatoin.length - 1];
    }();
    // =================================== 方法执行 =================================== 
    //设置table标题
    function setTableTitle(){
        document.getElementById('oltId').innerHTML="设备:"+oltId+"的切片管理";
    }
    function setVndMangement(vndName){
        document.getElementById('vndMangement').innerHTML="虚拟切片:"+vndName;
    }
    //给table赋值
    function setTable(oData){
        var hHtml = template("device-template", {list: oData});
        $("#device-placeholder").html(hHtml);
        $("#device a[data-click='get']").bind("click", getVirtualInfo);
        $("#device a[data-click='add']").bind("click", addPonResource);
        $("#device a[data-click='switch']").bind("click", bindSwitchId);
        $("#device a[data-click='delete']").bind("click", bindDeleteId);
        buttonDisable(oData);
        $("#confirmDelete").bind("click", deleteVirturalDevice);
    }
    //当切片状态为RUNNING时，将删除切片按钮禁用
    function buttonDisable(oData){
        for (var i = 0; i < oData.length; i++) {
            if(oData[i].vnd_status == "running"){
                //将删除切片禁用
                var button = oData[i].vnd_name+"Delete";
                $("#"+button).attr("class","btn btn-outline-danger btn-sm disabled");
                //将关闭切片改为运行切片
                var button2 = oData[i].vnd_name+"Switch";
                $("#"+button2).text("关闭切片");
            }
        };
    }
    //给确认框绑值
    function bindDeleteId(){
        var vnd_name = $(this).attr("data-click-data");
        $("#confirmDelete").attr("data-click-data", vnd_name); 
    }
    //给改变状态确认框绑值
    function bindSwitchId(){
        var vnd_name = $(this).attr("data-click-data");
        var title = "确认"+$(this).text()+"?";
        $('#SwitchModalTitle').html(title);
        $("#Switch").attr("data-click-data", vnd_name); 
    }
    //给模态框的interface表格赋值
    function setInterfaceTable(oData){
        var hHtml = template("vDevice-template", {list: oData.assigned_interface});
        $("#vDevice-placeholder").html(hHtml);
        setVndMangement(oData.vndName);
        $("#vDevice a[data-click='delete']").bind("click", bindPONdelete);
        $("#DeletePon").bind("click", deletePON);
        $("#subscribe").content(oData.description);
        $("#status").content(oData.status);
        $("#deploy_mpu").content(oData.deploy_mpu);        
    }
    //绑定删除PON口资源确认框
    function bindPONdelete(){
        var interfaceName = $(this).attr("data-click-data");
        $("#DeletePon").attr("data-click-data", interfaceName);
    }
    //清楚表单
    function clearForm(){
        $("#vnd_name").val("");
        $("#description").val("");
        document.getElementById("addResource").options.selectedIndex = -1; //回到空选择状态
        $("#addResource").selectpicker('refresh');
        document.getElementById("depoly_mpu").options.selectedIndex = -1; //回到空选择状态
        $("#depoly_mpu").selectpicker('refresh');
        document.getElementById("assign_interface").options.selectedIndex = -1; //回到空选择状态
        $("#assign_interface").selectpicker('refresh');
        document.getElementById("belongTo").options.selectedIndex = -1; //回到空选择状态
        $("#belongTo").selectpicker('refresh');
        $("#vnd_status").attr("checked",false);
    }
    //TABLE中的"添加资源按钮"
    function addPonResource(){
        clearForm();
        var vndName_modal = $(this).attr("data-click-data");
        $('#vndName_modal').content(vndName_modal);
    }

    //新建虚拟设备按钮按下的功能
    $("#spawnNewVirtual").click(function() {
        clearForm();
        $("#oltId_F").content(oltId);
    });
    //添加资源模态框添加键按下
    $("#addPon").click(function() {
        var vndName = $('#vndName_modal').text();
        var assign_interface =$("#addResource").val();
        var oAjaxOption = {
                type: "put",
                url: sContextPath + "/rest/"+oltId+"/addVndResource/"+ vndName +".json",
                contentType: "application/json",
                dataType: "text",
                data: JSON.stringify(assign_interface),
                success: function(oData, oStatus) {
                    initPage();
                    },
                error: function(oData, oStatus, eErrorThrow) {
                    util.handleAjaxError(oData, oStatus, eErrorThrow);
                    },
                complete: function (oXmlHttpRequest, oStatus) {
                    initPage();
                    $.unblockUI();
                    }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    });
    //显示可选用PON资源列表并复制到选项中 
    function setSelectValue(oData){
        $('#addResource').empty();//清空OPTION
        $('#assign_interface').empty();//清空OPTION
        $('#depoly_mpu').empty();//清空OPTION
        $('#belongTo').empty();//清空OPTION
        $('#belongTo').append("<option value='' disabled selected>请选择唯一用户</option>");//给用户选择框添加PLACEHOLDER
        var portList = new Array();
        var userList = new Array();
        var cpuList = new Array();
        portList = oData.port;
        userList = oData.user;
        cpuList = oData.cpu;
        for (var x in portList){
            if(portList[x].substring(0,4) == "xgei"){
                var result = "上联口:" + portList[x];
            }
            else{
                var result = "PON口:" + portList[x];
            }
            $('#assign_interface').append("<option value='"+portList[x]+"'>"+result+"</option>");
            $('#addResource').append("<option value='"+portList[x]+"'>"+result+"</option>");
        }
        for (var y in userList){
            $('#belongTo').append("<option value='"+userList[y]+"'>"+userList[y]+"</option>");
        }
        for (var z in cpuList){
            $('#depoly_mpu').append("<option value='"+cpuList[z]+"'>"+cpuList[z]+"</option>");
        }
    }
    //获取当前OLT可用PON口资源以及所属OLT的Id并赋值到FORM当中
    function getAvailableResource(){
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/OLTResource/"+oltId+".json",
                contentType: "application/text",
                dataType: "text",
                success: function(oData, oStatus) {
                    setSelectValue(JSON.parse(oData));
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
    
    //新增切片确认键按下 OK 传值OK
    $("#confirmSpawnVir").click(function() {
        var data = $("form").form2object();
        var assign_interface =$("#assign_interface").val();
        var depoly_mpu = $("#depoly_mpu").val();
        data.assign_interface = assign_interface;
        data.depoly_mpu = depoly_mpu;
        var oAjaxOption = {
                type: "post",
                url: sContextPath + "/rest/"+oltId+"/spawnNewVirtualDevice.json",
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
                    initPage();
                    $.unblockUI();
                    }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    });
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
  //获取该olt下切片的ID,NAME和STATUS列表
    function getVirtualList(){
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/showVirtualDeviceList/"+oltId+".json",
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
    //确认框确认删除切片
    function deleteVirturalDevice(){
        var vnd_name = $(this).attr("data-click-data");
        var oAjaxOption = {
                type: "delete",
                url: sContextPath + "/rest/"+oltId+"/VirtualInfo/"+vnd_name+".json",
                contentType: "application/json",
                dataType: "text",
                success: function(oData, oStatus) {
                    initPage();
                    $.unblockUI();
                },
                error: function(oData, oStatus, eErrorThrow) {
                    util.handleAjaxError(oData, oStatus, eErrorThrow);
                    $.unblockUI();
                },
                complete: function (oXmlHttpRequest, oStatus) {
                    initPage();
                    $.unblockUI();
                }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    }
    //确认删除PON资源 可刷新模态框table
    function deletePON(){
        //PON名称
        var interfaceName = $(this).attr("data-click-data");
        var pon = interfaceName.replaceAll("/","%2F");
        //VND名称
        var NN = document.getElementById('vndMangement').innerHTML;
        var vndName = NN.substring(5);
        var oAjaxOption = {
                type: "delete",
                url: sContextPath + "/rest/"+oltId+"/"+vndName+"/ponInVirtual.json",
                contentType: "application/json",
                data:JSON.stringify(pon),
                dataType: "text",
                success: function(oData, oStatus) {
                    $.unblockUI();
                },
                error: function(oData, oStatus, eErrorThrow) {
                    util.handleAjaxError(oData, oStatus, eErrorThrow);
                },
                complete: function (oXmlHttpRequest, oStatus) {
                    refreshVirtualInfo();
                }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    }
    String.prototype.replaceAll = function(s1,s2){ 
        return this.replace(new RegExp(s1,"gm"),s2); 
        }
    //画面初始化
    function initPage(){
        setTableTitle();
        getAvailableResource();
        getVirtualList();
    }
    
    
    
    // =================================== 页面加载 =================================== 
    
    initPage();

});