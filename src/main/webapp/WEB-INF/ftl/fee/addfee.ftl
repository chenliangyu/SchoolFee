<div class="row-fluid">
    <div class="span12">
        <div class="box">
            <div class="box-title">
                <h3><i class="icon-reorder"></i>添加费用</h3>
                <div class="box-tool">
                    <a data-action="collapse" href="#"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="box-content">
                <form action="${ctx}/action/fee/postadd" method="post" class="form-horizontal add_form">
                    <input type='hidden' name="id" class='modify_field' data-key="id" />
                    <div class="row-fluid">
                    	<div class="span6">
		                    <div class="control-group">
		                        <label for="name" class="control-label">费用名</label>
		                        <div class="controls">
		                        	<input id="name" name="name" class="input-medium modify_field" type="text"  data-key="name">
		                        </div>
		                    </div>
	                    </div>
	                    <div class="span6">
		                    <div class="control-group">
		                        <label for="money" class="control-label">应缴金额</label>
		                        <div class="controls">
		                        	<input id="money" name="money" class="input-medium modify_field" type="text" data-key="money">
		                        </div>
		                    </div>
	                    </div>
                    </div>
                     <div class="row-fluid">
                    	<div class="span6">
		                    <div class="control-group">
		                        <label for="name" class="control-label">费用起始时间</label>
		                        <div class="controls">
		                        	<div class="input-append date date-picker" data-date-format="yyyy-m-d">
					                  <input class="date-picker modify_field" data-date-format="yyyy-m-d" name="startDate" id="startDate" size="16" type="text" data-key="startDate"><span class="add-on"><i class="icon-calendar"></i></span>
					               </div>
		                        </div>
		                    </div>
	                    </div>
	                    <div class="span6">
		                    <div class="control-group">
		                        <label for="money" class="control-label">费用结束时间</label>
		                        <div class="controls">
		                        	<div class="input-append date date-picker" data-date-format="yyyy-m-d">
					                  <input class="date-picker modify_field" data-date-format="yyyy-m-d" name="endDate" id="endDate" size="16" type="text" data-key="endDate"><span class="add-on"><i class="icon-calendar"></i></span>
					               </div>
		                        </div>
		                    </div>
	                    </div>
                    </div>
                    <div class="form-actions">
                       <button type="submit" class="btn btn-primary add_submit"><i class="icon-ok"></i> 保存</button>
                    </div>
                 </form>
            </div>
        </div>
    </div>
</div>