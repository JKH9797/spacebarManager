package com.sist.manager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sist.manager.model.User;

@Repository("userDao")
public interface UserDao {
	
	// 사용자 리스트
	public List<User> userList(User user);
	
	// 사용자 리스트 전체 건수 조회
	public int userListCount(User user);
	
	// 사용자 조회
	public User userSelect(String userId);
	
	// 사용자 정보 수정
	public int userUpdate(User user);
	
	// 승인
	public int userApprove(String userId);
	
    // [수정] 대시보드 판매자 검색 (페이징 추가)
    List<User> findSellersByQuery(Map<String, Object> params);

    // [추가] 대시보드 판매자 검색 결과 총 개수
    int countSellersByQuery(Map<String, Object> params);
	   
}

