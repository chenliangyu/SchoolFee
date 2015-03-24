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
        	<#if result.student??>
        		<#assign url=ctx+"/action/payment/student/"+result.student.id>
        	<#elseif result.fee??>
        		<#assign url=ctx+"/action/payment/fee/"+result.fee.id>
        	<#else>
        		<#assign url=ctx+"/action/payment/">
        	</#if>
            <!-- BEGIN Content -->
            <div id="main-content">
                <!-- BEGIN Page Title -->
                <div class="page-title">
                    <div>
                        <h1><i class="icon-file-alt"></i>缴费记录</h1>
                        <h4>看看你学生的缴费情况吧，如果是继续缴费，记得点列表中的缴费</h4>
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
                <#include "payment/search.ftl" />
                <#include "payment/payform.ftl" />
                <!-- BEGIN Main Content -->
                   <#if result.student??>
                   		<#include "payment/addpayment.ftl" />
                   </#if>
                   <#include "payment/paymentlist.ftl" />
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
  		<script type="text/javascript" src="${ctx}/assets/jquery-validation/dist/jquery.validate.min.js"></script>
        <script type="text/javascript" src="${ctx}/assets/jquery-validation/dist/additional-methods.min.js"></script>


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
			$(".analysis_button").on("click",function(e){
				var form = $(".search_form");
				form.attr("action","${ctx}/action/analytics/list/0<#if result.fee??>?feeId=${result.fee.id}</#if>");
				$(".search_submit").click();
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
			});
			$(".instalmentmethod_select").change(function(e){
				var value = $(this).val();
				if(value==0){
					$(".instalmentmethod_month").show();
					$(".instalmentmethod_week").hide();
				}else{
					$(".instalmentmethod_month").hide();
					$(".instalmentmethod_week").show();
				}
			});
        </script>
    </body>
</html>
