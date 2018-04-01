package cn.workcenter.common;

import cn.workcenter.common.constant.WebConstant;

public class WorkcenterResult implements WebConstant{
	
	private String resultCode; //200 success   500 server-error 600 business-exception
	
	private String resultMsg;  //detail
	
	private Object data;       //attachment
	
	private WorkcenterCodeEnum codeEnum;//error-code-msg enum

	private WorkcenterResult() {
		
	}
	
	private WorkcenterResult(String resultCode, String resultMsg, Object data, WorkcenterCodeEnum codeEnum) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.data = data;
		this.codeEnum = codeEnum;
	}
	
	public String getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public Object getData() {
		return data;
	}

	public WorkcenterCodeEnum getCodeEnum() {
		return codeEnum;
	}

	public void setCodeEnum(WorkcenterCodeEnum codeEnum) {
		this.codeEnum = codeEnum;
	}
	
	public static Builder custom() {
		return new Builder();
	}
	
	public static class Builder {
		
		private String resultCode; //200  SUCCESS200    500server-error  ERROR500  600business-exception  BUSINESSEXCEPTION600
		
		private String resultMsg;  //detail
		
		private Object data;       //attachment
		
		private WorkcenterCodeEnum codeEnum;//error-code-msg enum
		
		Builder() {
			resultCode = SUCCESS200;
			resultMsg = "request success";
			data = "null";
			codeEnum = WorkcenterCodeEnum.valueOf(OK_SUCCESS);
		}
		
		public Builder setOK(WorkcenterCodeEnum codeEnum, Object data) {
			resultCode = SUCCESS200;
			resultMsg = "request success";
			this.data=data;
			this.codeEnum = codeEnum;
			return this;
		}
		
		public Builder setOK(WorkcenterCodeEnum codeEnum) {
			resultCode = SUCCESS200;
			resultMsg = "request success";
			this.data=null;
			this.codeEnum = codeEnum;
			return this;
		}
		
		public Builder setNO(WorkcenterCodeEnum codeEnum) {
			resultCode = EXCEPTION600;
			resultMsg = "business exception";
			this.data=null;
			this.codeEnum = codeEnum;
			return this;
		}
		
		public WorkcenterResult build() {
			return new WorkcenterResult(resultCode, resultMsg, data, codeEnum);
		}
		
	}
}
