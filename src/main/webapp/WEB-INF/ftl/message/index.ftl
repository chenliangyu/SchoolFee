<#include "common/vars.ftl">
<#include "common/page.ftl">
<!DOCTYPE html>
	<#include "common/html.ftl">
    <head>
		<#assign title="消息通知">
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
                        <h1><i class="icon-file-alt"></i>消息通知</h1>
                        <h4>到了过期时间还没有缴费的都会在这里告诉你哦</h4>
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
                        <li class="active">消息通知</li>
                    </ul>
                </div>
                <!-- END Breadcrumb -->
                <!-- BEGIN Main Content -->
                   <#include "message/msglist.ftl" />
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
			$(".add_payment").on("click",function(e){
				e.preventDefault();
				e.stopPropagation();
				var form = $(".add_form");
				var dataArray = form.serializeArray();
				var data = {};
				for(var i = 0,l = dataArray.length;i<l;i++){
					if($.trim(dataArray[i].value)){
						data[dataArray[i].name] = dataArray[i].value;
					}
				}
				$.ajax({
					url : form.prop("action"),
					type:form.prop("method").toLowerCase(),
					dataType : "json",
					data : data
				}).done(function(data){
					if(data && data.result&&data.result.code === "success"){
						alert(data.result.msg);
						location.reload();
					}
				})
			});
			$(".add_sendsms input").on("change",function(e){
				if($(this).is(":checked")){
					$(".sms_setting").show();
				}else{
					$(".sms_setting").hide();
				}
			});
			$(".payment_list").on("click",".delete_button",function(e){
				var id = $(this).attr("data-id");
				$.ajax({
					url:"${ctx}/action/payment/delete/"+id,
					type:"get",
					dataType:"json"
				}).done(function(data){
					if(data && data.result && data.result.code === "success"){
						alert(data.result.msg);
						location.reload();
					}
				})
			});
			$(".payment_list").on("click",".pay_button",function(e){
				var id = $(this).attr("data-id");
				$(".payform_id").val(id);
			});
			$(".delete_payment").on("click",function(e){
				var ids = []
				$(".payment_id:checked").each(function(){
					ids.push($(this).val());
				});
				$.ajax({
					url:"${ctx}/action/payment/delete",
					type:"post",
					data : {ids:$.toJSON(ids)},
					dataType:"json"
				}).done(function(data){
					if(data && data.result && data.result.code === "success"){
						alert(data.result.msg);
						location.reload();
					}
				})			
			});
			$(".payform_submit").click(function(e){
				e.preventDefault();
				e.stopPropagation();
				var id = $(".payform_id").val();
				var money = $(".payform_money").val();
				$.ajax({
					url:"${ctx}/action/payment/pay/"+id+"/"+money,
					dataType:"json"
				}).done(function(data){
					if(data && data.result && data.result.code === "success"){
						alert(data.result.msg);
						location.reload();
					}
				})	
			})
        </script>
    </body>
</html>
