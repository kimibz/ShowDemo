//========== Dayspring 本地化模块 ==========//
;var xigua_local;

if (xigua_local) {
    throw new Error("Dayspring localization file can not be loaded, it already exists.");
} else {
    xigua_local = function() {
        return {
            200: "处理成功。",
            201: "数据已创建成功。",
            202: "正在处理，请稍候。",
            301: "数据已被他人修改。",
            400: "请求无效，请检查输入的数据是否正确。",
            404: "没有找到符合条件的数据。",
            500: "处理失败。",
            503: "服务器当前正忙，无法处理请求。"
        };
    }();
}