package com.sist.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.manager.model.Notice;
import com.sist.manager.model.NoticeReply;

@Repository
public interface NoticeDao 
{
	List<Notice> selectNoticeList();
	
    Notice selectNoticeDetail(int noticeSeq);
    
    int insertNotice(Notice notice);
    
    void insertReply(NoticeReply reply);
    
    List<NoticeReply> selectRepliesByNotice(int noticeSeq);
    
    public int updateNotice(Notice notice);

} 