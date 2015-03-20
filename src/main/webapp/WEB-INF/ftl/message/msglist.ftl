<#list result.content as data>
<div class="well">
	<span class="well-operator"><span class='label label-info'><i class='icon-envelope'></i> 新邮件</span></span>
	<span class='delete_message' data-id="${data.id}"><i class='icon-remove'></i></span>
	<h4>${data.createDate?string("yyyy年MM月dd日")}</h4>
    <p>${data.msgContent}</p>
</div>
</#list>
<@pagination page=result url=ctx+"/action/message/index" />