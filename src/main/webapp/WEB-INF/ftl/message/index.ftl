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
    		.well{
    			position:relative;
    		}
    		.delete_message{
    			position:absolute;
    			right:10px;
    			top:5px;
    			cursor:pointer;
    		}
    		.well-operator{
    			position:absolute;
    			left:10px;
    			top:5px;
    			cursor:pointer;
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
        	(function(){
        		function update(){
	        		$.ajax({
	        			url : "${ctx}/action/message/updatestatus",
	        			dataType : "json"
	        		}).fail(function(){
	        			update();
	        		})
        		}
        		update();
        		$(".delete_message").click(function(e){
        			var id = $(this).attr("data-id");
        			var $this = $(this);
        			$.ajax({
	        			url : "${ctx}/action/message/delete/"+id,
	        			dataType : "json"
	        		}).done(function(){
	        			var well = $this.parents(".well")
	        			well.animate({
	        				height:0
	        			},"slow",function(){
	        				well.remove();
	        			});
	        		})
        		});
        		$(".show_payment").click(function(e){
        			e.preventDefault();
        			var studentId = $(this).attr("data-id");
        			location.href = "${ctx}/action/payment/student/"+studentId+"/0";
        		});
        	})();
        </script>
    </body>
</html>
