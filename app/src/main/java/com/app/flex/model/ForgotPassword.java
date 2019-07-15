package com.app.flex.model;

import com.google.gson.annotations.SerializedName;

public class ForgotPassword
{
	@SerializedName("status")
	private String mStatus;

	@SerializedName("result")
	private String mResult;

	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public String getmResult() {
		return mResult;
	}

	public void setmResult(String mResult) {
		this.mResult = mResult;
	}
}
