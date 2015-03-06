<#include "common/vars.ftl">
<!DOCTYPE html>
	<#include "common/html.ftl">
    <head>
		<#assign title="登陆">
		<#assign description="">
    	<#include "common/meta.ftl">
		<#if result??>
			<script>
				alert("${result.msg}");
			</script>
		</#if>
    </head>
    <body class="login-page">
		<#include "common/updatebrowser.ftl">
        <!-- BEGIN Main Content -->
        <div class="login-wrapper">
            <!-- BEGIN Login Form -->
            <form id="form-login" action="${ctx}/action/postlogin" method="post">
                <h3>缴费管理系统</h3>
                <hr/>
                <div class="control-group">
                    <div class="controls">
                        <input type="text" name="username" placeholder="请输入用户名" class="input-block-level" />
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <input type="password" name="password" placeholder="请输入密码" class="input-block-level" />
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <label class="checkbox">
                            <input type="checkbox" value="true" name="isRemember"/>记住我
                        </label>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <button type="submit" class="btn btn-primary input-block-level">登陆</button>
                    </div>
                </div>
                <hr/>
            </form>
            <!-- END Login Form -->
            <!-- END Register Form -->
        </div>
        <!-- END Main Content -->
        <!--basic scripts-->
        <script>window.jQuery || document.write('<script src="${ctx}/assets/jquery/jquery-1.10.1.min.js"><\/script>')</script>
        <script src="${ctx}/assets/bootstrap/bootstrap.min.js"></script>
    </body>
</html>
