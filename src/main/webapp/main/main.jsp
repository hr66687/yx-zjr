<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>

</head>
<body>

<!--头部导航-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">应学后台管理V1.0</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-right">

                <li>

                    <a href="#">欢迎： <font color="#7fff00">${sessionScope.login.username}</font></a></li>
                <li><a href="${path}/admin/exit">安全退出<span class="glyphicon glyphicon-log-out"></span> </a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<!--栅格系统-->
<!--左边手风琴部分-->
<div class="container-fluid">
    <div class="row">
        <!--右侧二级菜单-->
        <div class="col-md-2">
            <div class="panel-group" id="acc">
                <div class="panel panel-info">
                    <div class="panel-heading text-center">
                        <a href="#pn1" class="panel-title" data-toggle="collapse"data-parent="#acc">
                            <span class="glyphicon glyphicon-th-list"> 用户管理</span></a>
                    </div>




                    <%--in<div class="panel-collapse collapse   in text-center" id="pn1">--%>



                    <div class="panel-collapse collapse  text-center" id="pn1">
                        <div class="panel-body">
                            <a href="javascript:$('#mainId').load('${path}/user/showUser.jsp')" class="panel-body-item btn btn-primary" style="width: 70%">用户信息</a><br><br>
                            <a href="javascript:$('#mainId').load('${path}/user/China.jsp')" class="panel-body-item btn btn-primary" style="width: 70%">用户分布</a><br><br>
                            <a href="javascript:$('#mainId').load('${path}/user/Echarts.jsp')" class="panel-body-item btn btn-primary" style="width: 70%">用户统计</a><br>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading text-center">
                            <a href="#pn2" class="panel-title" data-toggle="collapse" data-parent="#acc"><span
                                    class="glyphicon glyphicon-th-large"> 类别管理</span></a>
                        </div>
                        <div class="panel-collapse collapse text-center" id="pn2">
                            <div class="panel-body">

                                <a href="javascript:$('#mainId').load('${path}/category/showcategory.jsp')"
                                   class="panel-body-item btn btn-primary" style="width: 70%">分类展示</a><br>
                                <%--<button class="panel-body-item btn btn-primary" style="width: 70%"></button>--%>
                            </div>
                        </div>
                    </div>


                    <div class="panel panel-default">
                        <div class="panel-heading text-center">
                            <a href="#pn3" class="panel-title" data-toggle="collapse" data-parent="#acc"><span
                                    class="glyphicon glyphicon glyphicon-film"> 视频管理</span></a>
                        </div>
                        <div class="panel-collapse collapse text-center" id="pn3">
                            <div class="panel-body">
                                <a href="javascript:$('#mainId').load('${path}/video/showvideo.jsp')" class="panel-body-item btn btn-primary" style="width: 70%">查看视频信息</a>
                            </div>
                        </div>
                    </div>


                    <div class="panel panel-default">
                        <div class="panel-heading text-center">
                            <a href="#pn4" class="panel-title" data-toggle="collapse" data-parent="#acc">
                                <span class="glyphicon glyphicon glyphicon-comment"> 反馈管理</span></a>
                        </div>
                        <div class="panel-collapse collapse text-center" id="pn4">
                            <div class="panel-body">
                                <a href="#" class="panel-body-item btn btn-primary" style="width: 70%">用户反馈</a>
                            </div>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading text-center">
                            <a href="#pn5" class="panel-title" data-toggle="collapse" data-parent="#acc">
                                <span class="glyphicon glyphicon glyphicon-tags "> 日志管理</span></a>
                        </div>
                        <div class="panel-collapse collapse text-center" id="pn5">
                            <div class="panel-body">
                                <a href="javascript:$('#mainId').load('${path}/log/showlog.jsp')" class="panel-body-item btn btn-primary" style="width: 70%">查看日志</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

                        <!--右边轮播图部分-->
        <div class="col-md-10" id="mainId">
            <!--巨幕开始-->

            <div class="jumbotron">
                <h2 style="text-align: center"><b>欢迎来到应学视频APP后台管理系统</b></h2>
            </div>
            <!--左侧轮播图-->
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="${path}/bootstrap/img/pic1.jpg" alt="..."
                             style="width: 100%;height: 300px">
                        <div class="carousel-caption">
                            <p>wangw</p>
                        </div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic2.jpg" alt="..."
                             style="width: 100%;height: 300px">
                        <div class="carousel-caption">
                            <p>lis</p>
                        </div>
                    </div>

                    <div class="item">
                        <img src="${path}/bootstrap/img/pic3.jpg" alt="..."
                             style="width: 100%;height: 300px">
                        <div class="carousel-caption">
                            <p>zhangs</p>
                        </div>
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button"
                   data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button"
                   data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
</div>

      <!--页脚-->
<div class="panel panel-footer navbar-fixed-bottom" align="center" style="margin-top: 90px">
    <span>@hr66688sina.cn</span>
</div>

</body>
</html>
