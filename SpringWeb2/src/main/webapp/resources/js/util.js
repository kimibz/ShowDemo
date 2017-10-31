//========== Dayspring 模块 ==========//
;var util;

if (util) {
    throw new Error("Dayspring can not be loaded, it already exists.");
} else {
    util = {};
}
if ("undefined" === typeof jQuery) { 
    throw new Error("util\'s javaScript requires jQuery.");
}

(function(namespace) {
	// 严格模式
    "use strict";
 // 取得ContextPath    
    function getContextPath() {
        var sPathName = document.location.pathname;
        var iIndex = sPathName.substr(1).indexOf("/");
        var sResult = sPathName.substr(0, iIndex+1);
        return sResult;
    }
/// 函数：显示信息（指定位置显示）
    function showMessageAt($element, status) {
        if (status == null || status.statusType == null) {
            status = {
                    statusType: "fail",
                    messages: ["未知错误。"]
            };
        }
        if (status.messages == null || $.isArray(status.messages) == false) {
            status.messages = [];
        }
        var clazz;
        var statusType = status.statusType.toLowerCase();
        if ("success" == statusType) {
            clazz = "alert-success";
        } else if ("info" == statusType) {
            clazz = "alert-info";
        } else if ("warning" == statusType) {
            clazz = "alert-warning";
        } else {
            clazz = "alert-danger";
        }
        var messages = "";
        for (var key in status.messages) {
            messages += (status.messages[key] + "<br>");
        }
        $element.empty().html("<div class='alert fade m-b-0'><button type='button' class='close' data-dismiss='alert'>&times;</button></div>");
        $element.find("button").after(messages);
        $element.find("div").addClass(clazz).addClass("in");
    }

    // 函数：显示信息（默认位置显示）
    function showMessage(status) {

        // gritter存在的场合，使用gritter向用户提示信息；不存在的场合，使用messageBar向用户提示信息
        if ($.gritter != null) {
            notifyMessage(status, false);
        } else {
            // 取得默认信息显示位置
            var $messageBar = $("#messagebar-placeholder");
            showMessageAt($messageBar, status);
        }
    }
 // 函数：显示警告对话框
    function showWarningDialog(message, buttons, callbacks, sender) {
        if (callbacks == null
                || $.isArray(callbacks) == false
                || callbacks.length < 1
                || $.fn.teninedialog == null
                || typeof($.fn.teninedialog) != "function") {
            return;
        }
        if (message == null) {
            message = "您确定要执行此项操作吗？";
        }
        var option = getDialogOption();
        option.content = message;
        if(buttons != null){
            option.otherButtons = buttons.split(",");
        }
        option.clickButton = function(dialogClickBtn,modal,index) {
            if (index == 1) {
                for (var i = 0; i < callbacks.length; i++) {
                    callbacks[i].call(sender);
                }
            }
            $(this).closeDialog(modal);
        };
        $.teninedialog(option);
    }
    //去除json数据里面的空属性
    function deleteEmptyProperty(object){
        for (var i in object) {
            var value = object[i];
            // sodino.com
            // console.log('typeof object[' + i + ']', (typeof value));
            if (typeof value === 'object') {
                if (Array.isArray(value)) {
                    if (value.length == 0) {
                        delete object[i];
                        console.log('delete Array', i);
                        continue;
                    }
                }
                deleteEmptyProperty(value);
                if (isEmpty(value)) {
                    console.log('isEmpty true', i, value);
                    delete object[i];
                    console.log('delete a empty object');
                }
            } else {
                if (value === '' || value === null || value === undefined) {
                    delete object[i];
                    console.log('delete ', i);
                } else {
                    console.log('check ', i, value);
                }
            }
        }
    }
    function isEmpty(object) {
        for (var name in object) {
            return false;
        }
        return true;
    }
    
    // 函数：获取QueryString
    function getQueryString(queryString, key) {

        if (!queryString || queryString == "") {
            queryString = new String(window.location.search);
        }
        if (queryString.indexOf("?") == 0) {
            queryString = queryString.slice(1); //remove "?"
        }

        var pairs = queryString.split("&");
        var result = {};
        pairs.forEach(function(pair) {
            pair = pair.split("=");
            result[pair[0]] = decodeURIComponent(pair[1] || "");
        });

        if (key) {
            return result[key];
        } else {
            return result;
        }
   }
    // 函数：获取blockUI的默认选项
    function getBlockOption() {
        var imgUrl = getContextPath() + "/resources/img/cycle.jpg";
        var option = {
                message: '<img src="' + imgUrl + '" />',
                css: { backgroundColor: "transparent", border: "0px" },
                baseZ: 100000
        };
        return option;
    }
 // 函数：处理Ajax异常
    function handleAjaxError(data, status, errorThrow) {
        var defaultStatus = {
                statusType : "fail",
                messages: ["远程通信失败。"]
        };
        if (data == null || data.status == null) {
            showMessage(defaultStatus);
            return;
        }

        var statusDescribe;
        if (data.status == "500" && data.responseText != null && data.responseText.indexOf("<html>") == -1) {
            statusDescribe = data.responseText;
        } else {
            statusDescribe = xigua_local[data.status];
        }
        if (statusDescribe == null) {
            showMessage(defaultStatus);
            return;
        }
        showMessage({ statusType: "fail", messages: [statusDescribe] });
        if (window.console && window.console.error && data.responseText && data.responseText.lastIndexOf("<pre>") != -1) console.error(data.responseText.substring(data.responseText.lastIndexOf("<pre>") + 5, data.responseText.lastIndexOf("</pre>"))); // 临时处理，以便测试人员能够查看服务端故障，正式发布时需清楚。
    }
 // 函数：页面跳转
    function getView(url) {
        location.href = url;
    }
    //timestamp转String time=1439018115000---> 2015-8-9 8:1:36
    function timeStamp2String (time){
        var datetime = new Date();
         datetime.setTime(time);
         var year = datetime.getFullYear();
         var month = datetime.getMonth() + 1;
         var date = datetime.getDate();
         var hour = datetime.getHours();
         var minute = datetime.getMinutes();
         var second = datetime.getSeconds();
         //var mseconds = datetime.getMilliseconds();
         return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
};
 // 函数：序列化对象转JSON（不支持创建子类对象）
    function serializeArray2obj(serializedParams) {

        var obj = {};
           $.each(serializedParams, function() {
               if ((this.value || "") == "") {
                   return; // 避免服务器端单项目验证（Pattern）无法验证通过
               }
               if (obj[this.name] !== undefined) {
                   if (!obj[this.name].push) {
                       obj[this.name] = [obj[this.name]];
                   }
                   obj[this.name].push(this.value);
               } else {
                   obj[this.name] = this.value;
               }
           });
           return obj;
   };
// 函数：提醒信息
   function notifyMessage(status, sticky) {
       if (status == null || status.statusType == null) {
           status = {
                   statusType: "fail",
                   messages: ["未知错误。"]
           };
       }
       if (status.messages == null || $.isArray(status.messages) == false) {
           status.messages = [];
       }
       if ($.gritter == null) {
           alert(status.messages.join(""));
       }
       var clazz;
       var title;
       var image = getContextPath() + "/resources/img/";
       var statusType = status.statusType.toLowerCase();
       if ("success" == statusType) {
           clazz = "gritter-success";
           title = "信息";
           image += "success.png";
       } else if ("info" == statusType) {
           clazz = "gritter-info";
           title = "信息";
           image += "info.png";
       } else if ("warning" == statusType) {
           clazz = "gritter-warning";
           title = "警告";
           image += "warning.png";
       } else {
           clazz = "gritter-danger";
           title = "错误";
           image += "danger.png";
       }
       var messages = status.messages.join("<br/>");
       $.gritter.add({
           title: title,
           text: messages,
           image: image,
           sticky: sticky,
           time: '',
           class_name: clazz
       });
   }
    namespace.getContextPath = getContextPath;
    namespace.showMessageAt = showMessageAt;
    namespace.showMessage = showMessage;
    namespace.deleteEmptyProperty = deleteEmptyProperty;
    namespace.getQueryString = getQueryString;
    namespace.getBlockOption = getBlockOption;
    namespace.serializeArray2obj = serializeArray2obj;
    namespace.getView = getView;
    namespace.handleAjaxError = handleAjaxError;
    namespace.showWarningDialog = showWarningDialog;
    namespace.notifyMessage = notifyMessage;
    namespace.timeStamp2String = timeStamp2String;
})(util);


//扩展jQuery函数
$.fn.form2object = function() {
    var serializedParams;
    if (this.is("form")) {
        serializedParams = this.serializeArray();
    } else {
        serializedParams = this.find(":input").serializeArray();
    }
    return util.serializeArray2obj(serializedParams);
}
$.fn.form2json = function() {
    var serializedParams = this.serializeArray();
    var obj = util.serializeArray2obj(serializedParams);
    return JSON.stringify(obj);
};
$.fn.container2json = function() {
    var serializedParams = this.find(":input").serializeArray();
    var obj = util.serializeArray2obj(serializedParams);
    return JSON.stringify(obj);
}
$.fn.table2json = function(options) { // 将table内的input（必有有name属性，且非disalbed）和指定属性（通过extraAttributes设定）转换为json字符串 *注意，只有具有id属性的table才能被转换
    if (!this.is("table") && this.find("table").lenght < 1) {
        return "{}";
    }

    var defaults = {
            extraDatas: null, // object || function TODO 暂未实装，用于自定义的其他数据（如表单的部分数据或表单以外的数据）。如希望涵盖表单内其他所有项目，可通过设置includeForm为true实现。
            extraAttributes : null, // array<string> TODO 暂未实装，用于将表格中输入项以外的部分属性也作为数据转换为json。
            includeForm: false // 是否连同表单的其他输入项一起构建成json
    };
    options = $.extend({}, defaults, options);

    function build(table) {
        var rowDatas = [];
        $(table).find("tr").each(function() {
            var serializedParams = $(this).find(":input").serializeArray();
            if (serializedParams == null || serializedParams.length < 1) {
                return;
            }
            var rowData = util.serializeArray2obj(serializedParams);
            rowDatas.push(rowData);
        });
        return rowDatas;
    }

    var output = {};
    var tableName = null;
    if (this.is("table")) {
        tableName = this.attr("id");
        if (tableName != null && tableName != "") {
            output[tableName] = build(this[0]);
        }
    } else {
        this.find("table").each(function() {
            tableName = this.attr("id");
            if (tableName != null && tableName != "") {
                output[tableName] = build(this[0]);
            }
        });
    }
    if (options.includeForm) {
        var formData = $(":not(form table :input):input").serializeArray(); // 表格以外的表单数据
        formData = util.serializeArray2obj(formData);
        $.extend(output, formData);
    }
    return JSON.stringify(output);
}
$.fn.content = function(value) {
    var $el = $(this);
    return value === undefined ? $el.text() : $el.text(value === null ? "" : value);
}
$.fn.clearForm = function() {
    this.find("input").not(":radio").not(":checkbox").val("");
    this.find(":checked").prop("checked", false);
    this.find("textarea, select").val("");
    return this;
}
