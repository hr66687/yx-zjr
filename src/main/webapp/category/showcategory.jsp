<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>



    <style>
        th{
            text-align: center;
        }
    </style>

<script>

    $(function(){
        pageInit();
    });

    function pageInit(){
        $("#cateTable").jqGrid({
            url : "${path}/category/queryOne",
            datatype : "json",
            rowNum : 3,
            rowList : [ 1, 2, 4, 6 ],
            pager : '#catePage',
            sortname : 'id',
            viewrecords : true,
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            multiselect:true,//开启多行选择
            rownumbers:true,//开启表格行号
            colNames : [ '编号', '名称', '级别'],
            colModel : [
                {name : 'id',align: "center" },
                {name : 'cateName',align: "center",editable:true},
                {name : 'levels',align: "center"},
                /*{name : 'parentId',align: "center"},*/
            ],
            subGrid : true,  //开启子表格
            // subgrid_id:是在创建表数据时创建的div标签的ID
            //row_id是该行的ID
            subGridRowExpanded : function(subgrid_id, row_id) {
                addSubGrid(subgrid_id, row_id);
            },
            editurl:'${path}/category/exit',
        });
        $("#cateTable").jqGrid('navGrid', '#catePage', {add : true,edit : true,del : true},
            {
                closeAfterEdit: true,  //关闭面板
                reloadAfterSubmit: true,
            },  //修改
            {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
            }, //添加
            {
                closeAfterDelete: true, //不生效
                reloadAfterSubmit: true,



                //删除    删除成功之后触发的function,接收删除返回的提示信息,在页面做展示
                afterSubmit:function (data) {
                    alert(data.responseJSON.status);
                    return "true";
                }

            },

        );
    }


    //开启子表格的样式
    function addSubGrid(subgridId, rowId){

        var subgridTableTd= subgridId + "Table";
         console.log(rowId);     // 父类id
        console.log(subgridId);
        var pagerId= subgridId+"Page";


        $("#"+subgridId).html("" +
            "<table id='"+subgridTableTd+"' />" +
            "<div id='"+pagerId+"' />"
        );



        $("#" + subgridTableTd).jqGrid({
            url : "${path}/category/queryTwo?parentid=" + rowId,
            datatype : "json",
            rowNum : 20,

            pager : "#"+pagerId,
            sortname : 'num',
            sortorder : "asc",
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            multiselect:true,//开启多行选择
            rownumbers:true,//开启表格行号
            colNames : [ '编号', '名称', '级别', '父类别ID'],
            colModel : [
                {name : 'id',align: "center" },
                {name : 'cateName',align: "center",editable:true},
                {name : 'levels',align: "center"},
                {name : 'parentId',align: "center"},
            ],
            editurl:'${path}/category/exit?parentId='+rowId,
        });

        $("#" + subgridTableTd).jqGrid('navGrid',"#" + pagerId, {edit : true,add : true,del : true},
            {
                closeAfterEdit: true,  //关闭面板
                reloadAfterSubmit: true,
            },  //修改
            {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
            }, //添加
            {
                closeAfterDelete: true, //不生效
                reloadAfterSubmit: true
            }, //删除    删除成功之后触发的function,接收删除返回的提示信息,在页面做展示

        );
    }


</script>


<%--设置面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">类别管理</a></li>
    </ul>

    <%--表单--%>
    <table id="cateTable" />

    <%--分页工具栏--%>
    <div id="catePage" />

</div>

<%--
    删除要有提示信息
--%>
