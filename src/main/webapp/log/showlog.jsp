<%@page pageEncoding="UTF-8" isELIgnored="false" %>

<style>
    th{
        text-align: center;
    }
</style>
<script>




    $(function () {
        //创建jqGrid  1.初始化参数全部以js对象形式放入jqGri的方法里
        $("#logId").jqGrid({

            styleUI: "Bootstrap",    //构建一个bootstrap风格表格
            url: "${pageContext.request.contextPath}/log/queryAlllog",  //用来发送服务器端地址 //${pageContext.request.contextPath}/user/findAll   要求返回必须是JSON格式
            editurl: "",
            datatype: "json",  //定义服务器端返回数据格式为 json
            colNames: ['ID', 'Name','Time', 'Options', 'status'],
            colModel: [
                {name: 'id', align:"center",},
                {name: 'name', align: "center",editable:true},
                {name: 'times', align: "center",editable:true},
                {name: 'options', align: "center",editable:false,},
                {name: 'status',align: "center",editable:true}
            ],
            pager: "#logPage",//让表格展示分页工具栏 后台项目   select * from t_user limit (当前页-1)*每页显示记录数,每页显示记录数   控制器 当前页(page)  每页显示记录数(rows)
            page: 1,//指定初始页码 当前页默认1
            rowNum: 3,//默认20  每页显示记录数
            rowList: [1, 3, 5],
            viewrecords: true,//显示数据库中总记录数
            // sortname:"bir",//使用那个字段排序  通过后台定义一个参数为sidx的变量接收
            // caption:"用户列表",//用来定义标题
            autowidth: true,//自适应父容器
            cellEdit: true,//列修改功能  必须配合列中editable属性使用  true
            hiddengrid: false,//控制表格默认是否展示  true 不展示   false展示
            hidegrid: true,//用来控制表格设置标题之后出现的控制表格显示和隐藏的按钮是否展示   true显示  false 不显示
            multiselect: true,//开启多行选择   true 显示checkbox
            rownumbers: true,//用来展示表格行号
            // editurl: "${pageContext.request.contextPath}/emp/edit"

        }).navGrid("#logPage",
            {add: true, edit: true, del: true, search: true, refresh: true},  //对展示增删改按钮配置
            {
                closeAfterEdit: true,  //关闭面板
                afterSubmit:function (data) {
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/video/updatealiyun",
                        type:"post",
                        data: {"id":data.responseText},
                        fileElementId: "videoPath",
                        success:function () {
                            $("#videoId").trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },  //对编辑配置
            {
                closeAfterAdd: true,

            },  //对添加配置
            {
                closeAfterDelete: true, //不生效
                reloadAfterSubmit: true
            },  //对删除配置
            {
                sopt: ['cn', 'eq'],//用来展示那些搜索的运算符
            });//对搜索的配置
    });


</script>

<%--设置面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">视频管理</a></li>
    </ul>

</div>
</div>

<%--表单--%>
<table id="logId"/>

<%--分页工具栏--%>
<div id="logPage"/>

</div>
























