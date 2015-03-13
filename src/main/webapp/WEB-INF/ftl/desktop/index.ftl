<#include "common/vars.ftl">
<!DOCTYPE html>
	<#include "common/html.ftl">
    <head>
		<#assign title="学生管理">
		<#assign description="">
    	<#include "common/meta.ftl">
    </head>
    <body>
       	<#include "common/updatebrowser.ftl">
        <!-- BEGIN Container -->
        <div class="container-fluid" id="main-container">
        	<#include "common/top.ftl">
        	<#include "common/sidebar.ftl">
            <!-- BEGIN Content -->
            <div id="main-content">
                <!-- BEGIN Page Title -->
                <div class="page-title">
                    <div>
                        <h1><i class="icon-file-alt"></i>学生管理</h1>
                        <h4>要想查询缴费，总得先添加学生吧</h4>
                    </div>
                </div>
                <!-- END Page Title -->

                <!-- BEGIN Breadcrumb -->
                <div id="breadcrumbs">
                    <ul class="breadcrumb">
                        <li class="active">
                            <i class="icon-home"></i>
                           学生管理
                        </li>
                    </ul>
                </div>
                <!-- END Breadcrumb -->

                <!-- BEGIN Main Content -->
                <div class="row-fluid">
                    <div class="span12">
                        <div class="box">
                            <div class="box-title">
                                <h3><i class="icon-table"></i>学生列表</h3>
                                <div class="box-tool">
                                    <a data-action="collapse" href="#"><i class="icon-chevron-up"></i></a>
                                </div>
                            </div>
                            <div class="box-content">
                                <div class="btn-toolbar">
                                    <a href='${ctx}/action/student/add' class="btn btn-primary"><i class="icon-cog"></i> 添加学生</a>
                                    <button class="btn btn-info"><i class="icon-cog"></i> 搜索</button>
                                    <button class="btn btn-danger"><i class="icon-cog"></i> 删除</button>
                                </div>
                                <table class="table table-striped table-hover fill-head">
                                    <thead>
                                    <tr>
                                        <th style="width: 18px"><input type="checkbox" /></th>
                                        <th>姓名</th>
                                        <th>年龄</th>
                                        <th>性别</th>
                                        <th>父亲</th>
                                        <th>母亲</th>
                                        <th>班级</th>
                                        <th>学校</th>
                                        <th>家长电话</th>
                                        <th style="width: 250px">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if result??>
                                    	<#list result.content as data>
                                    		<tr>
		                                        <td><input type="checkbox" name="ids" value="${data.id}" /></td>
		                                        <td>${data.name}</td>
		                                        <td>${data.age}</td>
		                                        <td><#if data.sex==0>男<#else>女</#if></td>
		                                        <td>${data.fatherName}</td>
		                                        <td>${data.motherName}</td>
		                                        <td>${data.klass}</td>
		                                        <td>${data.school}</td>
		                                        <td>${data.phone}</td>
		                                        <td>
		                                            <a class="btn btn-primary btn-small"  href="#"><i class="icon-edit"></i> 编辑</a>
		                                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 删除</a>
		                                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 缴费</a>
		                                        </td>
		                                    </tr>
                                    	</#list>
                                    </#if>
                                    
                                    <!--<tr>
                                        <td><input type="checkbox" value="1" /></td>
                                        <td>Mark</td>
                                        <td>Otto<span class="label label-info pull-right"><i class="icon-twitter"></i> New Twitte</span></td>
                                        <td>@mdo<span class="badge badge-info pull-right">+10</span></td>
                                        <td>
                                            <a class="btn btn-primary btn-small"  href="#"><i class="icon-edit"></i> 编辑</a>
                                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 删除</a>
                                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 缴费</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="checkbox" value="2" /></td>
                                        <td>Jacob</td>
                                        <td>Thornton</td>
                                        <td>@fat</td>
                                        <td>
                                            <a class="btn btn-primary btn-small" href="#"><i class="icon-edit"></i> 编辑</a>
                                            <a class="btn btn-danger btn-small" href="#"><i class="icon-trash"></i> 删除</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="checkbox" value="3" /></td>
                                        <td>Larry <span class="label label-success label-small pull-right">New User</span></td>
                                        <td>the Bird</td>
                                        <td><a href="#">@twitter</a></td>
                                        <td>
                                            <a class="btn btn-primary btn-small" href="#"><i class="icon-edit"></i> 编辑</a>
                                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 删除</a>
                                        </td>
                                    </tr>-->
                                    </tbody>
                                </table>
                                <div class="pagination text-center">
                                    <ul>
                                        <li><a href="#">← 上一页</a></li>
                                        <li><a href="#">1</a></li>
                                        <li><a href="#">2</a></li>
                                        <li class="active"><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">下一页 → </a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- END Main Content -->
                
                <footer>
                    <p>2013 © FLATY Admin Template.</p>
                </footer>

                <a id="btn-scrollup" class="btn btn-circle btn-large" href="#"><i class="icon-chevron-up"></i></a>
            </div>
            <!-- END Content -->
        </div>
        <!-- END Container -->
        <!--basic scripts-->
        <!--<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>-->
        <script>window.jQuery || document.write('<script src="${ctx}/assets/jquery/jquery-1.10.1.min.js"><\/script>')</script>
        <script src="${ctx}/assets/bootstrap/bootstrap.min.js"></script>
        <script src="${ctx}/assets/nicescroll/jquery.nicescroll.min.js"></script>

        <!--page specific plugin scripts-->


        <!--flaty scripts-->
        <script src="${ctx}/js/flaty.js"></script>

    </body>
</html>
