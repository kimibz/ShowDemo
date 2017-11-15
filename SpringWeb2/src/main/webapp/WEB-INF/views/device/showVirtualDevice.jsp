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
                                <button type="button" id="spawnNewVirtual" name="spawnNewVirtual"class="btn btn-outline-primary" data-toggle="modal" data-target="#spawnVirtualDevice">新增虚拟设备</button>
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
            <div class="modal fade bs-example-modal-lg" id="spawnVirtualDevice" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">新建虚拟切片</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">X</span>
                                </button>
                            </div>
                            <div class="modal-body">
<!--                                 <label for="name">设备名称</label> -->
<!--                                 <input onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" class="form-control" id="new_device_id" placeholder="Enter your name"> -->
                                    <form>
                                        <div class="form-group row">
                                            <label id="oltId_1" class="col-md-3 form-control-label">所属OLT:</label>
                                            <label class="col-md-1 form-label label-wide text-info"><span id="oltId_F"></span><span>&nbsp;</span><span id="limit"></span></label>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label" for="text-input">切片名称:</label>
                                            <div class="col-md-9">
                                                <input type="text" id="vnd_name" name="vnd_name" class="form-control" onkeyup="this.value=this.value.replace(/[^0-9a-zA-Z]+/g, '');" placeholder="切片名称">
                                                <span class="text-danger small">切片名不能重复</span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label" for="textarea-input">切片描述:</label>
                                            <div class="col-md-9">
                                                <input type="text" id="description" name="description" class="form-control" placeholder="description" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                                                <span class="text-warning small">切片描述</span>
                                            </div>
                                        </div>
                                        </form>
                                        
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label" for="multiple-select">切片处理器选择</label>
                                            <div class="col-md-9">
                                                <select id="depoly_mpu" name="depoly_mpu" class="selectpicker show-tick form-control" multiple data-live-search="false">
                                                    <option value="MPU-1/11/0">MPU-1/11/0</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label" for="multiple-select">切片资源选择</label>
                                            <div class="col-md-9">
                                                <select id="assign_interface" name="assign_interface" class="selectpicker show-tick form-control" multiple data-live-search="false">
                                                    <option value="0" disabled selected>无资源</option>
                                                </select>
                                            </div>
                                        </div>
                                        <form>
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label" for="select">分配给用户</label>
                                            <div class="col-md-9">
                                                <select id="belongTo" name="belongTo" class="selectpicker show-tick form-control">
                                                    <option value="" disabled selected>请选择唯一用户</option>
                                                    <option value="XIGUA">XIGUA</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label">是否启动</label>
                                            <div class="col-md-9">
                                                <label class="radio-inline" for="inline-radio1">
                                                    <input type="radio" id="vnd_status" name="vnd_status" value="enable">是
                                                </label>
                                                <label class="radio-inline" for="inline-radio2">
                                                    <input type="radio" id="vnd_status" name="vnd_status" value="disable">否
                                                </label>
                                            </div>
                                        </div>
                                    </form>
                                    <!-- end of form -->
                            </div>
                            <div class="modal-footer">
                                <a href="javascript:;" class="btn btn-secondary" data-dismiss="modal">取消</a>
                                <a href="javascript:;" id="confirmSpawnVir" name="confirmSpawnVir" class="btn btn-sm btn-primary" data-dismiss="modal" data-click-data="add" data-click="add" data-action-target="accountInfo">确定</a>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal-END -->
                <!-- modal begin -->
            <div class="modal fade bs-example-modal-lg" id="addPonModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">添加端口资源</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">X</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                        <div class="form-group row">
                                            <label id="vndName_title" class="col-md-3 form-control-label">切片名称:</label>
                                            <label class="col-md-1 form-label label-wide text-info"><span id="vndName_modal"></span><span>&nbsp;</span><span id="limit"></span></label>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label" for="multiple-select">端口资源选择:</label>
                                            <div class="col-md-9">
                                                <select id="addResource" name="addResource" class="selectpicker show-tick form-control" multiple data-live-search="false">
                                                    <option value="0" disabled>无资源</option>
                                                </select>
                                            </div>
                                        </div>
                            </div>
                            <div class="modal-footer">
                                <a href="javascript:;" class="btn btn-secondary" data-dismiss="modal">取消</a>
                                <a href="javascript:;" id="addPon" name="addPon" class="btn btn-sm btn-primary" data-dismiss="modal" data-click-data="add" data-click="add" data-action-target="accountInfo">添加</a>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal-END -->
            <!-- modal begin -->
            <div class="modal fade bs-example-modal-lg" id="showDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg modal-info" role="document">
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
                                        <label class="col-md-2 control-label label-wide">描述: </label>
                                        <label class="col-md-3 form-label label-wide text-info"><span id="subscribe"></span><span>&nbsp;</span><span id="limit"></span></label>
                                        <label class="col-md-2 control-label label-wide">状态: </label>
                                        <label class="text-info"><span id="status"></span><span>&nbsp;</span><span id="limit"></span></label>
                                     </div>
                                     <div class="form-group">
                                        <label class="col-md-2 control-label label-wide">部署的MPU: </label>
                                        <label class="col-md-6 form-label label-wide text-info"><span id="deploy_mpu"></span><span>&nbsp;</span><span id="limit"></span></label>
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
                <!-- 切片删除确认框 -->
                <div class="modal fade" id="ConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-danger" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">确认删除该切片？</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="button" id="confirmDelete" data-click-data="" data-dismiss="modal" class="btn btn-primary">确认删除</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
                <!-- 确认框结束 -->
                <!-- PON口资源删除确认框 -->
                <div class="modal fade" id="ConfirmDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-danger" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">确认删除该PON口？</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="button" id="DeletePon" data-click-data="" data-dismiss="modal" class="btn btn-primary">确认删除</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
                <!-- 确认框结束 -->
                <!-- 确认框结束 -->
                <!-- PON口资源删除确认框 -->
                <div class="modal fade" id="ConfirmSwitch" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-danger" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="SwitchModalTitle"></h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="button" id="Switch" data-click-data="" data-dismiss="modal" class="btn btn-primary">确认</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
                <!-- 确认框结束 -->
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
                                                                     <td class="email-subject text-ellipsis" title="{{item.vnd_id}}">{{item.vnd_id}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.vnd_name}}">{{item.vnd_name}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.vnd_status}}">{{item.vnd_status}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.belongTo}}">{{item.belongTo}}</td>
                                                                     <td class="email-select">
                                                                         <a href="javascript:;" class="btn btn-outline-primary btn-sm " data-toggle="modal" data-click="get" id="{{item.vnd_name}}" data-target="#showDetail" data-click-data="{{item.vnd_name}}">切片信息</a>
                                                                         <a href="javascript:;" class="btn btn-outline-primary btn-sm " data-toggle="modal" data-click="switch" id="{{item.vnd_name}}Switch" data-target="#ConfirmSwitch" data-click-data="{{item.vnd_name}}">运行切片</a>
                                                                         <a href="javascript:;" class="btn btn-outline-primary btn-sm "  data-toggle="modal" data-click="add" id="{{item.vnd_name}}" data-target="#addPonModal" data-click-data="{{item.vnd_name}}">添加端口资源</a>
                                                                         <a href="javascript:;" class="btn btn-outline-danger btn-sm "  data-toggle="modal" data-click="delete" id="{{item.vnd_name}}Delete" data-target="#ConfirmModal" data-click-data="{{item.vnd_name}}">删除切片</a>
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
                                                                     <td style="width: 15%;">架位</td>
                                                                     <td style="width: 15%;">槽位</td>
                                                                     <td style="width: 15%;">端口号</td>
                                                                     <td style="width: 15%;">类型</td>
                                                                     <td style="width: 15%;">速率</td>
                                                                     <td style="width: 15%;">操作</td>
                                                                 </tr>
                                                             </thead>
                                                             <tbody>
                                                                 {{each list as item,index}}
                                                                 <tr>
                                                                     <td class="email-subject text-ellipsis" title="{{index}}">{{index+1}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.shelf}}">{{item.shelf}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.slot}}">{{item.slot}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.portNum}}">{{item.portNum}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.type}}">{{item.type}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.speed}}">{{item.speed}}</td>
                                                                     <td class="email-select">
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-target="#ConfirmDelete" data-click="delete" id="{{item.node_id}}"data-click-data="{{item.interfaceName}}">删除</a>   
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
    <script type="text/javascript" src="<c:url value="/resources/js/showVirtualDevice.js" />"></script>

</body>

</html>