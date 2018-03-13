package cn.workcenter.common.response;

import cn.workcenter.common.WorkcenterResult;
import cn.workcenter.common.constant.WebConstant;

import com.alibaba.fastjson.JSON;


public class WorkcenterResponseBodyJson implements ResponseBody , WebConstant{
	
	public final String returncode; //200 success   500 server-error 600 business-exception
	public final String returnmsg; 
	public final String returnmemo;//{SUCCESS/FAILURE}:{errorcode}:{errormsg}
	public final Object data;//attachment for data transfer to web-client 
	public final String operator;
	
	private WorkcenterResponseBodyJson() {
		returncode = "";
		returnmsg = "";
		returnmemo = "";
		operator = "";
		data = null;
	}
	
	private WorkcenterResponseBodyJson(String returncode, String returnmsg, String returnmemo, Object data, String operator) {
		this.returncode = returncode;
		this.returnmsg = returnmsg;
		this.returnmemo = returnmemo;
		this.operator = operator;
		this.data = data;
	}
	
	public static WorkcenterResponseBodyJson.Builder custom() {
		return new Builder();
	}
	
	public static class Builder {
		private String returncode;
		private String returnmsg;
		private String returnmemo;
		private String operator;
		private Object data;
		
		Builder() {
			super();
			this.returncode = "200";
			this.returnmsg = "request success";
			this.returnmemo = "SUCCESS:200000:request success";
			this.operator = "nothing";
			this.data = "attachment";
		}
		
		public WorkcenterResponseBodyJson.Builder setReturncode(String returncode) {
			this.returncode = returncode;
			return this;
		}

		public WorkcenterResponseBodyJson.Builder setReturnmsg(String returnmsg) {
			this.returnmsg = returnmsg;
			return this;
		}

		public WorkcenterResponseBodyJson.Builder setReturnmemo(String returnmemo) {
			this.returnmemo = returnmemo;
			return this;
		}
		
		public WorkcenterResponseBodyJson.Builder setOperator(String operator) {
			this.operator = operator;
			return this;
		}
		
		public Builder setAll(RuntimeException e) {
			this.returncode = "500";
			this.returnmsg = "server error";
			this.returnmemo = "FAILURE:10010000:RuntimeExceptionException" + COLON + e.getMessage();
			this.operator = null;
			this.data = null;
			return this;
		}
		
		public Builder setAll(Exception e) {
			this.returncode = "500";
			this.returnmsg = "server error";
			this.returnmemo = "FAILURE:10010000:Exception" + COLON + e.getMessage();
			this.operator = null;
			this.data = null;
			return this;
		}
		
		public Builder setAll(Throwable t) {
			this.returncode = "500";
			this.returnmsg = "server error";
			this.returnmemo = "FAILURE:10010000:Throwable" + COLON + t.getMessage();
			this.operator = null;
			this.data = null;
			return this;
		}
		
		public WorkcenterResponseBodyJson.Builder setAll(RuntimeException e, String operator) {
			this.returncode = "500";
			this.returnmsg = "server error";
			this.returnmemo = "FAILURE:10010000:RuntimeException" + COLON + e.getMessage();
			this.operator = operator;
			this.data = null;
			return this;
		}
		
		public WorkcenterResponseBodyJson.Builder setAll(WorkcenterResult result, String operator) {
			this.returncode = result.getResultCode();
			this.returnmsg = result.getResultMsg();
			this.returnmemo = result.getCodeEnum().getStatus() + COLON+result.getCodeEnum().getCode() + COLON+result.getCodeEnum().getMsg();
			this.operator = operator;
			this.data = result.getData();
			return this;
		}
		
		public WorkcenterResponseBodyJson.Builder setAll(WorkcenterResult result,  String operator, Object data) {
			this.returncode = result.getResultCode();
			this.returnmsg = result.getResultMsg();
			this.returnmemo = result.getCodeEnum().getStatus() + COLON+result.getCodeEnum().getCode() + COLON+result.getCodeEnum().getMsg();
			this.operator = operator;
			this.data = data;
			return this;
		}
		
		public WorkcenterResponseBodyJson.Builder setAll(String returncode, String returnmsg, String returnmemo, String operator , Object data){
			this.returncode = returncode;
			this.returnmsg = returnmsg;
			this.returnmemo = returnmemo;
			this.operator = operator;
			this.data = data;
			return this;
		}

		public WorkcenterResponseBodyJson build() {
			return new WorkcenterResponseBodyJson(returncode, returnmsg, returnmemo, data, operator);
		}

	}

	public String getReturncode() {
		return returncode;
	}

	public String getReturnmsg() {
		return returnmsg;
	}

	public String getReturnmemo() {
		return returnmemo;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public Object getData() {
		return data;
	}
	
	@Override
	public String toString() {
		String json = JSON.toJSONString(this);
		return json;
	}
	
}
