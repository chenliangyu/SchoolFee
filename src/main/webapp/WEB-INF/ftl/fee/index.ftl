<#include "common/vars.ftl">
<#include "common/page.ftl">
<!DOCTYPE html>
	<#include "common/html.ftl">
    <head>
		<#assign title="费用管理">
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
                        <h1><i class="icon-file-alt"></i>费用管理</h1>
                        <h4>添加一些费用说明，比如，学费多少钱等等</h4>
                    </div>
                </div>
                <!-- END Page Title -->
                <!-- BEGIN Breadcrumb -->
                <div id="breadcrumbs">
                    <ul class="breadcrumb">
                        <li class="active">
                            <i class="icon-home"></i>
                          费用管理		
                        </li>
                    </ul>
                </div>
                <!-- END Breadcrumb -->
				<#include "fee/search.ftl">
                <!-- BEGIN Main Content -->
					<#include "fee/addfee.ftl">
					<#include "fee/feelist.ftl">
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
			$(".add_submit").click(function(e){
				e.preventDefault();
				e.stopPropagation();
				var form = $(".add_form");
				var data = form.serializeArray();
				var postData = {};
				for(var i = 0,l = data.length;i<l;i++){
					if($.trim(data[i].value)){
						postData[data[i].name] = data[i].value;
					}
				}
				$.ajax({
					url: form.prop("action"),
					data : postData,
					type : "post",
					dataType : "json"
				}).done(function(data){
					if(data && data.result && data.result.code === "success"){
						alert(data.result.msg);
						location.reload();
					}
				}).fail(function(data){
					alert("数据库错误")
				})
			});
			$(".fee_list").on("click",".edit_button",function(e){
				var id = $(this).attr("data-id");
				$.ajax({
					url:"${ctx}/action/fee/get/"+id,
					type:"get",
					dataType:"json"
				}).done(function(data){
					if(data && data.result && data.result.code === "success"){
						if(data.result.data){
							fillForm(data.result.data);
						}
					}
				})
			});
			$(".fee_list").on("click",".delete_button",function(e){
				var id = $(this).attr("data-id");
				$.ajax({
					url:"${ctx}/action/fee/delete/"+id,
					type:"get",
					dataType:"json"
				}).done(function(data){
					if(data && data.result && data.result.code === "success"){
						alert(data.result.msg);
						location.reload();
					}
				})
			});
			$(".delete_fee").click(function(e){
				var ids = []
				$(".fee_id:checked").each(function(){
					ids.push($(this).val());
				});
				$.ajax({
					url:"${ctx}/action/fee/delete",
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
			function fillForm(data){
				var fields = $(".add_form").find(".modify_field");
				fields.each(function(){
					var key = $(this).attr("data-key");
					if(data && data[key]){
						$(this).val(data[key]);
					}	
				})
			}
		</script>
    </body>
</html>
