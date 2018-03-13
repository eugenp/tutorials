<#include "/admin/_layout/master.ftl">

<#assign header>
<link rel="stylesheet" type="text/css" href="/public/css/form.css">
</#assign>

<@view title="user" sidebar="${(form.op_create())?then('create_user', 'users')}" header=header>
<section class="form-title">
	<h2>${(form.op_create())?then("创建用户", "修改用户信息")}</h2>
</section>

<form action="/admin/users${(form.op_create())?then("/create", "/"+form.id?default(0))}" method="post">
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
            <@spring.bind "form.email" />
					<div class="item-title col-md-3">邮件地址</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="email" name="${spring.status.expression}" class="form-control" value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
            <@spring.bind "form.name" />
					<div class="item-title col-md-3">名称</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control" value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
            <@spring.bind "form.password" />
					<div class="item-title col-md-3">密码</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control" <#if !form.op_create()>placeholder="请输入新密码"</#if>>
							<span class="help-block"><@spring.showErrors "" /></span>
                <#if !form.op_create()><div class="help">将值留空使其保持不变。</div></#if>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
            <@spring.bind "form.mobile" />
					<div class="item-title col-md-3">手机</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control" value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
            <@spring.bind "form.location" />
					<div class="item-title col-md-3">区域</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control" value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
            <@spring.bind "form.wechatid" />
					<div class="item-title col-md-3">微信</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control" value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
				</div>
		</section>

		<section class="form-row">
			<div class="container-fluid">
				<div class="row">
            <@spring.bind "form.role" />
					<div class="item-title col-md-3">权限</div>
					<div class="col-md-9">
						<div class="form-group form-inline ${spring.status.error?then("has-error", "")}">
							<input type="text" name="${spring.status.expression}" class="form-control" value="${spring.status.value?default("")}">
							<span class="help-block"><@spring.showErrors "" /></span>
						</div>
					</div>
		</section>
	</div>

	<section class="form-submit">
		<div class="pull-right">
			<a class="btn btn-lg btn-default btn-mute" href="/admin/users">取消</a>
			<button type="submit" class="btn btn-lg btn-primary btn-emphasis">确认保存</button>
		</div>
	</section>
</form>
</@view>
