<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- 加载一下errors.tag标签所在的tags文件夹 -->  
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
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
    <!-- CSS -->
    
    <!-- Icons -->
    <spring:url value="/resources/css/font-awesome.min.css" var="font_awesome"/>
    <link href="${font_awesome}" rel="stylesheet" type="text/css"  media="screen, projection" />
    
    <spring:url value="/resources/css/simple-line-icons.css" var="simple_line_icons"/>
    <link href="${simple_line_icons}" rel="stylesheet" type="text/css" media="screen, projection"/>
    
    <!-- Main styles for this application -->
    
    <spring:url value="/resources/css/style.css" var="style"/>
    <link href="${style}" rel="stylesheet" type="text/css" media="screen, projection"/>
    
    <spring:url value="/resources/plugin/gritter/css/jquery.gritter.css" var="gritter"/>
    <link href="${gritter}" rel="stylesheet" type="text/css" media="screen, projection"/>
    
     

    
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
                    <img src=<c:url value="/resources/img/avatars/6.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                    <span id = "username" class="hidden-md-down"><shiro:principal/></span>
                </a>
                <div class="dropdown-menu dropdown-menu-right">

                    <div class="dropdown-header text-center">
                        <strong>Account</strong>
                    </div>

                    <a class="dropdown-item" href="#"><i class="fa fa-bell-o"></i> Updates<span class="badge badge-info">42</span></a>
                    <a class="dropdown-item" href="#"><i class="fa fa-envelope-o"></i> Messages<span class="badge badge-success">42</span></a>
                    <a class="dropdown-item" href="#"><i class="fa fa-tasks"></i> Tasks<span class="badge badge-danger">42</span></a>
                    <a class="dropdown-item" href="#"><i class="fa fa-comments"></i> Comments<span class="badge badge-warning">42</span></a>

                    <div class="dropdown-header text-center">
                        <strong>Settings</strong>
                    </div>

                    <a class="dropdown-item" href="#"><i class="fa fa-user"></i> Profile</a>
                    <a class="dropdown-item" href="#"><i class="fa fa-wrench"></i> Settings</a>
                    <a class="dropdown-item" href="#"><i class="fa fa-usd"></i> Payments<span class="badge badge-default">42</span></a>
                    <a class="dropdown-item" href="#"><i class="fa fa-file"></i> Projects<span class="badge badge-primary">42</span></a>
                    <div class="divider"></div>
                    <a class="dropdown-item" href="#"><i class="fa fa-shield"></i> Lock Account</a>
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
                                <a class="nav-link" href="pages-login.html" target="_top"><i class="icon-star"></i> Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="pages-register.html" target="_top"><i class="icon-star"></i> Register</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="pages-404.html" target="_top"><i class="icon-star"></i> Error 404</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="pages-500.html" target="_top"><i class="icon-star"></i> Error 500</a>
                            </li>
                        </ul>
                    </li>

                </ul>
            </nav>
        </div>

        <!-- Main content 地址栏-->
        
        <main class="main">
            <div id="messagebar-placeholder"></div>
            <!-- Breadcrumb -->
            <ol class="breadcrumb">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item"><a href="#">Admin</a>
                </li>
                <li class="breadcrumb-item active">Dashboard</li>

                <!-- Breadcrumb Menu-->
                <li class="breadcrumb-menu hidden-md-down">
                    <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
                        <a class="btn btn-secondary" href="#"><i class="icon-speech"></i></a>
                        <a class="btn btn-secondary" href="./"><i class="icon-graph"></i> &nbsp;Dashboard</a>
                        <a class="btn btn-secondary" href="#"><i class="icon-settings"></i> &nbsp;Settings</a>
                    </div>
                </li>
            </ol>

            <!-- 网页主体Begin -->
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-header">
                                <i class="fa fa-align-justify"></i> 实体OLT信息
                            </div>
                            <div class="card-block">
                                <button type="button" id="addNewDevice" name="addNewDevice" class="btn btn-outline-primary" data-toggle="modal" data-target="#spawnModal">新增设备连接</button>
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
                <div class="animated fadeIn">
                    <div class="row">
                        <div class=" col-4 ">
                            <div class="card">
                                <div class="card-header">
                                    Line Chart
                                    <div class="card-actions">
                                        <a href="http://www.chartjs.org">
                                            <small class="text-muted">docs</small>
                                        </a>
                                    </div>
                                </div>
                                <div class="card-block">
                                    <div class="chart-wrapper">
                                        <canvas id="myChart" width="400" height="400"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=" col-4 ">
                            <div class="card">
                                <div class="card-header">
                                     Polar Area Chart
                                    <div class="card-actions">
                                        <a href="http://www.chartjs.org">
                                            <small class="text-muted">docs</small>
                                        </a>
                                    </div>
                                </div>
                                <div class="card-block">
                                    <div class="chart-wrapper">
                                     <canvas id="myChart2"></canvas>
                                    </div>
                              </div>
                            </div>
                        </div>
                        <div class=" col-4 ">
                            <div class="card">
                                <div class="card-header">
                                     Pie Chart
                                    <div class="card-actions">
                                        <a href="http://www.chartjs.org">
                                            <small class="text-muted">docs</small>
                                        </a>
                                    </div>
                                </div>
                                <div class="card-block">
                                    <div class="chart-wrapper">
                                     <canvas id="myChart3"></canvas>
                                    </div>
                              </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- begin #message -->
            
            <!-- end #message -->
            <!--Modal Begin --> 
                <div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">修改设备名称</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">X</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <label for="name">设备名称</label>
                                <input onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" class="form-control" id="new_device_id" placeholder="Enter your name">
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
                <div class="modal fade bs-example-modal-lg" id="sliceModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">device info</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">X</span>
                                </button>
                            </div>
                            <div class="modal-body">
<!--                                 <label for="name">设备名称</label> -->
<!--                                 <input onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" class="form-control" id="new_device_id" placeholder="Enter your name"> -->
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label label-wide">hostname: </label>
                                        <label class="col-md-3 form-label label-wide text-info"><span id="hostname"></span><span>&nbsp;</span><span id="limit"></span></label>
                                        <label class="col-md-2 control-label label-wide">location: </label>
                                        <label class="text-info"><span id="location"></span><span>&nbsp;</span><span id="limit"></span></label>
                                     </div>
                                     <div class="form-group">
                                        <label class="col-md-2 control-label label-wide">cpu-isolate: </label>
                                        <label class="col-md-1 form-label label-wide text-info"><span id="cpu-isolate"></span><span>&nbsp;</span><span id="limit"></span></label>
                                        <label class="col-md-2 control-label label-wide">load-mode: </label>
                                        <label class="text-info"><span id="load-mode"></span><span>&nbsp;</span><span id="limit"></span></label>
                                     </div>
                                     <div class="form-group">
                                        <label class="col-md-2 control-label label-wide">contact: </label>
                                        <label class="text-info"><span id="contact"></span><span>&nbsp;</span><span id="limit"></span></label>
                                        
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
                <div class="modal fade bs-example-modal-lg" id="spawnModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">新建设备</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">X</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <!--  begin form -->
                                <form>
                                <div class="card-block">
                                    <div class="form-group">
                                        <label for="company">node-id</label>
                                        <input type="text" class="form-control" id="node_id" name="node_id" placeholder="Enter your node-id">
                                    </div>
                                    <div class="form-group">
                                        <label for="company">host-id</label>
                                        <input type="text" class="form-control" id="host_id" name="host_id" placeholder="Enter your host-id">
                                    </div>
                                    <div class="form-group">
                                        <label for="vat">username</label>
                                        <input type="text" class="form-control" id="username" name="username" placeholder="Enter username">
                                    </div>
                                    <div class="form-group">
                                        <label for="street">password</label>
                                        <input type="text" class="form-control" id="password" name="password" placeholder="Enter password">
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">tcp-only</label>
                                         <div class="col-md-9">
                                            <label class="radio-inline" for="inline-radio1">
                                                <input type="radio" id="tcp" name="tcp" value="true">true
                                            </label>
                                            <label class="radio-inline" for="inline-radio2">
                                                <input type="radio" id="tcp" name="tcp" value="false">false
                                            </label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-sm-8">
                                            <label for="city">host address</label>
                                            <input onkeyup="value=value.replace((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))" type="text" class="form-control" id="address" name="address" placeholder="Enter your host address">
                                        </div>
                                        <div class="form-group col-sm-4">
                                            <label for="postal-code">port</label>
                                            <input type="text" class="form-control" id="port" name="port" placeholder="port">
                                        </div>
                                    </div>
                                    <!--/.row-->
                                    <div class="form-group">
                                        <label for="country">keepalive-delay</label>
                                        <input type="text" class="form-control" id="aliveTime" name="aliveTime" placeholder="Enter your keepalive time">
                                    </div>
                                </div>
                                </form>
                                <!-- end form -->
                            </div>
                            <div class="modal-footer">
                                <a href="javascript:;" class="btn btn-secondary" data-dismiss="modal">取消</a>
                                <a href="javascript:;" id="btn btn-primary" type="submit" class="btn btn-sm btn-primary" data-dismiss="modal" data-click="add" data-action-target="accountInfo">确定</a>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
            <!--Modal End -->
            <!-- 确认框 -->
            <div class="modal fade" id="deleteWarning" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">是否删除</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="button" id="deleteDevice" data-click-data="" data-dismiss="modal" class="btn btn-primary">确认删除</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
                <!-- 确认框结束 -->
                <!-- /.modal -->
            <!-- 主体END -->
        </main>

        <aside class="aside-menu">
            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" data-toggle="tab" href="#timeline" role="tab"><i class="icon-list"></i></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#messages" role="tab"><i class="icon-speech"></i></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#settings" role="tab"><i class="icon-settings"></i></a>
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
                    <div class="callout callout-warning m-0 py-1">
                        <div class="avatar float-right">
                            <img src=<c:url value="/resources/img/avatars/7.jpg" /> alt="admin@bootstrapmaster.com">
                        </div>
                        <div>Meeting with
                            <strong>Lucas</strong>
                        </div>
                        <small class="text-muted mr-1"><i class="icon-calendar"></i>&nbsp; 1 - 3pm</small>
                        <small class="text-muted"><i class="icon-location-pin"></i>&nbsp; Palo Alto, CA</small>
                    </div>
                    <hr class="mx-1 my-0">
                    <div class="callout callout-info m-0 py-1">
                        <div class="avatar float-right">
                            <img src="<c:url value="/resources/img/avatars/4.jpg" />" class="img-avatar" alt="admin@bootstrapmaster.com">
                        </div>
                        <div>Skype with
                            <strong>Megan</strong>
                        </div>
                        <small class="text-muted mr-1"><i class="icon-calendar"></i>&nbsp; 4 - 5pm</small>
                        <small class="text-muted"><i class="icon-social-skype"></i>&nbsp; On-line</small>
                    </div>
                    <hr class="transparent mx-1 my-0">
                    <div class="callout m-0 py-h text-muted text-center bg-faded text-uppercase">
                        <small><b>Tomorrow</b>
                        </small>
                    </div>
                    <hr class="transparent mx-1 my-0">
                    <div class="callout callout-danger m-0 py-1">
                        <div>New UI Project -
                            <strong>deadline</strong>
                        </div>
                        <small class="text-muted mr-1"><i class="icon-calendar"></i>&nbsp; 10 - 11pm</small>
                        <small class="text-muted"><i class="icon-home"></i>&nbsp; creativeLabs HQ</small>
                        <div class="avatars-stack mt-h">
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/2.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/3.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/4.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/5.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/6.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                        </div>
                    </div>
                    <hr class="mx-1 my-0">
                    <div class="callout callout-primary m-0 py-1">
                        <div>
                            <strong>Team meeting</strong>
                        </div>
                        <small class="text-muted mr-1"><i class="icon-calendar"></i>&nbsp; 4 - 6pm</small>
                        <small class="text-muted"><i class="icon-home"></i>&nbsp; creativeLabs HQ</small>
                        <div class="avatars-stack mt-h">
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/2.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/3.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/4.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/5.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/6.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/7.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                            <div class="avatar avatar-xs">
                                <img src=<c:url value="/resources/img/avatars/8.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                            </div>
                        </div>
                    </div>
                    <hr class="mx-1 my-0">
                </div>
                <div class="tab-pane p-1" id="messages" role="tabpanel">
                    <div class="message">
                        <div class="py-1 pb-3 mr-1 float-left">
                            <div class="avatar">
                                <img src=<c:url value="/resources/img/avatars/7.jpg" /> class="img-avatar" alt="admin@bootstrapmaster.com">
                                <span class="avatar-status badge-success"></span>
                            </div>
                        </div>
                        <div>
                            <small class="text-muted">Lukasz Holeczek</small>
                            <small class="text-muted float-right mt-q">11:11 PM</small>
                        </div>
                        <div class="text-truncate font-weight-bold">xigua</div>
                        <small class="text-muted">test,test</small>
                    </div>
                    <hr>
                </div>
                <!--右边按钮setting伸缩  -->
                <div class="tab-pane p-1" id="settings" role="tabpanel">
                    <h6>Settings</h6>

                    <div class="aside-options">
                        <div class="clearfix mt-2">
                            <small><b>Option 1</b>
                            </small>
                            <label class="switch switch-text switch-pill switch-success switch-sm float-right">
                                <input type="checkbox" class="switch-input" checked="">
                                <span class="switch-label" data-on="On" data-off="Off"></span>
                                <span class="switch-handle"></span>
                            </label>
                        </div>
                        <div>
                            <small class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</small>
                        </div>
                    </div>

                    <hr>
                    <h6>System Utilization</h6>

                    <div class="text-uppercase mb-q mt-2">
                        <small><b>CPU Usage</b>
                        </small>
                    </div>
                    <div class="progress progress-xs">
                        <div class="progress-bar bg-info" role="progressbar" style="width: 25%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <small class="text-muted">348 Processes. 1/4 Cores.</small>

                    <div class="text-uppercase mb-q mt-h">
                        <small><b>Memory Usage</b>
                        </small>
                    </div>
                    <div class="progress progress-xs">
                        <div class="progress-bar bg-warning" role="progressbar" style="width: 70%" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <small class="text-muted">11444GB/16384MB</small>

                    <div class="text-uppercase mb-q mt-h">
                        <small><b>SSD 1 Usage</b>
                        </small>
                    </div>
                    <div class="progress progress-xs">
                        <div class="progress-bar bg-danger" role="progressbar" style="width: 95%" aria-valuenow="95" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <small class="text-muted">243GB/256GB</small>

                    <div class="text-uppercase mb-q mt-h">
                        <small><b>SSD 2 Usage</b>
                        </small>
                    </div>
                    <div class="progress progress-xs">
                        <div class="progress-bar bg-success" role="progressbar" style="width: 10%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <small class="text-muted">25GB/256GB</small>
                </div>
            </div>
        </aside>

    </div>
    <!--页脚显示  -->
    <footer class="app-footer">
        <a href="#">naijgnat</a> © 2017 Xigua
        <span class="float-right">Created by-<a href="http://www.baidu.com/" target="_blank" title="Xigua">Xigua</a>
        </span>
    </footer>
    
<!-- ================== BEGIN TEMPLATE ================== -->
        <script id="device-template" type="text/html" charset="UTF-8">
                                                     <div class="table-responsive">
                                                         <table id="device" class="table table-condensed" cellspacing="0" width="100%">
                                                             <thead>
                                                                 <tr>
                                                                     <td style="width: 10%;">#</td>
                                                                     <td style="width: 30%;">设备名称</td>
                                                                     <td style="width: 30%;"></td>
                                                                     <td style="width: 30%;">设备状态</td>
                                                                 </tr>
                                                             </thead>
                                                             <tbody>
                                                                 {{each list as item,index}}
                                                                 <tr>
                                                                     <td class="email-subject text-ellipsis" title="{{index}}">{{index+1}}</td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.id}}">{{item.id}}</td>
                                                                     <td class="email-select">
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" id="{{item.node_id}}" data-click="edit" data-target="#myModal"  data-click-data="{{item.node_id}}">修改设备名称</a>
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-click="delete" id="{{item.node_id}}" data-target="#deleteWarning" data-click-data="{{item.node_id}}">删除设备</a>
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-click="get" id="{{item.node_id}}" data-target="#sliceModal"  data-click-data="{{item.node_id}}">设备信息</a>
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-click="getInfo" id="{{item.node_id}}" data-click-data="{{item.node_id}}">切片信息</a>
                                                                     </td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.state}}">{{item.state}}</td>
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
    <script type="text/javascript" src="<c:url value="/resources/plugin/gritter/js/jquery.gritter.js" />"></script>

    <!-- Plugins and scripts required by all views -->
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script> -->
<!--     <script type="text/javascript" src="<c:url value="resources/plugin/chart/chart.js" />"></script>           -->
    <script type="text/javascript" src="<c:url value="/resources/plugin/chart/Chart.min.js" />"></script>
    <!-- GenesisUI main scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/app.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/util.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/xigua_local.js" />"></script>
    <!-- Plugins and scripts required by this views -->
    <script type="text/javascript" src="<c:url value="/resources/plugin/artTemplate/template.js" />"></script>
    <!-- Custom scripts required by this view -->
    <script type="text/javascript" src="<c:url value="resources/js/views/widgets.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/listDevice.js" />"></script>
    

</body>

</html>