<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String url = request.getRequestURL().toString();
    url = url.substring(0, url.indexOf('/', url.indexOf("//") + 2));
    String context = request.getContextPath();
    url += context;
    application.setAttribute("ctx", url);
%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keyword" content="Bootstrap,Admin,Template,Open,Source,AngularJS,Angular,Angular2,jQuery,CSS,HTML,RWD,Dashboard">

    <spring:url value="/resources/img/favicon.png" var="shortcut_icon"/>
    <link href="${shortcut_icon}" rel="icon"/>
    
    <title>createdBy-西瓜不甜</title>

    <!-- Icons -->
    <spring:url value="/resources/css/font-awesome.min.css" var="font_awesome"/>
    <link href="${font_awesome}" rel="stylesheet" type="text/css" media="screen, projection"/>
    
    <spring:url value="/resources/css/simple-line-icons.css" var="simple_line_icons"/>
    <link href="${simple_line_icons}" rel="stylesheet" type="text/css" media="screen, projection"/>
    
    <spring:url value="/resources/plugin/bootstrap-select-1.12.4/dist/css/bootstrap-select.css" var="select"/>
    <link href="${select}" rel="stylesheet" type="text/css" media="screen, projection"/>
    
    <!-- Main styles for this application -->
    
    <spring:url value="/resources/css/style.css" var="style"/>
    <link href="${style}" rel="stylesheet" type="text/css" media="screen, projection"/>
</head>

<!--顶部菜单  -->
<body class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">
    <header class="app-header navbar">
        <a class="navbar-brand" href="#"></a>
        <ul class="nav navbar-nav hidden-md-down">
            <li class="nav-item">
                <a class="nav-link navbar-toggler sidebar-toggler" href="#">☰</a>
            </li>

            <li class="nav-item px-1">
                <a class="nav-link" href="/demo">主页</a>
            </li>
            <shiro:hasPermission name="delete"> 
            <li class="nav-item px-1">
                <a class="nav-link" href="/demo/listDevice">设备管理</a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="service">
            <li class="nav-item px-1">
                <a class="nav-link" href="/demo/management/showDevice">设备管理</a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="delete">  
            <li class="nav-item px-1">
                <a class="nav-link" href="#">其他</a>
            </li>
            </shiro:hasPermission>
        </ul>
        <ul class="nav navbar-nav ml-auto">
            <li class="nav-item hidden-md-down">
                <a class="nav-link" href="#"><i class="icon-bell"></i><span class="badge badge-pill badge-danger">5</span></a>
            </li>
            <li class="nav-item hidden-md-down">
                <a class="nav-link" href="#"><i class="icon-list"></i></a>
            </li>
            <li class="nav-item hidden-md-down">
                <a class="nav-link" href="#"><i class="icon-location-pin"></i></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle nav-link" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                    <img src=<c:url value="/resources/img/avatars/7.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                    <span id = "username" class="hidden-md-down"><shiro:principal/></span>
                </a>
                <div class="dropdown-menu dropdown-menu-right">

                    <div class="dropdown-header text-center">
                        <strong>Account</strong>
                    </div>

                    <a class="dropdown-item" href="${ctx}/logout"><i class="fa fa-lock"></i> Logout</a>
                </div>
            </li>
            <li class="nav-item hidden-md-down">
                <a class="nav-link navbar-toggler aside-menu-toggler" href="#">☰</a>
            </li>

        </ul>
    </header>

    <div class="app-body">
        <!-- 侧边菜单栏 -->
        <div class="sidebar">
            <nav class="sidebar-nav">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/demo"><i class="icon-speedometer"></i> 主页 </a>
                    </li>
                    <li class="nav-title">
                        Main Services
                    </li>
                    <li class="nav-item nav-dropdown">
                        <a class="nav-link nav-dropdown-toggle" href="#"><i class="icon-puzzle"></i> 服务</a>
                        <ul class="nav-dropdown-items">
                            <li class="nav-item">
                                <a class="nav-link" href="/demo/showTopo"><i class="icon-puzzle"></i> 显示拓扑图</a>
                            </li>
                        </ul>
                    </li>
                    <li class="divider"></li>
                    <li class="nav-title">
                        Extras
                    </li>
                    <li class="nav-item nav-dropdown">
                        <a class="nav-link nav-dropdown-toggle" href="#"><i class="icon-star"></i> Pages</a>
                        <ul class="nav-dropdown-items">
                            <li class="nav-item">
                                <a class="nav-link" href="/demo/register" target="_top"><i class="icon-star"></i> Register</a>
                            </li>
                        </ul>
                    </li>

                </ul>
            </nav>
        </div>

        <!-- Main content 地址栏-->
        
        <main class="main">

            <!-- Breadcrumb -->
            <ol class="breadcrumb">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item"><a href="#">Admin</a>
                </li>
                <li class="breadcrumb-item active">Management</li>

                <!-- Breadcrumb Menu-->
                <li class="breadcrumb-menu hidden-md-down">
                    <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
                        <a class="btn btn-secondary" href="#"><i class="icon-speech"></i></a>
                        <a class="btn btn-secondary" href="#"><i class="icon-graph"></i> &nbsp;Dashboard</a>
                        <a class="btn btn-secondary" href="#"><i class="icon-settings"></i> &nbsp;Settings</a>
                    </div>
                </li>
            </ol>

            <!-- 网页主体 -->
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-header">
                                <i class="fa fa-align-justify"></i>
                                <label id="oltId"></label>
                            </div>
                            <div class="card-block">
                                <div class="panel panel-default">
                                    <div class="panel-body">
                                        <div id="device-placeholder">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- modal begin -->
            <div class="modal fade bs-example-modal-lg" id="showDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <label id="vndMangement"></label>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">X</span>
                                </button>
                            </div>
                            <div class="modal-body">
<!--                                 <label for="name">设备名称</label> -->
<!--                                 <input onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" class="form-control" id="new_device_id" placeholder="Enter your name"> -->
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label label-wide">设备类型: </label>
                                        <label class="col-md-3 form-label label-wide text-info"><span id="device_type"></span><span>&nbsp;</span><span id="limit"></span></label>
                                        <label class="col-md-2 control-label label-wide">设备版本: </label>
                                        <label class="text-info"><span id="status"></span><span>&nbsp;</span><span id="device_version"></span></label>
                                        <input id="source" type="text" value="123" style="display:none">
                                     </div>
                                     <div class="form-group">
                                        <label class="col-md-2 control-label label-wide">系统版本: </label>
                                        <label class="col-md-8 form-label label-wide text-info"><span id="system_version"></span><span>&nbsp;</span><span id="limit"></span></label>
                                     </div> 
                                     <div class="form-group">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <div id="vDevice-placeholder">
                                                </div>
                                            </div>
                                        </div>
                                     </div>
                                 </div>
                            </div>
                            <div class="modal-footer">
                                <a href="javascript:;" class="btn btn-secondary" data-dismiss="modal">取消</a>
                                <a href="javascript:;" id="btn btn-primary" class="btn btn-sm btn-primary" data-dismiss="modal" data-click="add" data-action-target="accountInfo">确定</a>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal-END -->
                <!-- vlan修改 -->
                <div class="modal fade" id="SwitchVlan" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-warning" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">修改VLAN？</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                        
                                <div class="form-group row">
                                    <label class="col-md-3 control-label label-wide">原VLAN: </label>
                                    <label class="col-md-3 form-label label-wide text-info"><span id="vlanId"></span><span>&nbsp;</span><span id="limit"></span></label>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 control-label label-wide">端口模式: </label>
                                    <label class="col-md-2 form-label label-wide text-info"><span id="vlanMode"></span><span>&nbsp;</span><span id="limit"></span></label>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="text-input">添加VLAN:</label>
                                    <div class="col-md-9">
                                        <input type="text" id="VLAN" name="VLAN" class="form-control" onkeyup="this.value=this.value.replace(/[^\d]/g,'');" placeholder="添加的VLAN">
                                        <span class="text-danger small">0-4094</span>
                                    </div>
                                </div>
                                <div class="form-group row" id="choose">
                                    <label class="col-md-3 control-label label-wide">清空VLAN: </label>
                                    <label class="col-md-3 " for="checkbox1">
                                         <input type="checkbox"  class="switch-input" id="ifCleanVlan" name="ifCleanVlan" value="1" >
                                    </label>
                                </div>
                                
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="button" id="editVlanConfirm" data-click-data="" data-dismiss="modal" class="btn btn-primary">确认</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
                <!-- 确认框结束 -->
                <!-- 获取性能统计 -->
                <div class="modal fade" id="Portstatis" tabindex="-2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg modal-primary" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">性能统计</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group row">
                                    <label class="col-md-3 control-label label-wide">八位组发送速率: </label>
                                    <label class="col-md-3 form-label label-wide text-info"><span id="octetTx"></span><span>&nbsp;</span><span id="limit"></span></label>
                                    <label class="col-md-3 control-label label-wide">八位组接受速率: </label>
                                    <label class="col-md-2 form-label label-wide text-info"><span id="octetRx"></span><span>&nbsp;</span><span id="limit"></span></label>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 control-label label-wide">八位组最大发送速率: </label>
                                    <label class="col-md-3 form-label label-wide text-info"><span id="octetTxPeak"></span><span>&nbsp;</span><span id="limit"></span></label>
                                    <label class="col-md-3 control-label label-wide">八位组最大接受速率: </label>
                                    <label class="col-md-2 form-label label-wide text-info"><span id="octetRxPeak"></span><span>&nbsp;</span><span id="limit"></span></label>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 control-label label-wide">包发送速率: </label>
                                    <label class="col-md-3 form-label label-wide text-info"><span id="pktTxRate"></span><span>&nbsp;</span><span id="limit"></span></label>
                                    <label class="col-md-3 control-label label-wide">包接受速率: </label>
                                    <label class="col-md-2 form-label label-wide text-info"><span id="pktRxRate"></span><span>&nbsp;</span><span id="limit"></span></label>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="button" id="editVlanConfirm" data-click-data="" data-dismiss="modal" class="btn btn-primary">确认</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
            <!-- /.conainer-fluid -->
        </main>

        <aside class="aside-menu">
            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" data-toggle="tab" href="#timeline" role="tab"><i class="icon-list"></i></a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane active" id="timeline" role="tabpanel">
                    <div class="callout m-0 py-h text-muted text-center bg-faded text-uppercase">
                        <small><b>Today</b>
                        </small>
                    </div>
                    <hr class="transparent mx-1 my-0">
                    
                    <hr class="mx-1 my-0">
                  
                    <hr class="transparent mx-1 my-0">
                    <hr class="transparent mx-1 my-0">
                    <hr class="mx-1 my-0">
                    
                    <hr class="mx-1 my-0">
                </div>
                
                
        </aside>


    </div>
    <!--页脚显示  -->
    <footer class="app-footer">
        <a href="#">naijgnat</a> © 2017 Xigua
        <span class="float-right">Created by-<a href="https://github.com/kimibz/ShowDemo" target="_blank" title="Xigua">Xigua</a>
        </span>
    </footer>
    
    <!-- ================== BEGIN TEMPLATE ================== -->
        <script id="device-template" type="text/html" charset="UTF-8">
                                                     <div class="table-responsive">
                                                         <table id="device" class="table table-condensed" cellspacing="0" width="100%">
                                                             <thead>
                                                                 <tr>
                                                                     <td style="width: 10%;">ID</td>
                                                                     <td style="width: 30%;">NAME</td>
                                                                     <td style="width: 15%;">设备状态</td>
                                                                     <td style="width: 15%;">所属用户</td>
                                                                     <td style="width: 30%;">操作</td>
                                                                 </tr>
                                                             </thead>
                                                             <tbody>
                                                                 {{each list as item,index}}
                                                                 <tr>
                                                                     <td class="email-subject text-ellipsis" title="{{item.vnd_id}}">{{item.id}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.vnd_name}}">{{item.virtualName}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.vnd_status}}">{{item.status}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.user}}">{{item.user}}</td>
                                                                     <td class="email-select">
                                                                         <a href="javascript:;" class="btn btn-outline-primary btn-sm " data-toggle="modal" data-click="get" id="{{item.oltId}}_{{item.virtualName}}" data-target="#showDetail" data-click-data="{{item.oltId}}_{{item.virtualName}}">切片信息</a>
                                                                         <a href="javascript:;" class="btn btn-outline-primary btn-sm " data-toggle="modal" data-click="go" id="{{item.oltId}}_{{item.virtualName}}H" data-click-data="{{item.oltId}}_{{item.virtualName}}">性能历史数据</a>
                                                                         <a href="javascript:;" class="btn btn-outline-primary btn-sm " data-toggle="modal" data-click="onu" id="{{item.oltId}}_{{item.virtualName}}M" data-click-data="{{item.oltId}}_{{item.virtualName}}">ONU管理</a>
                                                                     </td>
                                                                 </tr>
                                                                 {{/each}}
                                                             </tbody>
                                                         </table>
                                                     </div>
        </script>
        <script id="vDevice-template" type="text/html" charset="UTF-8">
                                                     <div class="table-responsive">
                                                         <table id="vDevice" class="table table-condensed" cellspacing="0" width="100%">
                                                             <thead>
                                                                 <tr>
                                                                     <td style="width: 10%;">ID</td>
                                                                     <td style="width: 10%;">架位</td>
                                                                     <td style="width: 10%;">槽位</td>
                                                                     <td style="width: 10%;">端口号</td>
                                                                     <td style="width: 10%;">类型</td>
                                                                     <td style="width: 15%;">速率</td>
                                                                     <td style="width: 25%;">操作</td>
                                                                 </tr>
                                                             </thead>
                                                             <tbody>
                                                                 {{each list as item,index}}
                                                                 <tr>
                                                                     <td class="email-subject text-ellipsis" title="{{index}}">{{index+1}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.shelf}}">{{item.shelf}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.slot}}">{{item.slot}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.portno}}">{{item.portno}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.porttype}}">{{item.porttype}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.portspeed}}">{{item.portspeed}}</td>
                                                                     <td class="email-select">
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-target="#SwitchVlan" data-click="edit" id="{{item.portname}}"data-click-data="{{item.portname}}">修改VLAN</a>
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-target="#Portstatis" data-click="get" id="{{index+1}}"data-click-data="{{item.portname}}">查看性能</a> 
                                                                     </td>
                                                                 </tr>
                                                                 {{/each}}
                                                             </tbody>
                                                         </table>
                                                     </div>
        </script>

    <!-- ================== END TEMPLATE ================== -->

    <!-- Bootstrap and necessary plugins -->
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/plugin/bootstrap-growl/bootstrap-growl.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/plugin/tether_js/js/tether.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/plugin/bootstrap-3.2.0/js/bootstrap.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/plugin/pace-0.5.6/pace.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/plugin/blockui/jquery.blockUI.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/plugin/bootstrap-select-1.12.4/dist/js/bootstrap-select.min.js" />"></script>
    

    <!-- Plugins and scripts required by all views -->
    <!-- GenesisUI main scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/app.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/xigua_local.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/util.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/plugin/artTemplate/template.js" />"></script>
    <!-- Plugins and scripts required by this views -->

    <!-- Custom scripts required by this view -->
    <script type="text/javascript" src="<c:url value="/resources/js/userManageOlt.js" />"></script>

</body>

</html>