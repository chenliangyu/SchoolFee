<#include "common/vars.ftl">
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
    	</style>
    </head>
    <body>
       	<#include "common/updatebrowser.ftl">
        <!-- BEGIN Container -->
        <div class="container-fluid" id="main-container">
        	<#include "common/top.ftl">
        	<#include "common/sidebar.ftl">
        	<#if result.student??>
        		<#assign url=ctx+"/action/payment/student/"+result.student.id>
        	<#elseif result.feeId??>
        		<#assign url=ctx+"/action/payment/fee/"+result.feeId>
        	<#else>
        		<#assign url=ctx+"/action/payment/">
        	</#if>
            <!-- BEGIN Content -->
            <div id="main-content">
                <!-- BEGIN Page Title -->
                <div class="page-title">
                    <div>
                        <h1><i class="icon-file-alt"></i>缴费记录</h1>
                        <h4>看看你学生的缴费情况吧，</h4>
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
                        <li class="active">缴费记录</li>
                    </ul>
                </div>
                <!-- END Breadcrumb -->
                <!-- BEGIN Main Content -->
                   <#if result.student??>
                   		<#include "payment/addfee.ftl" />
                   </#if>
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
        <script type="text/javascript" src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${ctx}/assets/bootstrap-switch/static/js/bootstrap-switch.js"></script>
        <!--page specific plugin scripts-->


        <!--flaty scripts-->
        <script src="${ctx}/js/flaty.js"></script>
        <script>
			$(".add_paymethod").on("click","input[name='payMethod']",function(e){
				var value = $(this).val();
				if(value === "0"){
					$(".onepay_setting").show();
					$(".instalment_setting").hide();
				}else if(value === "1"){
					$(".instalment_setting").show();
					$(".onepay_setting").hide();
				}
			});
			$(".add_sendsms input").on("change",function(e){
				if($(this).is(":checked")){
					$(".sms_setting").show();
				}else{
					$(".sms_setting").hide();
				}
			});        	
        </script>
    </body>
</html>
