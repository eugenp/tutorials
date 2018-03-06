<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal fade bs-example-modal-lg" id="addServerModal" tabindex="-1" group="dialog" aria-labelledby="addServerModalLabel">
	<div class="modal-dialog modal-lg" group="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="back_btn close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="addServerModalLabel">exampleModal_title</h4>
			</div>
			<div class="modal-body">
				<form id="addServerModalForm">

					<span class="label label-info">redis.host</span>
					<div class="input-group">
					  <span class="input-group-addon" >redis.host</span>
					  <input name="host" type="text" class="form-control" placeholder="host">
					</div>
					
					<div class="col-sm-12">
						<br>
					</div> <!-- 空行 -->
					
					<span class="label label-info">redis.name</span>
					<div class="input_div input-group">
					  <span class="input-group-addon" >redis.name</span>
					  <input name="name" type="text" class="form-control" placeholder="name" >
					</div>
					
					<div class="col-sm-12">
						<br>
					</div> <!-- 空行 -->
					
					<span class="label label-info">redis.port</span>
					<div class="input_div input-group">
					  <span class="input-group-addon" >redis.port</span>
					  <input name="port" type="text" class="form-control" placeholder="port" >
					</div>
					
					<div class="col-sm-12">
						<br>
					</div> <!-- 空行 -->
					
					<span class="label label-info">redis.password</span>
					<div class="input_div input-group">
					  <span class="input-group-addon" >redis.password</span>
					  <input name="password" type="text" class="form-control" placeholder="password" >
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="close_btn btn btn-default" data-dismiss="modal">close</button>
				<button value1="addServer" type="button" class="addServer_btn btn btn-primary">addServer</button>
			</div>
		</div>
	</div>
</div>