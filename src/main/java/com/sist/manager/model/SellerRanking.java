package com.sist.manager.model;

import java.io.Serializable;

public class SellerRanking implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3819936656073750055L;

    private long rank;
    private String hostId;
    private String hostName;
    private String hostNickname;
    private long totalAmount;
    private long totalCount;
    private double averageRating;
    private int reviewCount;
    
    public SellerRanking()
    {
        rank = 0;
        hostId = "";
        hostName = "";
        hostNickname = "";
        totalAmount = 0;
        totalCount = 0;
        averageRating = 0;
        reviewCount = 0;
    }

    

	public double getAverageRating() {
		return averageRating;
	}



	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}



	public int getReviewCount() {
		return reviewCount;
	}



	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}



	public long getRank() {
		return rank;
	}


	public void setRank(long rank) {
		this.rank = rank;
	}


	public String getHostId() {
		return hostId;
	}


	public void setHostId(String hostId) {
		this.hostId = hostId;
	}


	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public String getHostNickname() {
		return hostNickname;
	}


	public void setHostNickname(String hostNickname) {
		this.hostNickname = hostNickname;
	}


	public long getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}


	public long getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
    
}	
