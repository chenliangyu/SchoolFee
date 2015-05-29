<form id="submitForm" method="post">
	<input type="hidden" name="klass" />
	<input type="hidden" name="school" />
	<#if result.filter.studentName??>
		<input type="hidden" name="studentName" value="${result.filter.studentName}" />
	</#if>
	<#if result.filter.startDate??>
		<input type="hidden" name="startDate" value="${result.filter.startDate?string("yyyy-MM-dd")}" />
	</#if>
	<#if result.filter.endDate??>
		<input type="hidden" name="endDate" value="${result.filter.endDate?string("yyyy-MM-dd")}" />
	</#if>
	<#if result.filter.notClear??>
		<input type="hidden" name="notClear" value="${result.filter.notClear}" />
	</#if>
</form>
<div class="clearfix">
	<a class="btn btn-info pull-right" href="#modal-2" role="button" data-toggle="modal"><i class="icon-search"></i> 搜索</a>
	<#if result.hasFilter??&& result.hasFilter>
		<div style="margin:10px 0">
			<span>当前列表统计选项：</span>
			<#if result.filter.feeName??>
				<span class='label label-info'>费用名：${result.filter.feeName}</span>
			</#if>
			<#if result.filter.studentName??>
				<span class='label label-info'>学生姓名：${result.filter.studentName}</span>
			</#if>
			<#if result.filter.klass??>
				<span class='label label-info'>班级：${result.filter.klass}</span>
			</#if>
			<#if result.filter.school??>
				<span class='label label-info'>学校：${result.filter.school}</span>
			</#if>
			<#if result.filter.payMethod??>
				<span class='label label-info'><#if result.filter.payMethod=="onePay">一次付清<#else>分期付款</#if></span>
			</#if>     
			<#if result.filter.startDate??>
				<span class='label label-info'>学期起始时间晚于：${result.filter.startDate?string("yyyy年MM月dd日")}</span>
			</#if>
			<#if result.filter.endDate??>
				<span class='label label-info'>学期结束时间早于：${result.filter.endDate?string("yyyy年MM月dd日")}</span>
			</#if>
			<#if result.filter.notClear??>
				<span class='label label-info'><#if result.filter.notClear>未结清<#else>已结清</#if></span>
			</#if>
		</div>
	</#if>
</div>
<#list result.analytics.content as data>
<div class="row-fluid">
    <div class="span12">
        <div class="box">
            <div class="box-title">
                <h3><i class="icon-table"></i> ${data.feeName}(${data.startDate?string("yyyy年MM月dd日")}-${data.endDate?string("yyyy年MM月dd日")})的统计情况</h3>
                <div class="box-tool">
                    <a data-action="collapse" href="#"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="box-content">
                <table class="table table-striped table-hover fill-head analytics_list">
                    <thead>
                    <tr>
                        <th>学校</th>
                        <th>班级</th>
                        <th>概览</th>
                        <th>一次付清概览</th>
                        <th>分期概览</th>
                        <th>详情</th>
                    </tr>
                    </thead>
                    <tbody>
                    	<#list data.result as singleData>
                    		<tr>
                                <td>${singleData.school}</td>
                                <td>${singleData.klass}</td>
                                <td>
                                	<ul class='unstyled'>
	                                	<li>
	                                		缴纳人数：<span class="text-error">${singleData.hasPayStudentNumber!0}</span>
	                                	</li>
	                                	<li>
	                                		已结清人数：<span class="text-error">${singleData.clearStudentNumber!0}</span>
	                                	</li>
	                                	<li>
	                                		未结清人数：<span class="text-error">${singleData.notClearStudentNumber!0}</span>
	                                	</li>
	                                	<li>
	                                		应缴纳：<span class="text-error">${singleData.total!0}</span>元
	                                	</li>
	                                	<li>
	                                		实际缴纳：<span class="text-error">${singleData.payTotal!0}</span>元
	                                	</li>
	                                	<li>
	                                		剩余钱款：<span class="text-error">${singleData.rest!0}</span>元
	                                	</li>
                                	</ul>
                                </td>
                                <td>
                                	<ul class="unstyled">
                                		<li>
                                			一次付清的人数：<span class="text-error">${singleData.onepayStudentNumber!0}</span>
                                		</li>
                                		<li>
                                			已结清人数：<span class="text-error">${singleData.onepayClearStudentNumber!0}</span>
                                		</li>
                                		<li>
                                			未结清的人数：<span class="text-error">${singleData.onepayNotClearStudentNumber!0}</span>
                                		</li>
                                		<li>
                                			应缴纳：<span class="text-error">${singleData.onepayTotal!0}</span>元
                                		</li>
                                		<li>
                                			实际缴纳：<span class="text-error">${singleData.onepayPayTotal!0}</span>元
                                		</li>
                                		<li>
                                			剩余钱款：<span class="text-error">${singleData.onepayRest!0}</span>元
                                		</li>
                                	</ul>
                                </td>
                                 <td>
                                	<ul class="unstyled">
                                		<li>
                                			分期付款的人数：<span class="text-error">${singleData.instalmentStudentNumber!0}</span>
                                		</li>
                                		<li>
                                			已结清人数：<span class="text-error">${singleData.instalmentClearStudentNumber!0}</span>
                                		</li>
                                		<li>
                                			未结清的人数：<span class="text-error">${singleData.instalmentNotClearStudentNumber!0}</span>
                                		</li>
                                		<li>
                                			应缴纳：<span class="text-error">${singleData.instalmentTotal!0}</span>元
                                		</li>
                                		<li>
                                			实际缴纳：<span class="text-error">${singleData.instalmentPayTotal!0}</span>元
                                		</li>
                                		<li>
                                			剩余钱款：<span class="text-error">${singleData.instalmentRest!0}</span>元
                                		</li>
                                	</ul>
                                </td>
                                <td>
                                	<a class='btn btn-primary detail_button' data-klass="${singleData.klass}" data-school="${singleData.school}" data-id="${data.id}">查看详情</a>
                                </td>
                            </tr>
                   		 </tbody>
                   	</#list>
                </table>
            </div>
        </div>
    </div>
</div>
</#list>
<@pagination page=result.analytics url=ctx+"/action/analytics/list" />
