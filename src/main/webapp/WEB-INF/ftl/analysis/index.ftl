<#include "common/vars.ftl">
<#include "common/page.ftl">
<!DOCTYPE html>
	<#include "common/html.ftl">
    <head>
		<#assign title="费用记录">
		<#assign description="">
    	<#include "common/meta.ftl">
    	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-switch/static/stylesheets/bootstrap-switch.css">
    	<style>
    		.miss_fee{
    			line-height:30px;
    		}
    		.instalment_setting{
    			display:none;
    		}
    		.sms_setting{
    			display:none;
    		}
    		div.datepicker{
    			z-index:9999;
    		}
    	</style>
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
                        <h1><i class="icon-file-alt"></i>费用统计</h1>
                        <h4>可以来看看各项费用的收费情况吧。</h4>
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
                        <li class="active">费用统计</li>
                    </ul>
                </div>
                <!-- END Breadcrumb -->
                <!-- BEGIN Main Content -->
                	<#include "analysis/search.ftl" />
                   <#include "analysis/list.ftl" />
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
        <script type="text/javascript" src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
        <script src="${ctx}/assets/nicescroll/jquery.nicescroll.min.js"></script>
        <script src="${ctx}/assets/jquery.json.min.js"></script>
        <!--page specific plugin scripts-->


        <!--flaty scripts-->
        <script src="${ctx}/js/flaty.js"></script>
        <script>
        	$("body").on("click",".detail_button",function(e){
        		var form = $("#submitForm");
        		form.find("[name='klass']").val($(this).attr("data-klass"));
        		form.find("[name='school']").val($(this).attr("data-school"));
        		form.attr("action","${ctx}/action/payment/fee/"+$(this).attr("data-id")+"/0");
        		form.submit();
        	});
        </script>
    </body>
</html>
