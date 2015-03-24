 <div class="row-fluid">
    <div class="span12">
        <div class="box">
            <div class="box-title">
                <h3><i class="icon-table"></i><#if result.student??>${result.student.name}的</#if><#if result.fee??>${result.fee.name}的</#if>缴费记录</h3>
                <div class="box-tool">
                    <a data-action="collapse" href="#"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="box-content">
                <div class="btn-toolbar pull-right clearfix">
                	<#if !result.student??>
                		<button class="btn btn-primary analysis_button"><i class="icon-signal"></i> 统计</button>
                	</#if>
                    <a href="#modal-2" role="button" class="btn btn-info" data-toggle="modal"><i class="icon-search"></i> 搜索</a>
                    <button class="btn btn-danger delete_payment"><i class="icon-trash"></i> 删除</button>
                </div>
                <#if result.hasFilter?? && result.hasFilter>
	                <div style="margin:10px 0">
	                	<span>当前搜索选项：</span>
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
	                	<#if result.filter.startDate??>
	                		<span class='label label-info'>过期时间晚于：${result.filter.startDate?string("yyyy年MM月dd日")}</span>
	                	</#if>
	                	<#if result.filter.endDate??>
	                		<span class='label label-info'>过期时间早于：${result.filter.endDate?string("yyyy年MM月dd日")}</span>
	                	</#if>
	                	<#if result.filter.notClear??>
	                		<span class='label label-info'><#if result.filter.notClear>未结清<#else>已结清</#if></span>
	                	</#if>
	                </div>
                </#if>
                <table class="table table-striped table-hover fill-head payment_list">
                    <thead>
                    <tr>
                        <th style="width: 18px"><input type="checkbox" /></th>
                        <#if !result.student??>
                        <th>学生姓名</th>
                        <th>学校</th>
                        <th>班级</th>
                        </#if>
                        <#if !result.fee??>
                        <th>费用名</th>
                        </#if>
                        <th>缴费类型</th>
                        <th>缴纳状态</th>
                        <th>缴纳记录</th>
                        <th>创建时间</th>
                        <th style="width: 250px">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if result??>
                    	<#list result.payments.content as data>
                    		<tr>
                                <td><input type="checkbox" name="ids" value="${data.id}" class='payment_id' /></td>
                                <#if !result.student??>
                                <td>${data.studentName}</td>
                                <td>${data.school}</td>
                                <td>${data.klass}</td>
                                </#if>
                                <#if !result.fee??>
                                <td>${data.feeName}</td>
                                </#if>
                                <td><#if (data.payMethod == 0)>一次付清<#else>分期:共${data.instalment}期</#if></td>
                                <td>
                                	<ul class='unstyled'>
                                	<li>
                                		共需缴纳<span class='text-error'>${data.feeMoney}</span>元,已缴<span class='text-error'>${data.payTotal}</span>元,
                                		<#if (data.feeMoney <= data.payTotal)>
                                			已结清
                                		<#else>
	                                		<#if data.payMethod==1>
	                                			下期需缴<span class='text-error'>${data.nextNeedPay}</span>元,剩余${data.restInstalment}期未结清
	                                		</#if>
                                		</#if>
                                	</li>
                                	<#list data.payResults as payResult>
                                		<#if (data.payMethod == 0)>
                                			<li class='payment_result'>
                                				<i class='<#if payResult.status==0>icon-ok green<#else>icon-remove red</#if>'></i> 应缴金额：<span class='text-error'>${payResult.payMoney}</span>,实缴金额：<span class='text-error'>${payResult.money}</span>,<#if (payResult.status==0)>已结清<#else>剩余<span class='text-error'>${payResult.restMoney}</span>未结清</#if>
                                			</li>
                                		<#else>
                                			<li class='payment_result'>
                                				<i class='<#if payResult.status==0>icon-ok green<#else>icon-remove red</#if>'></i> 第${(payResult_index+1)?string.number}期应缴金额：<span class='text-error'>${payResult.payMoney}</span>,实缴金额：<span class='text-error'>${payResult.money}</span>,<#if (payResult.status==0)>已结清<#else>剩余<span class='text-error'>${payResult.restMoney}</span>未结清,将于${payResult.expireDate?string("yyyy年MM月dd日")}到期</#if>
                                			</li>
                                		</#if>
                                	</#list>
                                	</ul>
                                </td>
                                <td>
                                	<ul class="unstyled">
                                	<#list data.money as payRecord>
                                		<li class='payment_record'>
                                			${payRecord.payDate?string("yyyy年MM月dd日")}缴纳<span class='text-error'>${payRecord.money}</span>元
                                		</li>
                                	</#list>
                                	</ul>
                                </td>
                                <td>${data.createDate?string("yyyy年MM月dd日")}</td>
                                <td>
                                    <a href="#modal-3" role="button" data-id="${data.id}" class="btn btn-primary btn-small pay_button" data-toggle="modal"><i class="icon-credit-card"></i> 缴费</a>
                                    <a class="btn btn-danger btn-small delete_button" data-id="${data.id}"  href="#"><i class="icon-trash"></i> 删除</a>
                                </td>
                            </tr>
                    	</#list>
                    </#if>
                    </tbody>
                </table>
                </form>
                <#if result??>
                    <@pagination page=result.payments url=url />
                </#if>
            </div>
        </div>
    </div>
</div>