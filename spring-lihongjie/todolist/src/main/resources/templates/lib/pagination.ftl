<#--
window.page = (index) ->
    p = window.location.href
    [base, paramString] = p.split '?'
    paramString = "" unless paramString?
    params = paramString.split '&'
    params = params.filter (x) -> x.match(/^page=\d+/) == null
    params.push "page=#{index}"
    result = "#{base}?#{params.join('&').replace(/^&/, "")}"
    document.location.href = result
-->

<#macro section page>
<section class="paginations">
	<div class="container-fluid">
		<small class="pull-left pagination-text visible-sm visible-md visible-lg">现在是第${page.number+1}页，共${page.totalPages}页，总共${page.totalElements}条数据</small>
		<ul class="pagination pull-right">
            <#-- first page -->
			<li ${page.first?then("class=\"disabled\"","")}>
				<a href="javascript:page(${page.number})" aria-label="Previous">
					<span aria-hidden="true">&laquo; 上一页</span>
				</a>
			</li>
            <li><a href="#">${page.number+1}</a></li>
    <#--<li><a href="#">2</a></li>-->
    <#--<li><a href="#">3</a></li>-->
    <#--<li><a href="#">4</a></li>-->
    <#--<li><a href="#">5</a></li>-->
            <#-- last page -->
            <li ${page.last?then("class=\"disabled\"","")}>
				<a href="javascript:page(${page.number+2})" aria-label="Next">
					<span aria-hidden="true">下一页 &raquo;</span>
				</a>
			</li>
		</ul>
	</div>
</section>
</#macro>
