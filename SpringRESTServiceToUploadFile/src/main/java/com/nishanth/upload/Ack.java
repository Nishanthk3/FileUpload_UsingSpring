package com.nishanth.upload;

import com.fasterxml.jackson.annotation.*;

@JsonRootName(value = "ack")

public class Ack {

	protected String uniqueId;
	protected String type;
	protected String errCode;
	protected String errMessage;
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

}