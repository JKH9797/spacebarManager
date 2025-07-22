package com.sist.manager.model;

import java.io.Serializable;

public class Room implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8704454051381687382L;
	
	private long roomSeq;
	private long roomCatSeq;
	private String hostId;
	private String roomAddr;
	private long latitude;
	private long longitude;
	private String region;
	private String regDt;
	private String updateDt;
	private String autoConfirmYn;
	private String roomTitle;
	private String roomDesc;
	private String cancelPolicy;
	private long averageRating;
	private long reviewCount;
	private long minTimes;
	private long maxTimes;
	private String delYn;
	private String saleYn;
	private String roomCatName;

	private int startRow;
	private int endRow;
	
	
	public Room()
	{
		roomSeq = 0;
		roomCatSeq = 0;
		hostId = "";
		roomAddr = "";
		latitude = 0;
		longitude = 0;
		region = "";
		regDt = "";
		updateDt = "";
		autoConfirmYn = "";
		roomTitle = "";
		roomDesc = "";
		cancelPolicy = "";
		averageRating = 0;
		reviewCount = 0;
		minTimes = 0;
		maxTimes = 0;
		delYn = "";
		saleYn = "";
		roomCatName ="";
		
		startRow = 0;
		endRow = 0;
	}

	
	

	public int getStartRow() {
		return startRow;
	}




	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}




	public int getEndRow() {
		return endRow;
	}




	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}




	public String getRoomCatName() {
		return roomCatName;
	}




	public void setRoomCatName(String roomCatName) {
		this.roomCatName = roomCatName;
	}




	public long getRoomSeq() {
		return roomSeq;
	}


	public void setRoomSeq(long roomSeq) {
		this.roomSeq = roomSeq;
	}


	public long getRoomCatSeq() {
		return roomCatSeq;
	}


	public void setRoomCatSeq(long roomCatSeq) {
		this.roomCatSeq = roomCatSeq;
	}


	public String getHostId() {
		return hostId;
	}


	public void setHostId(String hostId) {
		this.hostId = hostId;
	}


	public String getRoomAddr() {
		return roomAddr;
	}


	public void setRoomAddr(String roomAddr) {
		this.roomAddr = roomAddr;
	}


	public long getLatitude() {
		return latitude;
	}


	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}


	public long getLongitude() {
		return longitude;
	}


	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getRegDt() {
		return regDt;
	}


	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}


	public String getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}


	public String getAutoConfirmYn() {
		return autoConfirmYn;
	}


	public void setAutoConfirmYn(String autoConfirmYn) {
		this.autoConfirmYn = autoConfirmYn;
	}


	public String getRoomTitle() {
		return roomTitle;
	}


	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}


	public String getRoomDesc() {
		return roomDesc;
	}


	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}


	public String getCancelPolicy() {
		return cancelPolicy;
	}


	public void setCancelPolicy(String cancelPolicy) {
		this.cancelPolicy = cancelPolicy;
	}


	public long getAverageRating() {
		return averageRating;
	}


	public void setAverageRating(long averageRating) {
		this.averageRating = averageRating;
	}


	public long getReviewCount() {
		return reviewCount;
	}


	public void setReviewCount(long reviewCount) {
		this.reviewCount = reviewCount;
	}


	public long getMinTimes() {
		return minTimes;
	}


	public void setMinTimes(long minTimes) {
		this.minTimes = minTimes;
	}


	public long getMaxTimes() {
		return maxTimes;
	}


	public void setMaxTimes(long maxTimes) {
		this.maxTimes = maxTimes;
	}


	public String getDelYn() {
		return delYn;
	}


	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}


	public String getSaleYn() {
		return saleYn;
	}


	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}
	
	
}
