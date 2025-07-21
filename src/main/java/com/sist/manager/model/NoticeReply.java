package com.sist.manager.model;

import java.util.Date;
import java.util.List;

public class NoticeReply {
    private int replySeq;
    private int noticeSeq;
    private String userId;
    private String replyContent;
    private Date regDt;
    private List<NoticeReply> replies;
    
    // Getters and Setters
	public int getReplySeq() {
		return replySeq;
	}
	public void setReplySeq(int replySeq) {
		this.replySeq = replySeq;
	}
	public int getNoticeSeq() {
		return noticeSeq;
	}
	public void setNoticeSeq(int noticeSeq) {
		this.noticeSeq = noticeSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public List<NoticeReply> getReplies() {
		return replies;
	}
	public void setReplies(List<NoticeReply> replies) {
		this.replies = replies;
	}
    
}
