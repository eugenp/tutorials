package cn.workcenter.common;

import cn.workcenter.common.constant.WebConstant;

public enum WorkcenterCodeEnum implements WebConstant{
	//{1:error 2:success}{001:workcenter model}{0001:workcenter first-error}
	OK_REDISKV_UPDATE("20010001", "update success"),
	OK_SUCCESS("20010000", "SUCCESS"),
	;
	
	private String code;
	private String msg;
	WorkcenterCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
	public String getStatus() {
		if(this.getCode().indexOf("2")==0) {
			return SUCCESS;
		} 
		return FAILURE;
	}
}
