<div id="modal-2" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
 <form action="${ctx}/action/student/" method="post" class="form-horizontal form-row-separated search_form">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel1">搜索选项</h3>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label for="keyword" class="control-label">关键词</label>
            <div class="controls">
                <input type="text" name="keyword" id="keyword" placeholder="姓名/班级/学校/父亲名/母亲名的关键字" value='${result.filter.keyword}' class="input-large">
            </div>
        </div>
        <div class="control-group">
            <label for="ageStart" class="control-label">最小年龄</label>
            <div class="controls">
                <input type="text" name="ageStart" id="ageStart" placeholder="搜索年龄大于改值的学生" class="input-mini" <#if result.filter.ageStart??>value='${result.filter.ageStart?string.number}'</#if>>岁
            </div>
        </div>
        <div class="control-group">
           <label for="ageEnd" class="control-label">最大年龄</label>
           <div class="controls">
              <input type="text" name="ageEnd" id="ageEnd" placeholder="搜索年龄小于改值的学生" class="input-mini" <#if result.filter.ageEnd??>value='${result.filter.ageEnd?string.number}'</#if>>岁  
           </div>
        </div>
        <div class="control-group">
              <label class="control-label">性别</label>
               <div class="controls">
               	  <label class="radio inline">
                      <input type="radio" value="0" /> 随便
                  </label>
                  <label class="radio inline">
                      <input type="radio" name="sex" value="0" <#if result.filter.sex==0>checked</#if> /> 男孩
                  </label>
                  <label class="radio inline">
                      <input type="radio" name="sex" value="1" <#if result.filter.sex==1>checked</#if> /> 女孩
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