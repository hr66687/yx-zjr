<%@page pageEncoding="UTF-8" isELIgnored="false" %>

    <style>
        th{
            text-align: center;
        }
    </style>
<script>


    // 用户状态
    function update(id, status) {
        let a = confirm("确定要修改");

        //()方法用于显示一个带有指定消息和确认及取消按钮的对话框。
        //如果访问者点击"确定"，此方法返回true，否则返回false。
        if (a == true) {
            $.ajax({
                url: "${pageContext.request.contextPath}/user/status",
                data: {'id': id, 'state': status},
                datatype: "json",
                success: function (res) {
                    //console.log(res);
                    $('#userId').jqGrid('clearGridData');     // 清空表格数据
                    $('#userId').trigger('reloadGrid');   // 重新读取数据 刷新表单
                }
            });
        }
    }


    $(function () {
        //创建jqGrid  1.初始化参数全部以js对象形式放入jqGri的方法里
        $("#userId").jqGrid({

            styleUI: "Bootstrap",    //构建一个bootstrap风格表格
            url: "${pageContext.request.contextPath}/user/queryUsers",  //用来发送服务器端地址 //${pageContext.request.contextPath}/user/findAll   要求返回必须是JSON格式
            datatype: "json",  //定义服务器端返回数据格式为 json
            colNames: ['编号', '头像', '名字', '密码','简介','学分', '状态', '手机号', '注册时间','城市','性别'],
            colModel: [
                {name: 'id', align: "center",editable:false},
                {
                    name: 'head', align: "center",editable:true,edittype:"file",formatter: function (value) {
                        return "<img src='https://hr-999.oss-cn-beijing.aliyuncs.com/" +
                            value + "'style='width:60px;' class='img-circle' />"
                    }
                },
                {name: 'username', align: "center",editable:true},
                {name: 'password', align: "center",editable:true},
                {name: 'brief', align: "center",editable:true},
                {name: 'scroe', align: "center",editable:true},
                {
                    name: 'state', align: "center",
                    // 二次渲染表格
                    formatter: function (value, options, rows) {
                         //console.log(rows.id);   // rows拿到一行数据
                         //console.log(value);   //  获取到指定的指  button
                        //console.log(options);  // 获取后台的格式

                        // let id = rows.id;
                        if (value == '正常') return "<button class='btn btn-success' onclick='update(\"" + rows.id + "\",\"" + value + "\")'>正常</button>";
                        else return "<button class='btn btn-danger' onclick='update(\"" + rows.id + "\",\"" + value + "\")'>冻结</button>";
                        //else return "<button class='btn btn-danger' onclick='update(rows.id+value)'>冻结</button>";

                    }

                },
                {name: 'phone', align: "center",editable:true},
                {name: 'date', editable: false, align: "center"},
                {name: 'city', editable: true, align: "center"},
                {name: 'sex', editable: true, align: "center"}
            ],
            pager: "#userPage",//让表格展示分页工具栏 后台项目   select * from t_user limit (当前页-1)*每页显示记录数,每页显示记录数   控制器 当前页(page)  每页显示记录数(rows)
            page: 1,//指定初始页码 当前页默认1
            rowNum: 4,//默认20  每页显示记录数
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
            editurl: "${pageContext.request.contextPath}/user/edit"

        }).navGrid("#userPage",
            {add: true, edit: true, del: true, search: true, refresh: true},  //对展示增删改按钮配置
            {
                closeAfterEdit: true,  //关闭面板
                reloadAfterSubmit: true,
            },  //对编辑配置
            {
                closeAfterAdd: true,
                afterSubmit:function (data) {
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/user/adduploadUser",
                        type:"post",
                        data: {"id":data.responseText},
                        fileElementId: "head",
                        success:function () {
                            $("#userId").trigger("reloadGrid");
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





    // 阿里云服务发送验证码
    $('#aliyun').click(function () {
        let phone = $('#exampleInputAmount').val();
        $.ajax({
            url:'${pageContext.request.contextPath}/user/aliyun',
        type: 'post',
            data: {"phoneNumbers":phone},
            datatype:"Json",
            success:function (res) {
                alert("ok")
            }

        })
    });




    $('#poi').click(function () {

        $.ajax({
            url:'${pageContext.request.contextPath}/user/easyPoi',
            datatype:"json",
            type:'post',
            success:function (res) {
                console.log(res.message)
                alert(res.message);
            }
        })
    })



</script>





<%--设置面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">用户管理</a></li>
    </ul>

        <div class="form-group">

            <div class="col-md-5" style="margin-top: 5px">
                <button class="btn btn-info" id="poi">导出用户信息</button>
                <button class="btn btn-success">导入用户信息</button>
                <button class="btn btn-warning">测试按钮</button>
            </div>
            <div class="input-group col-md-7" style="margin-top: 18px">

                <input type="text" name="name" class="form-control" id="exampleInputAmount" placeholder="请输入手机号">
                <div class="input-group-addon" id="aliyun"><font color="#6495ed">发送验证码</font></div><br>

            </div>
        </div>









</div>
    </div>

    <%--表单--%>
    <table id="userId"/>

    <%--分页工具栏--%>
    <div id="userPage"/>

</div>
