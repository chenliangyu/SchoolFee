<div id="modal-2" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
 <form action="${ctx}/action/fee/" method="post" class="form-horizontal form-row-separated search_form">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel1">搜索选项</h3>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label for="name" class="control-label">关键词</label>
            <div class="controls">
                <input type="text" name="name" id="name" placeholder="费用名称的关键词" value="${result.data.name}" class="input-large">
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true"><i class='icon-mail-reply'></i> 返回</button>
        <button type="submit" class="btn btn-primary"><i class='icon-search'></i> 搜索</button>
    </div>
  </form>
</div>