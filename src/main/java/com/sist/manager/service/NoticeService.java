package com.sist.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.manager.dao.NoticeDao;
import com.sist.manager.model.Notice;
import com.sist.manager.model.NoticeReply;


@Service
public class NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    // 공지사항 목록 조회
    public List<Notice> getNoticeList() {
        return noticeDao.selectNoticeList();
    }

    // 공지사항 상세 조회
    public Notice getNoticeById(int noticeSeq) {
        Notice notice = noticeDao.selectNoticeDetail(noticeSeq);
        if (notice != null) {
            // 공지사항 상세와 함께 댓글도 같이 가져옴
            notice.setReplies(noticeDao.selectRepliesByNotice(noticeSeq));
        }
        return notice;
    }

    // 공지사항 등록
    public int insertNotice(Notice notice) throws Exception
	{
		
		int count = noticeDao.insertNotice(notice);
		
		return count;
	}

    // 댓글 등록
    public void writeReply(NoticeReply reply) {
        noticeDao.insertReply(reply);
    }

    // 댓글 목록 조회 (개별 호출용)
    public List<NoticeReply> getReplies(int noticeSeq) {
        return noticeDao.selectRepliesByNotice(noticeSeq);
    }
    
    //공지사항 수정
    public int updateNotice(Notice notice) {
        return noticeDao.updateNotice(notice);
    }
    
    //댓글수정
    public boolean updateReply(int replySeq, String replyContent) {
        int updated = noticeDao.updateReply(replySeq, replyContent);
        return updated > 0;
    }
    
    //댓글삭제
    public boolean deleteReply(int replySeq, String userId) {
        int cnt = noticeDao.deleteReply(replySeq, userId);
        return cnt > 0;
    }
    
}
