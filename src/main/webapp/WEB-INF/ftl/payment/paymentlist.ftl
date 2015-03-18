 <div class="row-fluid">
    <div class="span12">
        <div class="box">
            <div class="box-title">
                <h3><i class="icon-table"></i>缴费记录</h3>
                <div class="box-tool">
                    <a data-action="collapse" href="#"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="box-content">
                <div class="btn-toolbar">
                    <a href="#modal-2" role="button" class="btn btn-info" data-toggle="modal"><i class="icon-cog"></i> 搜索</a>
                    <button class="btn btn-danger delete_student"><i class="icon-cog"></i> 删除</button>
                </div>
                <form action="${ctx}/action/payment/delete" method="post" class='delete_form'>
                <table class="table table-striped table-hover fill-head">
                    <thead>
                    <tr>
                        <th style="width: 18px"><input type="checkbox" /></th>
                        <#if !result.student??>
                        <th>学生姓名</th>
                        <th>学校</th>
                        <th>班级</th>
                        </#if>
                        <#if !result.feeId??>
                        <th>费用名</th>
                        </#if>
                        <th>缴费类型</th>
                        <th>缴纳状态</th>
                        <th>缴纳记录</th>
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
                                <#if !result.feeId??>
                                <td>${data.feeName}</td>
                                </#if>
                                <td><#if (data.payMethod == 0)>一次付清<#else>分期</#if></td>
                                <td>
                                	<#list data.payResults as payResult>
                                		<#if (data.payMethod == 0)>
                                			<span class='payment_result'>
                                				应缴金额：${payResult.payMoney},实缴金额：${payResult.money},<#if (payResult.status==0)>已结清<#else>剩余${payResult.restMoney}未结清</#if>
                                			</span>
                                		<#else>
                                			<span class='payment_result'>
                                				第${payResult_index}期应缴金额：${payResult.payMoney},实缴金额：${payResult.money},<#if (payResult.status==0)>已结清<#else>剩余${payResult.restMoney}未结清</#if>
                                			</span>
                                		</#if>
                                	</#list>
                                </td>
                                <td>
                                	<#list data.money as payRecord>
                                		<span class='payment_record'>
                                			${payRecord.payDate?date}缴纳${payRecord.money}元
                                		</span>
                                	</#list>
                                </td>
                                <td>
                                    <a href="#modal-3" role="button" class="btn btn-info" data-toggle="modal"><i class="icon-cog"></i> 缴费</a>
                                    <a class="btn btn-danger btn-small"  href="${ctx}/action/student/delete/${data.id}"><i class="icon-trash"></i>删除</a>
                                </td>
                            </tr>
                    	</#list>
                    </#if>
                    
                    <!--<tr>
                        <td><input type="checkbox" value="1" /></td>
                        <td>Mark</td>
                        <td>Otto<span class="label label-info pull-right"><i class="icon-twitter"></i> New Twitte</span></td>
                        <td>@mdo<span class="badge badge-info pull-right">+10</span></td>
                        <td>
                            <a class="btn btn-primary btn-small"  href="#"><i class="icon-edit"></i> 编辑</a>
                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 删除</a>
                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 缴费</a>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" value="2" /></td>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>@fat</td>
                        <td>
                            <a class="btn btn-primary btn-small" href="#"><i class="icon-edit"></i> 编辑</a>
                            <a class="btn btn-danger btn-small" href="#"><i class="icon-trash"></i> 删除</a>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" value="3" /></td>
                        <td>Larry <span class="label label-success label-small pull-right">New User</span></td>
                        <td>the Bird</td>
                        <td><a href="#">@twitter</a></td>
                        <td>
                            <a class="btn btn-primary btn-small" href="#"><i class="icon-edit"></i> 编辑</a>
                            <a class="btn btn-danger btn-small"  href="#"><i class="icon-trash"></i> 删除</a>
                        </td>
                    </tr>-->
                    </tbody>
                </table>
                </form>
                <#if result??>
                    <@pagination page=result.payments url=url />
                        <!--<li><a href="#">← 上一页</a></li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li class="active"><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">6</a></li>
                        <li><a href="#">下一页 → </a></li>-->
                </#if>
            </div>
        </div>
    </div>
</div>