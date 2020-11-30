<%@page pageEncoding="UTF-8" isELIgnored="false" %>

    <style>
        th{
            text-align: center;
        }
    </style>
<script>




    $(function () {
        //创建jqGrid  1.初始化参数全部以js对象形式放入jqGri的方法里
        $("#videoId").jqGrid({

            styleUI: "Bootstrap",    //构建一个bootstrap风格表格
            url: "${pageContext.request.contextPath}/video/queryVideo",  //用来发送服务器端地址 //${pageContext.request.contextPath}/user/findAll   要求返回必须是JSON格式
            editurl: "${pageContext.request.contextPath}/video/edit",
            datatype: "json",  //定义服务器端返回数据格式为 json
            colNames: ['编号', '标题','简介', '封面', '视频','上传时间', '类别','用户', '分组'],
            colModel: [
                {name: 'id', align:"center",},
                {name: 'title', align: "center",editable:true},
                {name: 'brief', align: "center",editable:true},
                {name: 'coverPath', align: "center",editable:false,
                    formatter:function(cellvalue, options, rowObject){
                        return "<img src='"+cellvalue+"' width='110px' height='80px'>";
                    }
                },
                {name: 'videoPath', align: "center",editable:true,edittype:"file",
                    formatter:function(cellvalue, options, rowObject){
                        return "<video src='"+cellvalue+"' width='110px' height='80px' controls='controls'>";
                    }
                    },
                {name: 'uploadTime', align: "center"},
                {name: 'categoryId', align: "center",editable:true},
                {name: 'userId', align: "center",editable:true},
                {name: 'groupId',align: "center",editable:true}
            ],
            pager: "#videoPage",//让表格展示分页工具栏 后台项目   select * from t_user limit (当前页-1)*每页显示记录数,每页显示记录数   控制器 当前页(page)  每页显示记录数(rows)
            page: 1,//指定初始页码 当前页默认1
            rowNum: 2,//默认20  每页显示记录数
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

        }).navGrid("#videoPage",
            {add: true, edit: true, del: true, search: true, refresh: true},  //对展示增删改按钮配置
            {
                closeAfterEdit: true,  //关闭面板
                reloadAfterSubmit: true,
            },  //对编辑配置
            {
                closeAfterAdd: true,
                afterSubmit:function (data) {
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/video/uploadvideo",
                        type:"post",
                        data: {"id":data.responseText},
                        fileElementId: "videoPath",
                        success:function () {
                            $("#videoId").trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
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
    <table id="videoId"/>

    <%--分页工具栏--%>
    <div id="videoPage"/>

</div>
