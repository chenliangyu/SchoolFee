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
                        <h1><i class="icon-file-alt"></i><#if result.student??>修改学生<#else>添加学生</#if></h1>
                        <h4>添加你的学生吧，添加完后就到学生列表里设置缴费项目去</h4>
                    </div>
                </div>
                <!-- END Page Title -->
                <!-- BEGIN Breadcrumb -->
                <div id="breadcrumbs">
                    <ul class="breadcrumb">
                        <li>
                            <i class="icon-home"></i>
                            <a href="index.html">学生管理</a>
                            <span class="divider"><i class="icon-angle-right"></i></span>
                        </li>
                        <li class="active"><#if result.student??>修改学生<#else>添加学生</#if></li>
                    </ul>
                </div>
                <!-- END Breadcrumb -->

                <!-- BEGIN Main Content -->
                 <div class="row-fluid">
                    <div class="span12">
                        <div class="box">
                            <div class="box-title">
                                <h3><i class="icon-reorder"></i><#if result.student??>修改学生<#else>添加学生</#if></h3>
                                <div class="box-tool">
                                    <a data-action="collapse" href="#"><i class="icon-chevron-up"></i></a>
                                </div>
                            </div>
                            <div class="box-content">
                                <form action="${ctx}/action/student/postadd" method="post" class="form-horizontal form-row-separated add_form">
                                <#if result.student??>
                                	<input type='hidden' name="id" value="${result.student.id}" />
                                </#if>
                                    <div class="control-group">
                                        <label for="name" class="control-label">姓名</label>
                                        <div class="controls">
                                            <input type="text" name="name" id="name" placeholder="真实姓名" value='${result.student.name}' class="input-medium">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label for="age" class="control-label">年龄</label>
                                        <div class="controls">
                                            <input type="text" name="age" id="age" placeholder="年龄" class="input-mini" value='${result.student.age}'>岁
                                            <span class="help-inline">填写年龄，如：10</span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                       <label class="control-label">性别</label>
                                       <div class="controls">
                                          <label class="radio inline">
                                              <input type="radio" name="sex" value="0" <#if result.student.sex == 0>checked</#if>/> 男孩
                                          </label>
                                          <label class="radio inline">
                                              <input type="radio" name="sex" value="1" <#if result.student.sex == 1>checked</#if>/> 女孩
                                          </label>  
                                       </div>
                                    </div>
                                    <div class="control-group">
                                        <label for="klass" class="control-label">班级</label>
                                        <div class="controls">
                                            <input type="text" name="klass" id="klass" placeholder="所属的你的学校的班级" class="input-large" value='${result.student.klass}'>
                                            <span class="help-inline">填写班级，如：小一班</span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label for="school" class="control-label">学校</label>
                                        <div class="controls">
                                            <input type="text" name="school" id="school" placeholder="所属的你的学校将学生" class="input-large"  value='${result.student.school}'>
                                            <span class="help-inline">填写学校，如：幼儿园</span>
                                        </div>
                                    </div>
                                     <div class="control-group">
                                        <label for="fatherName" class="control-label">父亲</label>
                                        <div class="controls">
                                            <input type="text" name="fatherName" id="fatherName" placeholder="学生父亲" class="input-large"  value='${result.student.fatherName}'>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label for="motherName" class="control-label">母亲</label>
                                        <div class="controls">
                                            <input type="text" name="motherName" id="motherName" placeholder="学生母亲" class="input-large"  value='${result.student.motherName}'>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label for="phone" class="control-label">家长联系电话</label>
                                        <div class="controls">
                                            <input type="text" name="phone" id="phone" placeholder="多个以空格隔开,用于发送短信通知" class="input-xlarge"  value='${result.student.phone}'>
                                            <span class="help-inline">填写电话，如：18520879240 18212345678</span>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                       <button type="submit" class="btn btn-primary"><i class="icon-ok"></i> 保存</button>
                                       <button type="reset" class="btn">重置</button>
                                    </div>
                                 </form>
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

    	<script>
    		var isModify = "<#if result?? && result.isModify??>${result.isModify?string}</#if>";
    		if(isModify == "false"){
    			if(!confirm("添加成功，是否继续添加？")){
    				location.href="${ctx}/";
    			}
    		}else if(isModify === "true"){
    			alert("修改成功");
    			history.go(-2);
    		}
    	</script>
    </body>
</html>
