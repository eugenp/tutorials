<#include "/admin/_layout/master.ftl">

<@view title="backup" sidebar="backup">
<section class="subtitle">
	<h2>备份和还原</h2>
</section>

<section style="margin:0 14px 0 16px;">
	<form method="post">
	<div class="row">
		<div class="col-md-8">
			<div id="op-panel" style="margin-right:5px">
				<button type="submit" formaction="/admin/backup/export" class="btn btn-warning btn-sm">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>  立即备份
				</button>

				<button style="margin-left:10px;" type="submit" formaction="/admin/backup/sync" class="btn btn-warning btn-sm">
					<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>  与文件系统同步
				</button>
			</div>
		</div>
		<div class="col-md-4"></div>
	</div>
	</form>
</section>

<section class="data-table">
	<div class="container-fluid">
		<table class="table table-striped">
			<thead>
			<tr>
				<th style="width:30px"><input class="item-selected" type="checkbox"></th>
				<th class="visible-sm visible-md visible-lg">文件名</th>
				<th class="visible-sm visible-md visible-lg">文件尺寸</th>
				<th class="visible-md visible-lg">创建时间</th>
				<th class="text-center td-btn">下载</th>
				<th class="text-center td-btn">还原</th>
			</tr>
			</thead>
			<tbody>
				<#list page.content as path>
				<tr>
					<td><input class="item-selected" name="selected" type="checkbox" value=""></td>
					<td class="visible-sm visible-md visible-lg"><a href="/admin/backup/file?q=${path.getFileName()}">${path.getFileName()}</a></td>
					<td class="visible-sm visible-md visible-lg">${filesize(path.fileSize)}</td>
					<td class="visible-md visible-lg">${path.createdAt}</td>
					<td class="text-center td-btn"><a class="btn-table" href="/admin/backup/file?op=download&q=${path.getFileName()}" title="下载"><i class="fa fa-save" aria-hidden="true"></a></td>
					<td class="text-center td-btn"><a class="btn-table" onclick="return confirm('Are you sure?')" href="/admin/backup/import?q=${path.getFileName()}" title="还原"><i class="fa fa-play" aria-hidden="true"></a></td>
				</tr>
				</#list>
			<tbody>
		</table>
	</div>
</section>
<@pagination.section page />
</@view>
