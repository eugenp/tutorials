<#include "/admin/_layout/master.ftl">

<#assign header>
<link rel="stylesheet" type="text/css" href="/public/css/form.css">
</#assign>

<@view title="task" sidebar="${(form.op_create())?then('create_task', 'tasks')}" header=header>
<section class="form-title">
	<h2>${(form.op_create())?then("创建任务", "修改任务信息")}</h2>
</section>

<form action="/admin/tasks${(form.op_create())?then("/create", "/"+form.id)}" method="post">
<#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />-->
	<@spring.formHiddenInput "form.op" />

	<div class="form-body">
		<@spring.bind "form.error" />
		<#if spring.status.error>
			<section class="form-row" style="margin-bottom:20px;">
				<div class="container-fluid">
					<div class="row">
						<div class="alert alert-danger" role="alert">
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							<span class="sr-only">Error:</span><@spring.showErrors "" />
						</div>
					</div>
			</section>
		</#if>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
					<@spring.bind "form.belongsTo" />
					<div class="item-title col-md-3">创建人</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control"
							       value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
					<@spring.bind "form.category" />
					<div class="item-title col-md-3">分类</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control"
							       value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>


		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
					<@spring.bind "form.priority" />
					<div class="item-title col-md-3">优先级</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control"
							       value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
					<@spring.bind "form.status" />
					<div class="item-title col-md-3">状态</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control"
							       value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
					<@spring.bind "form.content" />
					<div class="item-title col-md-3">内容</div>
					<div class="col-md-9" style="padding-right:0px;">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<textarea name="${spring.status.expression}" class="form-control" style="width:100%;max-width:100%;"
							          rows="3">${spring.status.value?default("")}</textarea>
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>
	</div>

	<section class="form-submit">
		<div class="pull-right">
			<a class="btn btn-lg btn-default btn-mute" href="/admin/tasks">取消</a>
			<button type="submit" class="btn btn-lg btn-primary btn-emphasis">确认保存</button>
		</div>
	</section>
</form>
</@view>
