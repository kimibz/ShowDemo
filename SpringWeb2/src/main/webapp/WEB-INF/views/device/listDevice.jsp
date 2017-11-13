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
                                <a class="nav-link" href="register" target="_top"><i class="icon-star"></i> Register</a>
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
                <!-- 饼图树状图 -->
<!--                 <div class="animated fadeIn"> -->
<!--                     <div class="row"> -->
<!--                         <div class=" col-4 "> -->
<!--                             <div class="card"> -->
<!--                                 <div class="card-header"> -->
<!--                                     Line Chart -->
<!--                                     <div class="card-actions"> -->
<!--                                         <a href="http://www.chartjs.org"> -->
<!--                                             <small class="text-muted">docs</small> -->
<!--                                         </a> -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                                 <div class="card-block"> -->
<!--                                     <div class="chart-wrapper"> -->
<!--                                         <canvas id="myChart" width="400" height="400"></canvas> --%>
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div> -->
<!--                         </div> -->
<!--                         <div class=" col-4 "> -->
<!--                             <div class="card"> -->
<!--                                 <div class="card-header"> -->
<!--                                      Polar Area Chart -->
<!--                                     <div class="card-actions"> -->
<!--                                         <a href="http://www.chartjs.org"> -->
<!--                                             <small class="text-muted">docs</small> -->
<!--                                         </a> -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                                 <div class="card-block"> -->
<!--                                     <div class="chart-wrapper"> -->
<!--                                      <canvas id="myChart2"></canvas> --%>
<!--                                     </div> -->
<!--                               </div> -->
<!--                             </div> -->
<!--                         </div> -->
<!--                         <div class=" col-4 "> -->
<!--                             <div class="card"> -->
<!--                                 <div class="card-header"> -->
<!--                                      Pie Chart -->
<!--                                     <div class="card-actions"> -->
<!--                                         <a href="http://www.chartjs.org"> -->
<!--                                             <small class="text-muted">docs</small> -->
<!--                                         </a> -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                                 <div class="card-block"> -->
<!--                                     <div class="chart-wrapper"> -->
<!--                                      <canvas id="myChart3"></canvas> --%>
<!--                                     </div> -->
<!--                               </div> -->
<!--                             </div> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                 </div> -->
            </div>
            <!-- begin #message -->
            
            <!-- end #message -->
            <!--Modal Begin --> 
                <div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-warning" role="document">
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
                <div class="modal fade bs-example-modal-lg" id="telnetModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">telnet命令</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">X</span>
                                </button>
                            </div>
                            <div class="modal-body">
<!--                                 <label for="name">设备名称</label> -->
<!--                                 <input onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" class="form-control" id="new_device_id" placeholder="Enter your name"> -->
                                
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
                    <div class="modal-dialog modal-danger" role="document">
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
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-click="getInfo" id="{{item.node_id}}" data-click-data="{{item.node_id}}">切片信息</a>
                                                                         <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-click="telnet" id="{{item.node_id}}" data-target="#telnetModal" data-click-data="{{item.node_id}}">telnet接口</a>
                                                                     </td>
                                                                     <td class="email-subject text-ellipsis" title="{{item.state}}">{{item.state}}</td>
                                                                 </tr>
                                                                 {{/each}}
                                                             </tbody>
                                                         </table>
                                                     </div>
        </script>
        <!-- <a href="javascript:;" button class="btn btn-outline-primary btn-sm" data-toggle="modal" data-click="get" id="{{item.node_id}}" data-target="#sliceModal"  data-click-data="{{item.node_id}}">设备信息</a> -->

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