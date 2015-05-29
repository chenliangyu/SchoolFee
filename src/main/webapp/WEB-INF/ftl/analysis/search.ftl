<div id="modal-2" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
 <form action="${ctx}/action/analytics/list/0" method="post" class="form-horizontal form-row-separated search_form">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel1">搜索选项</h3>
    </div>
    <div class="modal-body">
		<div class="control-group">
            <label for="klass" class="control-label">班级</label>
            <div class="controls">
                <input type="text" name="klass" id="klass" class="input-large" value="${result.filter.klass}">
            </div>
        </div>
        <div class="control-group">
            <label for="school" class="control-label">学校</label>
            <div class="controls">
                <input type="text" name="school" id="school" class="input-large" value="${result.filter.school}">
            </div>
        </div>
        <div class="control-group">
            <label for="studentName" class="control-label">学生姓名</label>
            <div class="controls">
                <input type="text" name="studentName" id="studentName" class="input-large" value="${result.filter.studentName}">
            </div>
        </div>
		<div class="control-group">
            <label for="feeName" class="control-label">费用名</label>
            <div class="controls">
                <input type="text" name="feeName" id="feeName" class="input-large" value="${result.filter.feeName}">
            </div>
        </div>
        <div class="control-group">
              <label class="control-label">付款方式</label>
               <div class="controls">
               	  <label class="radio inline">
                      <input type="radio" name="payMethod" value="" <#if !result.filter.payMethod??>checked</#if>/> 不选
                  </label>
                  <label class="radio inline">
                      <input type="radio" name="payMethod" value="onePay" <#if result.filter.payMethod?? && result.filter.payMethod=="onePay">checked</#if>/> 一次付清
                  </label>
                  <label class="radio inline">
                      <input type="radio" name="payMethod" value="instalment" <#if result.filter.payMethod?? && result.filter.payMethod=="instalment">checked</#if>/> 分期付款
                  </label>  
               </div>
        </div>
        <div class="control-group">
            <label for="startDate" class="control-label">学期起始时间最早于</label>
            <div class="controls">
               <div class="input-append date date-picker" data-date="<#if result.filter.startDate??>${result.filter.startDate?string("yyyy-MM-dd")}</#if>" data-date-format="yyyy-m-d">
                  <input class="date-picker" data-date-format="yyyy-m-d" name="startDate" id="startDate" size="16" type="text" value="<#if result.filter.startDate??>${result.filter.startDate?string("yyyy-MM-dd")}</#if>"><span class="add-on"><i class="icon-calendar"></i></span>
               </div>
            </div>
        </div>
        <div class="control-group">
            <label for="endDate" class="control-label">学期结束时间最晚于</label>
            <div class="controls">
               <div class="input-append date date-picker" data-date="<#if result.filter.endDate??>${result.filter.endDate?string("yyyy-MM-dd")}</#if>" data-date-format="yyyy-m-d">
                  <input class="date-picker" data-date-format="yyyy-m-d" name="endDate" id="endDate" size="16" type="text" value="<#if result.filter.endDate??>${result.filter.endDate?string("yyyy-MM-dd")}</#if>"><span class="add-on"><i class="icon-calendar"></i></span>
               </div>
            </div>
        </div>
        <div class="control-group">
              <label class="control-label">是否结清</label>
               <div class="controls">
               	  <label class="radio inline">
                      <input type="radio" name="notClear" value="" <#if !result.filter.notClear??>checked</#if>/> 不选
                  </label>
                  <label class="radio inline">
                      <input type="radio" name="notClear" value="false" <#if result.filter.notClear?? && !result.filter.notClear>checked</#if>/> 是
                  </label>
                  <label class="radio inline">
                      <input type="radio" name="notClear" value="true" <#if result.filter.notClear?? && result.filter.notClear>checked</#if>/> 否
                  </label>  
               </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true"><i class='icon-mail-reply'></i> 返回</button>
        <button type="submit" class="btn btn-primary search_submit"><i class='icon-search'></i> 搜索</button>
    </div>
  </form>
</div>