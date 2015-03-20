<div class="row-fluid">
    <div class="span12">
        <div class="box">
            <div class="box-title">
                <h3><i class="icon-table"></i>费用列表</h3>
                <div class="box-tool">
                    <a data-action="collapse" href="#"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="box-content">
                <div class="btn-toolbar pull-right clearfix">
                    <a href="#modal-2" role="button" class="btn btn-info" data-toggle="modal"><i class="icon-search"></i> 搜索</a>
                    <button class="btn btn-danger delete_fee"><i class="icon-trash"></i> 删除</button>
                </div>
                <div style="margin:10px 0">
	                <#if result.data.name??>
						<span class='search_item'>当前搜索选项：</span>
						<span class="label label-info">${result.data.name}</span>
					</#if> 
				</div>
                <table class="table table-striped table-hover fill-head fee_list">
                    <thead>
                    <tr>
                        <th style="width: 18px"><input type="checkbox" /></th>
                        <th>费用名</th>
                        <th>应缴金额</th>
                        <th style="width: 300px">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if result??>
                    	<#list result.data.feeList as data>
                    		<tr>
                                <td><input type="checkbox" name="ids" value="${data.id}" class='student_id' /></td>
                                <td>${data.name}</td>
                                <td>${data.money}</td>
                                <td>
                                	<a class="btn btn-primary btn-small"  href="${ctx}/action/payment/fee/${data.id}/0"><i class="icon-reorder"></i> 缴费记录</a>
                                    <a class="btn btn-info btn-small edit_button"  href="#" data-id="${data.id}"><i class="icon-edit"></i> 编辑</a>
                                    <a class="btn btn-danger btn-small delete_button"  href="#" data-id="${data.id}"><i class="icon-trash"></i> 删除</a>
                                </td>
                            </tr>
                    	</#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>