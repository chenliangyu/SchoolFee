<div id="modal-2" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
 <form action="${ctx}/action/student/" method="post" class="form-horizontal form-row-separated search_form">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel1">搜索选项</h3>
    </div>
    <div class="modal-body">
    	<#if !result.student??>
    		<div class="control-group">
                <label for="klass" class="control-label">班级</label>
                <div class="controls">
                    <input type="text" name="klass" id="klass" class="input-large">
                </div>
            </div>
            <div class="control-group">
                <label for="school" class="control-label">学校</label>
                <div class="controls">
                    <input type="text" name="school" id="school" class="input-large">
                </div>
            </div>
            <div class="control-group">
                <label for="studentName" class="control-label">学生姓名</label>
                <div class="controls">
                    <input type="text" name="studentName" id="studentName" class="input-large">
                </div>
            </div>
    		<#if !result.feeId??>
        		<div class="control-group">
                    <label for="feeName" class="control-label">费用名</label>
                    <div class="controls">
                        <input type="text" name="feeName" id="feeName" class="input-large">
                    </div>
                </div>
    		</#if>
    	</#if>
        <div class="control-group">
            <label for="startDate" class="control-label">过期时间最早于</label>
            <div class="controls">
               <div class="input-append date date-picker" data-date="12-02-2012" data-date-format="yyyy年mm月dd日" data-date-viewmode="years">
                  <input class="date-picker" name="startDate" id="startDate" size="16" type="text" value="12-02-2012"><span class="add-on"><i class="icon-calendar"></i></span>
               </div>
            </div>
        </div>
        <div class="control-group">
            <label for="endDate" class="control-label">过期时间最晚于</label>
            <div class="controls">
               <div class="input-append date date-picker" data-date="12-02-2012" data-date-format="yyyy年mm月dd日" data-date-viewmode="years">
                  <input class="date-picker" name="endDate" id="endDate" size="16" type="text" value="12-02-2012"><span class="add-on"><i class="icon-calendar"></i></span>
               </div>
            </div>
        </div>
        <div class="control-group">
              <label class="control-label">是否结清</label>
               <div class="controls">
               	  <label class="radio inline">
                      <input type="radio" value="0" /> 不选
                  </label>
                  <label class="radio inline">
                      <input type="radio" name="notClear" value="0" /> 是
                  </label>
                  <label class="radio inline">
                      <input type="radio" name="notClear" value="1" /> 否
                  </label>  
               </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">返回</button>
        <button type="submit" class="btn btn-primary">搜索</button>
    </div>
  </form>
</div>