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
                                <div class="btn-toolbar pull-right">
                                    <div class="btn-group">
                                        <a class="btn btn-circle show-tooltip" title="添加学生" href="add_student.html"><i class="icon-plus"></i></a>
                                        <a class="btn btn-circle show-tooltip" title="删除选中的学生" href="#"><i class="icon-trash"></i></a>
                                    </div>
                                    <div class="btn-group">
                                        <a class="btn btn-circle show-tooltip" title="打印" href="#"><i class="icon-print"></i></a>
                                        <a class="btn btn-circle show-tooltip" title="导出到pdf" href="#"><i class="icon-file-text-alt"></i></a>
                                        <a class="btn btn-circle show-tooltip" title="导出到excel" href="#"><i class="icon-table"></i></a>
                                    </div>
                                    <div class="btn-group">
                                        <a class="btn btn-circle show-tooltip" title="刷新" href="#"><i class="icon-repeat"></i></a>
                                    </div>
                                </div>
                                <table class="table table-striped table-hover fill-head">
                                    <thead>
                                    <tr>
                                        <th style="width: 18px"><input type="checkbox" /></th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Username</th>
                                        <th style="width: 150px">Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><input type="checkbox" value="1" /></td>
                                        <td>Mark</td>
                                        <td>Otto<span class="label label-info pull-right"><i class="icon-twitter"></i> New Twitte</span></td>
                                        <td>@mdo<span class="badge badge-info pull-right">+10</span></td>
                                        <td>
                                            <a class="btn btn-primary btn-small"  href="#"><i class="icon-edit"></i> 编辑</a>
                                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 删除</a>
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
                                    </tr>
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
