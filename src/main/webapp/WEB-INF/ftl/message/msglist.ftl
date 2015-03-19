<#list result.content as data>
<div class="well">
	<h4><#if data.isNew><span class='label label-primary'><i class='icon-envelope'></i>新邮件</span></#if>${data.createDate?string("yyyy年MM月dd日")}</h4>
    <p>${data.msgContent}</p>
</div>
</#list>
<@pagination page=result url=ctx+"/action/message/index" />