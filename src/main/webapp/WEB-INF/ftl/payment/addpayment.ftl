<div class="row-fluid">
    <div class="span12">
        <div class="box">
            <div class="box-title">
                <h3><i class="icon-reorder"></i>缴费</h3>
                <div class="box-tool">
                    <a data-action="collapse" href="#"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="box-content">
                <form action="${ctx}/action/payment/add" method="post" class="form-horizontal form-row-separated add_form">
                	<input type='hidden' name="studentId" value="${result.student.id}" />
                    <div class="control-group">
                        <label for="name" class="control-label">姓名</label>
                        <div class="controls">
                        	<input class="input-medium" type="text" placeholder="${result.student.name}" disabled="">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">费用选择</label>
                        <div class="controls">
                        	<#if result.feeList?? && (result.feeList?size>0)>
                            <select class="span6" name="feeId" data-placeholder="Choose a Category" tabindex="1">
                                <option>Select...</option>
                                <#list result.feeList as fee>
                                <option value='${fee.id}'>${fee.name}:${fee.money}</option>
                                </#list>
                             </select>
                             <#else>
                             	<span class='span6 miss_fee'>先去<a href="#">费用管理</a>里添加几个费用先吧。否则没法缴费</span>
                             </#if>
                        </div>
                    </div>
                    <div class="control-group">
                       <label class="control-label">缴费方式</label>
                       <div class="controls add_paymethod">
                          <label class="radio inline">
                              <input type="radio" name="payMethod" value="0" checked/> 一次付清
                          </label>
                          <label class="radio inline">
                              <input type="radio" name="payMethod" value="1" /> 分期付款
                          </label>  
                       </div>
                    </div>
                    <div class="control-group instalment_setting">
                        <label for="instalment" class="control-label">分期数</label>
                        <div class="controls">
                            <input type="text" name="instalment" id="instalment" placeholder="分多少期" class="input-mini">
                        </div>
                    </div>
                    <div class="control-group instalment_setting">
                        <label for="school" class="control-label">过期时间</label>
                        <div class="controls expire_month">
                            	每月<input type="text" name="expireDayOfMonth" id="expireDayOfMonth" class="input-mini">号
                            	<input type="hidden" name="instalmentMethod" value="0"/>
                        </div>
                    </div>
                    <div class="control-group onepay_setting">
                        <label for="fatherName" class="control-label">过期时间</label>
                        <div class="controls">
                            <div class="input-append date date-picker" data-date-format="yyyy-m-d">
                                <input class="date-picker" name='expireDate' data-date-format="yyyy-m-d" size="16" type="text"><span class="add-on"><i class="icon-calendar"></i></span>
                             </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">过期后是否发送短信</label>
                        <div class="controls">
							<div class="switch has-switch add_sendsms" data-on="primary" data-off="info">
                                <input name="sendMessage" id="sendMessage" type="checkbox" value="true">
                            </div>
                        </div>
                    </div>
                    <div class="control-group sms_setting">
                        <label for="motherName" class="control-label">短信通知间隔</label>
                        <div class="controls">
							每<input class="input-mini" name='smsInterval' type="text"><select name='smsPeriod' class="span1"><option value='0'>天</option><option value='1'>周</option><option value='2'>月</option></select>发送一次
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="phone" class="control-label">过期后是否发送消息通知</label>
                        <div class="controls">
                            <div class="switch has-switch" data-on="primary" data-off="info">
                                <input name="sendNotify" id="sendNotify" type="checkbox" value="true" checked>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="phone" class="control-label">金额</label>
                        <div class="controls">
                            <input class="input-medium" name='payMoney' type="text">
                        </div>
                    </div>
                    <div class="form-actions">
                       <button type="submit" class="btn btn-primary add_payment"><i class="icon-ok"></i> 保存</button>
                    </div>
                 </form>
            </div>
        </div>
    </div>
</div>