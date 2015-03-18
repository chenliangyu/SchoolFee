<#include "common/vars.ftl">
<#include "common/page.ftl">
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
				 <div id="modal-2" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
				 <form action="${ctx}/action/student/" method="post" class="form-horizontal form-row-separated search_form">
			        <div class="modal-header">
			            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			            <h3 id="myModalLabel1">搜索选项</h3>
			        </div>
			        <div class="modal-body">
                        <div class="control-group">
                            <label for="keyword" class="control-label">关键词</label>
                            <div class="controls">
                                <input type="text" name="keyword" id="keyword" placeholder="姓名/班级/学校/父亲名/母亲名的关键字" value='${result.student.name}' class="input-large">
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="ageStart" class="control-label">最小年龄</label>
                            <div class="controls">
                                <input type="text" name="ageStart" id="ageStart" placeholder="搜索年龄大于改值的学生" class="input-mini" value='${result.student.age}'>岁
                            </div>
                        </div>
                        <div class="control-group">
                           <label for="ageEnd" class="control-label">最大年龄</label>
                           <div class="controls">
                              <input type="text" name="ageEnd" id="ageEnd" placeholder="搜索年龄小于改值的学生" class="input-mini" value='${result.student.age}'>岁  
                           </div>
                        </div>
                        <div class="control-group">
                              <label class="control-label">性别</label>
                               <div class="controls">
                               	  <label class="radio inline">
                                      <input type="radio" value="0" /> 随便
                                  </label>
                                  <label class="radio inline">
                                      <input type="radio" name="sex" value="0" /> 男孩
                                  </label>
                                  <label class="radio inline">
                                      <input type="radio" name="sex" value="1" /> 女孩
                                  </label>  
                               </div>
                        </div>
			        </div>
			        <div class="modal-footer">
			            <button class="btn" data-dismiss="modal" aria-hidden="true">返回</button>
			            <button type="submit" class="btn btn-primary">搜索</button>
			        </div>
			      </form>
			    </div>
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
                                    <a href="#modal-2" role="button" class="btn btn-info" data-toggle="modal"><i class="icon-cog"></i> 搜索</a>
                                    <button class="btn btn-danger delete_student"><i class="icon-cog"></i> 删除</button>
                                </div>
                                <form action="${ctx}/action/student/delete" method="post" class='delete_form'>
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
		                                        <td><input type="checkbox" name="ids" value="${data.id}" class='student_id' /></td>
		                                        <td>${data.name}</td>
		                                        <td>${data.age}</td>
		                                        <td><#if data.sex==0>男<#else>女</#if></td>
		                                        <td>${data.fatherName}</td>
		                                        <td>${data.motherName}</td>
		                                        <td>${data.klass}</td>
		                                        <td>${data.school}</td>
		                                        <td>${data.phone}</td>
		                                        <td>
		                                            <a class="btn btn-primary btn-small"  href="${ctx}/action/student/modify/${data.id}"><i class="icon-edit"></i> 编辑</a>
		                                            <a class="btn btn-danger btn-small"  href="${ctx}/action/student/delete/${data.id}"><i class="icon-trash"></i> 删除</a>
		                                            <a class="btn btn-danger btn-small"  href="${ctx}/action/payment/student/${data.id}/0"><i class="icon-trash"></i> 缴费</a>
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
                                </form>
                                <#if result??>
	                                <@pagination page=result url=ctx+"/action/student" />
	                                    <!--<li><a href="#">← 上一页</a></li>
	                                    <li><a href="#">1</a></li>
	                                    <li><a href="#">2</a></li>
	                                    <li class="active"><a href="#">3</a></li>
	                                    <li><a href="#">4</a></li>
	                                    <li><a href="#">5</a></li>
	                                    <li><a href="#">6</a></li>
	                                    <li><a href="#">下一页 → </a></li>-->
                                </#if>
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
		<script src="${ctx}/assets/jquery.json.min.js"></script>
        <!--page specific plugin scripts-->


        <!--flaty scripts-->
        <script src="${ctx}/js/flaty.js"></script>
		<script>
			$(".delete_student").click(function(e){
				var ids = []
				$(".student_id:checked").each(function(){
					ids.push($(this).val());
				});
				$.ajax({
					url:"${ctx}/action/student/delete",
					type:"post",
					data : {ids:$.toJSON(ids)},
					dataType:"json"
				}).done(function(data){
					location.reload();
				})				
			})
		</script>
    </body>
</html>
