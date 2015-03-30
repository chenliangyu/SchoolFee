<#include "common/vars.ftl">
<!DOCTYPE html>
	<#include "common/html.ftl">
    <head>
		<#assign title="测试短信发送">
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
            <form action="${ctx}/action/test/postsend" method="post">
                <h3>测试发送短信</h3>
                <hr/>
                <div class="control-group">
                    <div class="controls">
                        <input type="text" name="phone" placeholder="输入测试的手机号码" class="input-block-level" />
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <input type="text" name="studentName" placeholder="输入学生姓名" class="input-block-level" />
                    </div>
                </div>
                 <div class="control-group">
                    <div class="controls">
                        <input type="text" name="feeName" placeholder="输入费用名" class="input-block-level" />
                    </div>
                </div>
                 <div class="control-group">
                    <div class="controls">
                        <input type="text" name="total" placeholder="输入应缴的费用" class="input-block-level" />
                    </div>
                </div>
                 <div class="control-group">
                    <div class="controls">
                        <input type="text" name="rest" placeholder="输入剩余的费用" class="input-block-level" />
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <button type="submit" class="btn btn-primary input-block-level">发送</button>
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
