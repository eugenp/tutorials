<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal fade bs-example-modal-lg" id="addModal" tabindex="-1" group="dialog" aria-labelledby="addModalLabel">
	<div class="modal-dialog modal-lg" group="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="back_btn close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="addModalLabel">exampleModal_title</h4>
			</div>
			<div class="modal-body">
				<form id="addModalForm">
					<div class="form-group">
						<label for="recipient-name" class="control-label">serverName:</label> 
						<select name="serverName" class="form-control">
							<c:forEach items="${redisServers }" var="redisServer" varStatus="status">
								<c:if test="${status.index == 0}">
									<option checked=true value="${redisServer.name }">${redisServer.name }</option>
								</c:if>
								<c:if test="${status.index != 0}">
									<option value="${redisServer.name }">${redisServer.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					
					<div class="form-group">
						<label for="recipient-name" class="control-label">dbIndex:</label> 
						<select name="dbIndex" class="form-control">
						  <option checked=true value="0">0</option>
						  <option value="1">1</option>
						  <option value="2">2</option>
						  <option value="3">3</option>
						  <option value="4">4</option>
						  <option value="5">5</option>
						  <option value="6">6</option>
						  <option value="7">7</option>
						  <option value="8">8</option>
						  <option value="9">9</option>
						  <option value="10">10</option>
						  <option value="11">11</option>
						  <option value="12">12</option>
						  <option value="13">13</option>
						  <option value="14">14</option>
						  <option value="15">15</option>
						</select>
					</div>
					
					<div class="form-group">
						<label for="recipient-name" class="control-label">dataType:</label> 
						<select id="addModal_dateType" name="dataType" class="form-control">
						  <option checked=true value="STRING">STRING</option>
						  <option value="LIST">LIST</option>
						  <option value="SET">SET</option>
						  <option value="ZSET">ZSET</option>
						  <option value="HASH">HASH</option>
						</select>
					</div>

					<span class="label label-info">key</span>
					<div class="input-group">
					  <span class="input-group-addon" >key</span>
					  <input name="key" type="text" class="form-control" placeholder="key">
					</div>
					
					<div class="col-sm-12">
						<br>
					</div> <!-- 空行 -->
					
					<span class="label label-info">STRING value</span>
					<div class="input_div input-group">
					  <span class="input-group-addon" >value</span>
					  <input name="value" type="text" class="form-control" placeholder="value" >
					</div>
					
					<!-- <span class="label label-info">STRING value</span>
					<div class="input_div input-group">
					  <span class="input-group-addon" >value</span>
					  <input name="value" type="text" class="form-control" placeholder="value" >
					</div>
					
					<div class="col-sm-12">
						<br>
					</div> 空行
					
					<span class="label label-info">LIST values</span>
					<div class="input_div input-group">
						<span class="input-group-addon">value</span>
						<input name="value" type="text" class="requireds form-control" placeholder="value"  > 
						<span class="input-group-btn">
							<button class="plus_btn btn btn-default" type="button" value1="LIST">
								<span class="glyphicon glyphicon-plus" />
							</button>
							<button class="minus_btn btn btn-default" type="button" value1="LIST">
								<span class="glyphicon glyphicon-minus" />
							</button>
						</span>
					</div>
					
					<div class="col-sm-12">
						<br>
					</div> 空行
					
					<span class="label label-info">SET values</span>
					<div class="input_div input-group">
						<span class="input-group-addon">value</span>
						<input name="value" type="text" class="requireds form-control" placeholder="value"  > 
						<span class="input-group-btn">
							<button class="plus_btn btn btn-default" type="button" value1="SET">
								<span class="glyphicon glyphicon-plus" />
							</button>
							<button class="minus_btn btn btn-default" type="button" value1="SET">
								<span class="glyphicon glyphicon-minus" />
							</button>
						</span>
					</div>
					
					<div class="col-sm-12">
						<br>
					</div> 空行
					
					<span class="label label-info">ZSET values</span>
					<div class="input_div input-group col-sm-12 col-lg-12">
						<div class="row col-sm-12 col-lg-12">
							<div class="col-sm-6 col-lg-6">
								<div class="input-group col-sm-12 col-lg-12">
								  <span class="input-group-addon" >score</span>
								  <input name="score" type="text" class="form-control" placeholder="score" >
								</div>
							</div>
							<div class="col-sm-6 col-lg-6">
								<div class="input-group col-sm-12 col-lg-12">
									<span class="input-group-addon">member</span>
									<input name="member" type="text" class="requireds form-control" placeholder="member"  > 
									<span class="input-group-btn">
										<button class="plus_btn btn btn-default" type="button" value1="ZSET">
											<span class="glyphicon glyphicon-plus" />
										</button>
										<button class="minus_btn btn btn-default" type="button" value1="ZSET">
											<span class="glyphicon glyphicon-minus" />
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-sm-12">
						<br>
					</div> 空行
					
					
					<span class="label label-info">HASH values</span>
					<div class="input_div input-group col-sm-12 col-lg-12">
						<div class="row col-sm-12 col-lg-12">
							<div class="col-sm-6 col-lg-6">
								<div class="input-group col-sm-12 col-lg-12">
								  <span class="input-group-addon" >field</span>
								  <input name="field" type="text" class="form-control" placeholder="field" >
								</div>
							</div>
							<div class="col-sm-6 col-lg-6">
								<div class="input-group col-sm-12 col-lg-12">
									<span class="input-group-addon">value</span>
									<input name="value" type="text" class="requireds form-control" placeholder="value"  > 
									<span class="input-group-btn">
										<button class="plus_btn btn btn-default" type="button" value1="HASH">
											<span class="glyphicon glyphicon-plus" />
										</button>
										<button class="minus_btn btn btn-default" type="button" value1="HASH">
											<span class="glyphicon glyphicon-minus" />
										</button>
									</span>
								</div>
							</div>
						</div>
					</div> -->
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="close_btn btn btn-default" data-dismiss="modal">close</button>
				<button value1="add" type="button" class="add_KV_btn btn btn-primary">add</button>
			</div>
		</div>
	</div>
</div>