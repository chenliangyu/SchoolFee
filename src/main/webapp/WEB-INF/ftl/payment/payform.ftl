<div id="modal-3" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
 <form action="${ctx}/action/student/" method="post" class="form-horizontal form-row-separated search_form">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel1">继续缴费</h3>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label for="money" class="control-label">金额</label>
            <div class="controls">
                <input type="text" name="money" id="money" placeholder="姓名/班级/学校/父亲名/母亲名的关键字" value='${result.student.name}' class="input-large">
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">返回</button>
        <button type="submit" class="btn btn-primary">缴费</button>
    </div>
  </form>
</div>