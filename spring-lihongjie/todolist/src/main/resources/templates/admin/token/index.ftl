<#include "/admin/_layout/master.ftl">

<#assign header>

</#assign>

<@view title="token" sidebar="tokens" header=header>
<section class="subtitle">
	<h2>API登录凭证</h2>
</section>

<section class="data-table">
	<div class="container-fluid">
		<table class="table table-striped">
			<thead>
			<tr>
				<th class="visible-sm visible-md visible-lg">Key</th>
				<th class="visible-lg">用户编号</th>
				<th class="visible-lg">邮箱地址</th>
				<th class="visible-lg">创建时间</th>
			</tr>
			</thead>
			<tbody>
				<#list tokens as token>
				<tr>
					<td class="visible-sm visible-md visible-lg">${token.secret}</td>
					<td class="visible-lg">${token.id}</td>
					<td class="visible-lg">${token.email}</td>
					<td class="visible-lg">${token.createdAt}</td>
				</tr>
				</#list>
			<tbody>
		</table>
	</div>
</section>
</@view>
