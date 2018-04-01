<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal fade bs-example-modal-lg" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="back_btn close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="updateModalLabel">view/update redisKV</h4>
			</div>
			<div class="modal-body">
			
				<!--<div class="row">
					<br>
				</div>  空行 -->
				
				<div class="input-group">
				  <span class="input-group-addon" >serverName</span>
				  <input id="updateModal_serverName" name="serverName" class="form-control" placeholder="serverName" readonly>
				</div>
				
				<div class="row">
					<br>
				</div>
				
				<div class="input-group">
				  <span class="input-group-addon" >dbIndex</span>
				  <input id="updateModal_dbIndex" name="dbIndex" class="form-control" placeholder="dbIndex" readonly> 
				</div>
				
				<div class="row">
					<br>
				</div>
				
				<div class="input-group">
				  <span class="input-group-addon" >key</span>
				  <input id="updateModal_key" name="key" class="form-control" placeholder="key" readonly>
				</div>
				
				<div class="row">
					<br>
				</div>
				
				<div class="input-group">
				  <span class="input-group-addon" >dataType</span>
				  <input id="updateModal_dataType" name="dataType" class="form-control" placeholder="dataType" readonly>
				</div>
				
				<div class="row">
					<br>
				</div>
				
				<form id="STRINGForm" style="display: none;" value1="STRINGForm">
					<table id="STRINGFormTable" class="table table-striped table-bordered" >
						<thead>
							<tr>
								<th>value</th>
								<th class="col-sm-1 col-lg-1">operation</th>
							</tr>
						</thead>
						<tbody>
							<tr style="display: none;">
								<td title="value">
									<div class="input-group">
										<span class="input-group-addon">value</span>
										<input name="value" type="text" class="requireds form-control" placeholder="value"  > 
										<span class="input-group-btn">
											<button class="update_minus_btn btn btn-default" type="button" value1="STRING">
												<span class="glyphicon glyphicon-minus" />
											</button>
										</span>
									</div>
								<!-- <input name="value" class="form-control" > -->
								</td>
								<td><button type="button" class="update_redis_btn btn btn-primary">update</button></td>
							</tr>
						</tbody>
					</table>
				</form>
				
				<form id="LISTForm" style="display: none;">
					<label for="recipient-name" class="control-label">value(REMARK:leftpop , leftpush)</label> 
					<table id="LISTFormTable" class="table table-striped table-bordered" >
						<thead>
							<tr>
								<th>value</th>
								<th class="col-sm-1 col-lg-1">operation</th>
							</tr>
						</thead>
						<tbody>
							<tr style="display: none;">
								<td title="value">
									<div class="input-group">
										<span class="input-group-addon">value</span>
										<input name="value" type="text" class="requireds form-control" placeholder="value"  > 
										<span class="input-group-btn">
											<button class="update_plus_btn btn btn-default" type="button" value1="LIST">
												<span class="glyphicon glyphicon-plus" />
											</button>
											<button class="update_minus_btn btn btn-default" type="button" value1="LIST">
												<span class="glyphicon glyphicon-minus" />
											</button>
										</span>
									</div>
								<!-- <input name="value" class="form-control" > -->
								</td>
								<td><button type="button" class="update_redis_btn btn btn-primary">lpush</button></td>
							</tr>
						</tbody>
					</table>
				</form>
				
				<form id="SETForm" style="display: none;">
					<label for="recipient-name" class="control-label">value(REMARK:minus is random)</label> 
					<table id="SETFormTable" class="table table-striped table-bordered" >
						<thead>
							<tr>
								<th>value</th>
								<th class="col-sm-1 col-lg-1">operation</th>
							</tr>
						</thead>
						<tbody>
							<tr style="display: none;">
								<td title="value">
									<div class="input-group">
										<span class="input-group-addon">value</span>
										<input name="value" type="text" class="requireds form-control" placeholder="value"  > 
										<span class="input-group-btn">
											<button class="update_plus_btn btn btn-default" type="button" value1="SET">
												<span class="glyphicon glyphicon-plus" />
											</button>
											<button class="update_minus_btn btn btn-default" type="button" value1="SET">
												<span class="glyphicon glyphicon-minus" />
											</button>
										</span>
									</div>
								</td>
								<td><button type="button" class="update_redis_btn btn btn-primary">update</button></td>
							</tr>
						</tbody>
					</table>
				</form>
				
				<form id="ZSETForm" style="display: none;">
					<table id="ZSETFormTable" class="table table-striped table-bordered" >
						<thead>
							<tr>
								<th class="col-sm-5 col-lg-5">score</th>
								<th>member</th>
								<th class="col-sm-1 col-lg-1">operation</th>
							</tr>
						</thead>
						<tbody>
							<tr style="display: none;">
								<td title="score"><input name="score" class="form-control" ></td>
								<td title="value">
									<div class="input-group">
										<span class="input-group-addon">value</span>
										<input name="value" type="text" class="requireds form-control" placeholder="value" readonly> 
										<span class="input-group-btn">
											<button class="update_plus_btn btn btn-default" type="button" value1="ZSET">
												<span class="glyphicon glyphicon-plus" />
											</button>
											<button class="update_minus_btn btn btn-default" type="button" value1="ZSET">
												<span class="glyphicon glyphicon-minus" />
											</button>
										</span>
									</div>
								<!-- <input name="member" class="form-control" > -->
								</td>
								<td><button type="button" class="update_redis_btn btn btn-primary">update</button></td>
							</tr>
						</tbody>
					</table>
				</form>
				
				<form id="HASHForm" style="display: none;">
					<table id="HASHFormTable" class="table table-striped table-bordered" >
						<thead>
							<tr>
								<th class="col-sm-5 col-lg-5">field</th>
								<th>value</th>
								<th class="col-sm-1 col-lg-1">operation</th>
							</tr>
						</thead>
						<tbody>
							<tr style="display: none;">
								<td title="field"><input name="field" class="form-control" placeholder="field" readonly></td>
								<td title="value">
									<div class="input-group">
										<span class="input-group-addon">value</span>
										<input name="value" type="text" class="requireds form-control" placeholder="value"  > 
										<span class="input-group-btn">
											<button class="update_plus_btn btn btn-default" type="button" value1="HASH">
												<span class="glyphicon glyphicon-plus" />
											</button>
											<button class="update_minus_btn btn btn-default" type="button" value1="HASH">
												<span class="glyphicon glyphicon-minus" />
											</button>
										</span>
									</div>
								<!-- <input name="value" class="form-control"> -->
								</td>
								<td><button type="button" class="update_redis_btn btn btn-primary">update</button></td>
							</tr>
						</tbody>
					</table>
				</form>
				
			</div>
			
			<div class="modal-footer">
				<button type="button" class="close_btn btn btn-default" data-dismiss="modal">close</button>
			</div>
		</div>
	</div>
</div>