<#macro pagination page url>
	<#if page.totalElements!=0>
	<div class="pagination text-center">
	<ul>
	<#if page.isFirst()>
		<li><a href="#">← 上一页</a></li>
	<#else>
		<li><a href="${url}/${page.previousPageable().pageNumber}">← 上一页</a></li>
	</#if>
	<#if (page.totalPages<=8)>
		<#list 1..page.totalPages as pageNumber>
			<li <#if page.number==pageNumber-1>class="active"</#if>><a href="${url}/${pageNumber-1}">${pageNumber}</a></li>		
		</#list>
	<#else>
		<#if page.number<=3>
			<#list 1..6 as pageNumber>
				<li <#if page.number==pageNumber-1>class="active"</#if>><a href="${url}/${pageNumber-1}">${pageNumber}</a></li>
			</#list>
			<li><a href="#">...</a></li>
			<li><a href="${url}/${page.totalPages-1}">${page.totalPages}</a></li>
		<#elseif (page.number>=page.totalPages-5)>
			<li><a href="${url}/0">1</a></li>
			<li><a href="#">...</a></li>
			<#list page.totalPages-5..page.totalPages as pageNumber>
				<li <#if page.number==pageNumber-1>class="active"</#if>><a href="${url}/${pageNumber-1}">${pageNumber}</a></li>
			</#list>
		<#else>		
			<li><a href="${url}/0">1</a></li>
			<li><a href="#">...</a></li>
			<#list page.number..page.number+3 as pageNumber>
				<li <#if page.number==pageNumber-1>class="active"</#if>><a href="${url}/${pageNumber-1}">${pageNumber}</a></li>
			</#list>
			<li><a href="#">...</a></li>
			<li><a href="${url}/${page.totalPages-1}">${page.totalPages}</a></li>	
		</#if>
	</#if>
	
	<#if page.isLast()>
        <li><a href="#">下一页 → </a></li>
	<#else>
        <li><a href="${url}/${page.nextPageable().pageNumber}">下一页 → </a></li>
	</#if>
	</ul>
	</div>
	</#if>
</#macro>