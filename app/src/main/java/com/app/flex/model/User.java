package com.app.flex.model;

import com.google.gson.annotations.SerializedName;

public class User {
	@SerializedName("id")
	private String mId;

	@SerializedName("email")
	private String mEmail;

	@SerializedName("password")
	private String mPassword;

	@SerializedName("first_name")
	private String mFirstname;

	@SerializedName("last_name")
	private String mLastName;

	@SerializedName("role")
	private String mRole;

	@SerializedName("home_address")
	private String mHomeAddress;

	@SerializedName("office_address")
	private String mOfficeAddress;

	@SerializedName("phone_number")
	private String mPhoneNumber;

	@SerializedName("profile_image")
	private String mProfilePicture;

	@SerializedName("device_type")
	private String mDeviceType;

	@SerializedName("device_token")
	private String mDeviceToken;

	@SerializedName("status")
	private String mStatus;

	@SerializedName("last_login_date")
	private String mLastLogin;

	@SerializedName("create_date")
	private String mCreatedDate;

	@SerializedName("update_date")
	private String mUpdatedDate;

	@SerializedName("profile_updated")
	private String mProfileUpdated;


	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getmPassword() {
		return mPassword;
	}

	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	public String getmFirstname() {
		return mFirstname;
	}

	public void setmFirstname(String mFirstname) {
		this.mFirstname = mFirstname;
	}

	public String getmLastName() {
		return mLastName;
	}

	public void setmLastName(String mLastName) {
		this.mLastName = mLastName;
	}

	public String getmRole() {
		return mRole;
	}

	public void setmRole(String mRole) {
		this.mRole = mRole;
	}

	public String getmHomeAddress() {
		return mHomeAddress;
	}

	public void setmHomeAddress(String mHomeAddress) {
		this.mHomeAddress = mHomeAddress;
	}

	public String getmOfficeAddress() {
		return mOfficeAddress;
	}

	public void setmOfficeAddress(String mOfficeAddress) {
		this.mOfficeAddress = mOfficeAddress;
	}

	public String getmPhoneNumber() {
		return mPhoneNumber;
	}

	public void setmPhoneNumber(String mPhoneNumber) {
		this.mPhoneNumber = mPhoneNumber;
	}

	public String getmProfilePicture() {
		return mProfilePicture;
	}

	public void setmProfilePicture(String mProfilePicture) {
		this.mProfilePicture = mProfilePicture;
	}

	public String getmDeviceType() {
		return mDeviceType;
	}

	public void setmDeviceType(String mDeviceType) {
		this.mDeviceType = mDeviceType;
	}

	public String getmDeviceToken() {
		return mDeviceToken;
	}

	public void setmDeviceToken(String mDeviceToken) {
		this.mDeviceToken = mDeviceToken;
	}

	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public String getmLastLogin() {
		return mLastLogin;
	}

	public void setmLastLogin(String mLastLogin) {
		this.mLastLogin = mLastLogin;
	}

	public String getmCreatedDate() {
		return mCreatedDate;
	}

	public void setmCreatedDate(String mCreatedDate) {
		this.mCreatedDate = mCreatedDate;
	}

	public String getmUpdatedDate() {
		return mUpdatedDate;
	}

	public void setmUpdatedDate(String mUpdatedDate) {
		this.mUpdatedDate = mUpdatedDate;
	}

	public String getmProfileUpdated() {
		return mProfileUpdated;
	}

	public void setmProfileUpdated(String mProfileUpdated) {
		this.mProfileUpdated = mProfileUpdated;
	}
}
