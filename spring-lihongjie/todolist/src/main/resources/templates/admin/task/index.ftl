<#include "/admin/_layout/master.ftl">

<#assign header>
<script type="text/coffeescript" src="/private/script/tasks.coffee"></script>
</#assign>

<@view title="task" sidebar="tasks" header=header>
<section class="subtitle">
	<h2>任务列表</h2>
</section>

<form method="post">
	<section style="margin:0 14px 0 16px;">
		<div class="row">
			<div class="col-md-8">
				<div id="op-panel" class="btn-group hidden" role="group" aria-label="..." style="margin-right:5px">
					<button type="submit" formaction="/admin/tasks/delete" class="btn btn-warning btn-sm">
						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除
					</button>
				</div>
				<div id="sort-panel" class="btn-group" role="group" aria-label="...">
					<button type="button" class="btn btn-default btn-sm">编号</button>
					<button type="button" class="btn btn-default btn-sm">创建时间</button>
				</div>
			</div>
			<div class="col-md-4">
				<div id="search-panel" class="input-group">
					<div class="form-group has-feedback has-clear">
						<input type="text" class="form-control" id="exampleInput1" placeholder="">
						<span style="color:#bec0c2;" class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden"></span>
					</div>
                <span class="input-group-btn">
                    <button type="button" class="btn btn-default" id="exampleButton1">搜索</button>
                </span>
				</div>
			</div>
		</div>
	</section>

	<section class="data-table">
		<div class="container-fluid">
			<table class="table table-striped">
				<thead>
				<tr>
					<th style="width:30px"><input class="item-selected" type="checkbox"></th>
					<th class="visible-sm visible-md visible-lg">编号</th>
					<th class="visible-lg">类型</th>
					<th class="visible-lg">优先级</th>
					<th class="visible-lg">状态</th>
					<th class="visible-xs visible-sm visible-md visible-lg">内容</th>
					<th class="visible-lg">创建人</th>
					<th class="visible-lg">创建时间</th>
					<th class="text-center td-btn">操作</th>
				</tr>
				</thead>
				<tbody>
					<#list page.content as task>
					<tr>
						<td><input class="item-selected" name="selected" type="checkbox" value="${task.id}"></td>
						<td class="visible-sm visible-md visible-lg">${task.id}</td>
						<td class="visible-lg">${task.category}</td>
						<td class="visible-lg">${task.priority}</td>
						<td class="visible-lg">${task.status}</td>
						<td class="visible-xs visible-sm visible-md visible-lg">${task.content?html}</td>
						<td class="visible-lg">${task.belongsToName}</td>
						<td class="visible-lg">${task.createdAt}</td>
						<td class="text-center td-btn"><a class="btn-table" href="/admin/tasks/${task.id}/edit" title="编辑"><i class="fa fa-cog" aria-hidden="true"></a></td>
					</tr>
					</#list>
				<tbody>
			</table>
		</div>
	</section>
	<@pagination.section page />
</form>
</@view>
